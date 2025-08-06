package com.recruitment.demo.services;

import com.recruitment.demo.entity.Job;
import com.recruitment.demo.entity.JobApplication;
import com.recruitment.demo.entity.Role;
import com.recruitment.demo.entity.User;
import com.recruitment.demo.repository.JobApplicationRepository;
import com.recruitment.demo.repository.JobRepository;
import com.recruitment.demo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, JobApplicationRepository jobApplicationRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }

    public Job createJob(Job job) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isPresent() && user.get().getRole() == Role.RECRUITER) {
            job.setPostedBy(user.get());
            return jobRepository.save(job);
        }
        throw new IllegalStateException("Only recruiters can post jobs");
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void applyForJob(Long jobId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Optional<Job> job = jobRepository.findById(jobId);
        if (user.isPresent() && job.isPresent() && user.get().getRole() == Role.APPLICANT) {
            JobApplication application = new JobApplication(job.get(), user.get());
            jobApplicationRepository.save(application);
        } else {
            throw new IllegalStateException("Invalid job or user not an applicant");
        }
    }

    public List<JobApplication> getApplicantsForJob(Long jobId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Optional<Job> job = jobRepository.findById(jobId);
        if (user.isPresent() && job.isPresent() && user.get().getRole() == Role.RECRUITER && job.get().getPostedBy().getId().equals(user.get().getId())) {
            return job.get().getApplications().stream().toList();
        }
        throw new IllegalStateException("Only the job's recruiter can view applicants");
    }
}