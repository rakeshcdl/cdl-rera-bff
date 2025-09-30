package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.RealEstateDocumentCriteria;
import com.cdl.escrow.criteriaservice.RealEstateDocumentCriteriaService;
import com.cdl.escrow.dto.RealEstateDocumentDTO;
import com.cdl.escrow.entity.RealEstateDocument;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateDocumentRepository;
import com.cdl.escrow.service.RealEstateDocumentService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/real-estate-document")
@RequiredArgsConstructor
@Slf4j
public class RealEstateDocumentController {

    private final RealEstateDocumentService realEstateDocumentService;

    private final RealEstateDocumentCriteriaService realEstateDocumentCriteriaService;

    private final RealEstateDocumentRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_DOCUMENT";

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "pdf", "jpg", "jpeg", "png", "gif", "txt", "csv", "xlsx", "docx"
    );

    @GetMapping
    public ResponseEntity<Page<RealEstateDocumentDTO>> getAllRealEstateDocumentByCriteria(@ParameterObject RealEstateDocumentCriteria criteria,
                                                                                                                      @ParameterObject  Pageable pageable) {
        Page<RealEstateDocumentDTO> page = realEstateDocumentCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateDocumentDTO>> getAllRealEstateDocument(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate document, page: {}", pageable.getPageNumber());
        Page<RealEstateDocumentDTO> page = realEstateDocumentService.getAllRealEstateDocument(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateDocumentDTO> saveRealEstateDocument(
            @Valid @RequestBody RealEstateDocumentDTO dto) {
        log.info("Creating new real estate document");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate document cannot already have an ID", ENTITY_NAME , "idexists");
        }
        RealEstateDocumentDTO saved = realEstateDocumentService.saveRealEstateDocument(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateDocumentDTO> getRealEstateDocumentById(@PathVariable Long id) {
        log.info("Fetching real estate document with ID: {}", id);
        return realEstateDocumentService.getRealEstateDocumentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate document not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateDocumentDTO> updateRealEstateDocument(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateDocumentDTO dto) {
        log.info("Updating real estate document with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateDocumentDTO updated = realEstateDocumentService.updateRealEstateDocument(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateDocumentById(@PathVariable Long id) {
        log.info("Deleting real estate document with ID: {}", id);
        boolean deleted = realEstateDocumentService.deleteRealEstateDocumentById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateDocument deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateDocument deletion failed - ID: " + id);
        }
    }

    // ✅ Upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<RealEstateDocument> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("module") String module,
            @RequestParam("recordId") Long recordId,
            @RequestParam("documentType") Long documentTypeId,
            @RequestParam(value = "storageType", defaultValue = "LOCAL") String storageType
    ) throws IOException {
        RealEstateDocument savedDoc = realEstateDocumentService.storeFile(file, module, recordId, storageType,documentTypeId);
        return ResponseEntity.ok(savedDoc);
    }

    // ✅ Download by ID
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFileById(@PathVariable Long id) throws IOException {
        Resource resource = realEstateDocumentService.loadFileById(id);

        String contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // ✅ Download by File Name
    @GetMapping("/download/{module}/name/{fileName}")
    public ResponseEntity<Resource> downloadFileByName(
            @PathVariable String module,
            @PathVariable String fileName
    ) throws IOException {
        Resource resource = realEstateDocumentService.loadFileByName(module, fileName);

        String contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // Load files from resource path for templates download

    @GetMapping("/download/templates/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Security: Validate filename
            if (!isValidFileName(fileName)) {
                log.warn("Invalid filename attempted: {}", fileName);
                return ResponseEntity.badRequest().build();
            }

            ClassPathResource resource = new ClassPathResource("templates/" + fileName);

            if (!resource.exists()) {
                log.warn("File not found: {}", fileName);
                return ResponseEntity.notFound().build();
            }

            String contentType = determineContentType(fileName);
            long fileSize = resource.contentLength();

            log.info("Downloading file: {} ({})", fileName, fileSize + " bytes");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(fileSize)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (IOException e) {
            log.error("Error downloading file: {}", fileName, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isValidFileName(String fileName) {
        // Check for path traversal attempts
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            return false;
        }

        // Check file extension
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }


    private String determineContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "txt" -> "text/plain";
            case "csv" -> "text/csv";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "zip" -> "application/zip";
            default -> "application/octet-stream";
        };
    }

}
