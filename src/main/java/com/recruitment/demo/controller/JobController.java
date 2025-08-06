package com.recruitment.demo.controller;

import com.recruitment.demo.entity.Job;
import com.recruitment.demo.services.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        try {
            return ResponseEntity.ok(jobService.createJob(job));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<?> applyForJob(@PathVariable Long jobId) {
        try {
            jobService.applyForJob(jobId);
            return ResponseEntity.ok("Application submitted");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/{jobId}/applicants")
    public ResponseEntity<?> getApplicantsForJob(@PathVariable Long jobId) {
        try {
            return ResponseEntity.ok(jobService.getApplicantsForJob(jobId));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}