package com.recruitment.demo.repository;

import com.recruitment.demo.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}