package com.reverseresume.service;

import com.reverseresume.dto.ResumeDTO;
import com.reverseresume.model.Job;
import com.reverseresume.model.Resume;
import com.reverseresume.model.User;
import com.reverseresume.repository.JobRepository;
import com.reverseresume.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResumeService {

    @Autowired
    private UserService userService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    public List<Map<String, Object>> analyzeSkillGap(String token, Long jobId) {
        User user = userService.getUserFromToken(token);
        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        List<String> userSkills = user.getSkills() != null ? user.getSkills() : new ArrayList<>();
        List<String> requiredSkills = job.getRequiredSkills() != null ? job.getRequiredSkills() : new ArrayList<>();

        List<Map<String, Object>> analysis = new ArrayList<>();
        for (String required : requiredSkills) {
            Map<String, Object> entry = new HashMap<>();
            boolean hasSkill = userSkills.stream().anyMatch(s -> s.equalsIgnoreCase(required));
            entry.put("name", required);
            entry.put("requiredLevel", "Intermediate");
            entry.put("userLevel", hasSkill ? "Intermediate" : "None");
            entry.put("gap", !hasSkill);
            analysis.add(entry);
        }
        return analysis;
    }

    public ResumeDTO generateResume(String token, Long jobId) {
        User user = userService.getUserFromToken(token);
        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        ResumeDTO dto = new ResumeDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone("+1-000-000-0000");
        dto.setSummary("Motivated professional targeting the role of " + job.getTitle() + " at " + job.getCompany() + ".");
        dto.setSkills(user.getSkills());

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setJob(job);
        resume.setContent(dto.toString());
        resumeRepository.save(resume);

        return dto;
    }

    public List<Resume> getHistory(String token) {
        User user = userService.getUserFromToken(token);
        return resumeRepository.findByUser(user);
    }
}
