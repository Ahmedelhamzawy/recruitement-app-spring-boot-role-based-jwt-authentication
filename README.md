# Recruitment Platform API

A Spring Boot application with JWT-based authentication for a recruitment platform, allowing recruiters to post jobs and applicants to apply for them. The system uses a MySQL database to manage users, jobs, and job applications, with secure role-based access control.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Entities and Relationships](#entities-and-relationships)
- [Authentication](#authentication)

## Overview
The Recruitment Platform API enables recruiters to create job postings and applicants to apply for jobs. Users are authenticated using JSON Web Tokens (JWT) with role-based access (`APPLICANT` or `RECRUITER`). The system uses a MySQL database with entities for users, jobs, and job applications, supporting one-to-many and many-to-many relationships.

## Features
- **User Management**:
  - Register and authenticate users with roles (`APPLICANT`, `RECRUITER`).
  - Secure password storage using BCrypt.
- **Job Management**:
  - Recruiters can create job postings.
  - Applicants can apply to jobs.
  - Recruiters can view applicants for their jobs.
- **Security**:
  - JWT-based authentication for secure endpoint access.
  - Role-based authorization to restrict actions (e.g., only recruiters can post jobs).
- **Data Management**:
  - MySQL database with tables for `user`, `job`, and `job_application`.
  - Efficient data retrieval using JPA relationships.

## Entities and Relationships

### Entities
1. **User**
   - **Table**: `user`
   - **Purpose**: Represents users (applicants or recruiters).

2. **Job**
   - **Table**: `job`
   - **Purpose**: Represents a job posting created by a recruiter.

3. **JobApplication**
   - **Table**: `job_application`
   - **Purpose**: Represents the many-to-many relationship between applicants and jobs.

### Relationships
- **One-to-Many (User to Job)**:
  - A `User` (recruiter) can post many `Job` entities (`postedJobs`).
  - A `Job` is posted by one `User` (`postedBy`).
  - Implemented via `job.posted_by` foreign key referencing `user.id`.
- **Many-to-Many (User to Job via JobApplication)**:
  - A `User` (applicant) can apply to many `Job` entities.
  - A `Job` can have many applicants.
  - Managed by the `job_application` table, linking `user.id` (applicant) and `job.id`.
- **One-to-Many (Job to JobApplication)**:
  - A `Job` can have many `JobApplication` entries (`applications`).
  - A `JobApplication` belongs to one `Job`.
- **One-to-Many (User to JobApplication)**:
  - A `User` (applicant) can have many `JobApplication` entries (`applications`).
  - A `JobApplication` belongs to one `User`.

### Entity Diagram
```
User
  |
  | (postedBy, One-to-Many)
  |
Job
  |
  | (job, One-to-Many)
  |
JobApplication
  |
  | (applicant, Many-to-One)
  |
User


## Authentication
- **JWT Authentication**:
  - Users authenticate via `/api/auth/login`, receiving a JWT token.
  - The token must be included in the `Authorization` header as `Bearer <token>` for protected endpoints.
- **Role-Based Access**:
  - `APPLICANT`: Can apply to jobs (`POST /api/jobs/{jobId}/apply`).
  - `RECRUITER`: Can create jobs (`POST /api/jobs`) and view applicants (`GET /api/jobs/{jobId}/applicants`).
- **Security Configuration**:
  - Uses Spring Security with a stateless session policy.
  - Public endpoints: `/api/auth/**`, `/api/test/**`, `/api/jobs` (GET).
  - Protected endpoints require JWT and appropriate roles.
