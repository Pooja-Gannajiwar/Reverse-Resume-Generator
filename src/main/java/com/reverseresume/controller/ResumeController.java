package com.reverseresume.controller;

import com.reverseresume.dto.ResumeDTO;
import com.reverseresume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/resume/history")
    public ResponseEntity<?> getResumeHistory(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(resumeService.getHistory(token));
    }
}
