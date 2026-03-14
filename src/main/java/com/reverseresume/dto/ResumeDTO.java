package com.reverseresume.dto;

import java.util.List;
import java.util.Map;

public class ResumeDTO {
    private String name;
    private String email;
    private String phone;
    private String summary;
    private List<String> skills;
    private List<Map<String, String>> experience;
    private List<Map<String, String>> education;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public List<Map<String, String>> getExperience() { return experience; }
    public void setExperience(List<Map<String, String>> experience) { this.experience = experience; }
    public List<Map<String, String>> getEducation() { return education; }
    public void setEducation(List<Map<String, String>> education) { this.education = education; }
}
