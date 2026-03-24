package com.reverseresume.controller;

import com.reverseresume.dto.ResumeDTO;
import com.reverseresume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/skills/analyze")
    public ResponseEntity<?> analyzeSkills(
            @RequestParam Long jobId,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        List<Map<String, Object>> analysis = resumeService.analyzeSkillGap(token, jobId);
        return ResponseEntity.ok(analysis);
    }

    @PostMapping("/resume/generate")
    public ResponseEntity<ResumeDTO> generateResume(
            @RequestParam Long jobId,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        ResumeDTO resume = resumeService.generateResume(token, jobId);
        return ResponseEntity.ok(resume);
    }
 
    @GetMapping("/resume/download")
    public ResponseEntity<byte[]> downloadResume(
            @RequestParam Long jobId,
            @RequestHeader("Authorization") String authHeader) throws Exception {

        String token = authHeader.replace("Bearer ", "");

        ResumeDTO resume = resumeService.generateResume(token, jobId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Name: " + resume.getName()));
        document.add(new Paragraph("Email: " + resume.getEmail()));
        document.add(new Paragraph("Phone: " + resume.getPhone()));
        document.add(new Paragraph("Summary: " + resume.getSummary()));

        document.add(new Paragraph("\nSkills"));
        if (resume.getSkills() != null) {
            resume.getSkills().forEach(skill ->
                    document.add(new Paragraph("- " + skill)));
        }

        document.add(new Paragraph("\nExperience"));
        document.add(new Paragraph("\nExperience"));
        if (resume.getExperience() != null) {
            resume.getExperience().forEach(exp ->
                    document.add(new Paragraph("- " + exp)));
        }

        document.add(new Paragraph("\nEducation"));
        if (resume.getEducation() != null) {
            resume.getEducation().forEach(edu ->
                    document.add(new Paragraph("- " + edu)));
        }

        document.close();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=resume.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }
    @GetMapping("/resume/history")
    public ResponseEntity<?> getResumeHistory(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(resumeService.getHistory(token));
    }
}
