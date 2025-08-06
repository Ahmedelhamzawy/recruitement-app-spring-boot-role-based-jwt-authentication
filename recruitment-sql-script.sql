CREATE DATABASE IF NOT EXISTS `recruitment_directory`;
use `recruitment_directory`;
create table `user`(
 `id` int not null auto_increment,
 `name` varchar(100) not null,
 `email` varchar(250) not null unique,
 `password` varchar(100) not null,
 `role` ENUM('APPLICANT', 'RECRUITER') NOT NULL,
 `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
);
CREATE TABLE job (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    company VARCHAR(255),
    posted_by INT NOT NULL,
 `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (posted_by) REFERENCES user(id) ON DELETE CASCADE
);
CREATE TABLE job_application (
    id INT PRIMARY KEY AUTO_INCREMENT,
    job_id INT NOT NULL,
    applicant_id INT NOT NULL,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES job(id) ON DELETE CASCADE,
    FOREIGN KEY (applicant_id) REFERENCES user(id) ON DELETE CASCADE,
    UNIQUE (job_id, applicant_id)
);
