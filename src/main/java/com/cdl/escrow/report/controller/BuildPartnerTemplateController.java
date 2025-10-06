package com.cdl.escrow.report.controller;

import com.cdl.escrow.report.service.BuildPartnerTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/build-partners/templates")
@Slf4j
@RequiredArgsConstructor
public class BuildPartnerTemplateController {

    private final BuildPartnerTemplateService service;

    @GetMapping("/master.xlsx")
    public ResponseEntity<byte[]> excel() {
        byte[] file = service.buildExcelTemplate();
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        h.setContentDisposition(ContentDisposition.attachment().filename("build-partner-master-template.xlsx").build());
        return ResponseEntity.ok().headers(h).body(file);
    }

    @GetMapping("/letter.docx")
    public ResponseEntity<byte[]> letter() {
        byte[] file = service.buildLetterTemplate();
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        h.setContentDisposition(ContentDisposition.attachment().filename("build-partner-letter-template.docx").build());
        return ResponseEntity.ok().headers(h).body(file);
    }
}
