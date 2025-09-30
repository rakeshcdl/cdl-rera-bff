package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.ApplicationSettingDTO;
import com.cdl.escrow.dto.RealEstateDocumentDTO;
import com.cdl.escrow.entity.ApplicationConfiguration;
import com.cdl.escrow.entity.RealEstateDocument;
import com.cdl.escrow.enumeration.WorkflowStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationSettingMapper;
import com.cdl.escrow.mapper.RealEstateDocumentMapper;
import com.cdl.escrow.repository.ApplicationConfigurationRepository;
import com.cdl.escrow.repository.RealEstateDocumentRepository;
import com.cdl.escrow.service.ApplicationSettingService;
import com.cdl.escrow.service.RealEstateDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateDocumentServiceImpl implements RealEstateDocumentService {
    private final RealEstateDocumentRepository repository;

    private final RealEstateDocumentMapper mapper;

    private final ApplicationConfigurationRepository applicationConfigurationRepository;

    private final ApplicationSettingService applicationSettingService;

    private final ApplicationSettingMapper applicationSettingMapper;

    // CONFIG KEY in ApplicationConfiguration
    private static final String FILE_STORAGE_KEY = "FILE_STORAGE_PATH"; // e.g. "/opt/escrow/files"

    // safety limits
    private static final long MAX_FILE_SIZE = DataSize.ofMegabytes(25).toBytes();


    // Allowed file extensions (lowercase)
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "xlsx", "docx", "png", "jpg", "jpeg");
    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateDocumentDTO> getAllRealEstateDocument(Pageable pageable) {
        log.debug("Fetching all Real EstateAssest document , page: {}", pageable.getPageNumber());
        Page<RealEstateDocument> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateDocumentDTO> getRealEstateDocumentById(Long id) {
        log.debug("Fetching Real EstateAssest document with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public RealEstateDocumentDTO saveRealEstateDocument(RealEstateDocumentDTO realEstateDocumentDTO) {
        log.info("Saving new Real EstateAssest document");
        RealEstateDocument entity = mapper.toEntity(realEstateDocumentDTO);
        RealEstateDocument saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public RealEstateDocumentDTO updateRealEstateDocument(Long id, RealEstateDocumentDTO realEstateDocumentDTO) {
        log.info("Updating Real EstateAssest Beneficiary with ID: {}", id);

        RealEstateDocument existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest document not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateDocument toUpdate = mapper.toEntity(realEstateDocumentDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateDocument updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteRealEstateDocumentById(Long id) {
        log.info("Deleting Real EstateAssest document  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest document not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

/// //// Business

// ✅ Upload file (Hardened, consistent RELATIVE storage)
@Transactional
public RealEstateDocument storeFile(MultipartFile file, String module, Long recordId, String storageType,Long documentTypeId) {
    // ---- 0) Basic validations ----
    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException("File is required");
    }
    if (file.getSize() > MAX_FILE_SIZE) {
        throw new IllegalArgumentException("File exceeds max size: " + MAX_FILE_SIZE + " bytes");
    }

    // Module is stored in DB metadata only (we are saving under base root directly)
    String safeModule = sanitizeSimple(module);
    if (safeModule == null || safeModule.isBlank()) {
        throw new IllegalArgumentException("Invalid module");
    }

    // ---- 1) Validate storage type ----
  /*  List<String> allowedStorage = List.of("LOCAL", "SERVER", "S3");
    String normalizedStorage = storageType == null ? null : storageType.toUpperCase(Locale.ROOT);
    if (normalizedStorage == null || !allowedStorage.contains(normalizedStorage)) {
        throw new IllegalArgumentException("Invalid storage type. Allowed: " + allowedStorage);
    }*/

    try {
        // ---- 2) Resolve & ensure base directory exists ----
        // Save directly under /opt/escrow/reradocs (no subfolders)
        Path baseRoot = Paths.get("/opt/escrow/reradocs").toAbsolutePath().normalize();

        // Create directory if missing, before checks
       // Files.createDirectories(baseRoot);

        // Check it's a directory & writable
      /*  if (!Files.isDirectory(baseRoot)) {
            throw new IOException("Missing base dir: " + baseRoot);
        }
        if (!Files.isWritable(baseRoot)) {
            throw new IOException("Not writable by " + System.getProperty("user.name") + ": " + baseRoot);
        }*/

        // Optional: a targetDir alias for clarity
        Path targetDir = baseRoot;
        ensureUnderRoot(baseRoot, targetDir); // safety method

        // ---- 3) Build a safe filename ----
        String original = (file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                ? file.getOriginalFilename()
                : "upload.bin";

        String baseName = sanitizeFilename(FilenameUtils.getBaseName(original));
        String ext = Optional.ofNullable(FilenameUtils.getExtension(original))
                .map(s -> s.toLowerCase(Locale.ROOT))
                .orElse("");
        ext = sanitizeExtension(ext);

        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: " + ALLOWED_EXTENSIONS);
        }

        String safeFileName = baseName + "-" + UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

        // ---- 4) Final target path & safety checks ----
        Path target = targetDir.resolve(safeFileName).normalize();
        ensureUnderRoot(baseRoot, target);

        // ---- 5) Save file (atomic-ish replace) ----
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // ---- 6) Persist metadata (relative path for portability) ----
        String relativeLocation = baseRoot.relativize(target).toString().replace('\\', '/');

        Optional<ApplicationSettingDTO> applicationSettingDTO =applicationSettingService.getApplicationSettingById(documentTypeId);

        RealEstateDocument doc = new RealEstateDocument();
        doc.setDocumentName(safeFileName);
        doc.setModule(safeModule);
        doc.setRecordId(String.valueOf(recordId));
        doc.setStorageType("SERVER");
        doc.setLocation(relativeLocation);
        doc.setUploadDate(ZonedDateTime.now());
        doc.setDocumentSize(file.getSize() + " bytes");
        doc.setEnabled(true);
        doc.setDocumentType( applicationSettingDTO
                .map(applicationSettingMapper::toEntity)
                .orElse(null));
        //doc.setStatus(WorkflowStatus.ACTIVE);

        // Persist doc and return
        return repository.save(doc);

    } catch (IOException e) {
        System.out.println("Java user: " + System.getProperty("user.name"));
        System.out.println("Directory writable? " + Files.isWritable(Paths.get("/opt/escrow/reradocs")));
        e.printStackTrace();
        throw new RuntimeException("Could not store file under /opt/escrow/reradocs. Check directory existence & permissions.", e);
    }
}




    // ✅ Download by ID (resolves RELATIVE location under configured base root)
    public org.springframework.core.io.Resource loadFileById(Long docId) {
        RealEstateDocument doc = repository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + docId));


        if (doc.getLocation() == null || doc.getLocation().isBlank()) {
            throw new IllegalStateException("File location is not set for document: " + doc.getDocumentName());
        }


        /*ApplicationConfiguration cfg = applicationConfigurationRepository
                .findByConfigKeyAndEnabledTrue(FILE_STORAGE_KEY)
                .orElseThrow(() -> new IllegalStateException(FILE_STORAGE_KEY + " not configured"));
*/


      //  Path baseRoot = resolveRoot(cfg.getConfigValue());
        Path baseRoot = Paths.get("/opt/escrow/reradocs").toAbsolutePath().normalize();
        Path absolute = baseRoot.resolve(doc.getLocation()).normalize();
        ensureUnderRoot(baseRoot, absolute);


        if (!Files.exists(absolute) || !Files.isReadable(absolute)) {
            throw new RuntimeException("File not found or not readable: " + absolute);
        }


        try {
            return new UrlResource(absolute.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while loading file: " + absolute, e);
        }
    }

    // ✅ Download by Name (latest from module root)
    public org.springframework.core.io.Resource loadFileByName(String module, String fileName) {
        String safeModule = sanitizeSimple(module);
        if (safeModule.isBlank()) {
            throw new IllegalArgumentException("Invalid module");
        }
       /* ApplicationConfiguration cfg = applicationConfigurationRepository
                .findByConfigKeyAndEnabledTrue(FILE_STORAGE_KEY)
                .orElseThrow(() -> new IllegalStateException(FILE_STORAGE_KEY + " not configured"));


        Path baseRoot = resolveRoot(cfg.getConfigValue());*/
        Path baseRoot = Paths.get("/opt/escrow/reradocs").toAbsolutePath().normalize();
        Path absolute = baseRoot.resolve(fileName).normalize();
        ensureUnderRoot(baseRoot, absolute);


        if (!Files.exists(absolute) || !Files.isReadable(absolute)) {
            throw new RuntimeException("File not found or not readable: " + absolute);
        }
        try {
            return new UrlResource(absolute.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while loading file: " + absolute, e);
        }
    }

    // ---------- Helpers ----------


    private static void ensureUnderRoot(Path root, Path candidate) {
        if (!candidate.normalize().startsWith(root.normalize())) {
            throw new SecurityException("Path traversal detected");
        }
    }


    private Path resolveRoot(String rawBasePath) {
        if (rawBasePath == null || rawBasePath.isBlank()) {
            throw new IllegalStateException("Base storage path is empty");
        }
        String bp = rawBasePath.trim().replace("\\", "/"); // unify separators
        bp = bp.replaceFirst("^/+", "/"); // collapse leading slashes
        Path root = Paths.get(bp).toAbsolutePath().normalize();
        return root;
    }


    private static String sanitizeSimple(String s) {
        return s == null ? "" : s.trim().replaceAll("[^a-zA-Z0-9_\\-]", "");
    }


    private static String sanitizeFilename(String name) {
        String n = name == null ? "file" : name;
        n = n.replaceAll("[\\\\/\\s]+", "_");
        n = n.replaceAll("[^a-zA-Z0-9_\\-\\.]", "");
        return n.isBlank() ? "file" : n;
    }


    private static String sanitizeExtension(String ext) {
        if (ext == null) return "";
        return ext.replaceAll("[^a-zA-Z0-9]", "");
    }



}



