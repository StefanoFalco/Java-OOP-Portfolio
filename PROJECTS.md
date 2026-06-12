# Project Notes

This file documents the selection and cleanup decisions applied to build the public portfolio repository.

## Included projects

### oop-tv-series-database

Original folder: `exam-20230717-tvseriesdb`

Complete exam-style Java project focused on TV series, seasons, episodes, users, reviews and statistics.

Status: passing Maven tests.

### oop-project-review-scheduler

Original folder: `exam-20240125-project-review`

Exam-style Java project involving group management, time slots, preferences, review assignment and statistics.

Status: passing Maven tests.

### oop-university-management

Original folder: `lab1-university`

Introductory OOP case study covering students, courses, exams, averages, awards and logging.

Portfolio cleanup included compatibility and logic fixes for ranking and logging behavior.

Status: passing Maven tests.

### oop-medical-clinic

Original folder: `lab5-clinic`

OOP case study covering patients, doctors, doctor-patient assignment, file loading, custom exceptions and statistics.

Portfolio cleanup included fixes to doctor modeling, file parsing, patient assignment and statistical aggregation.

Status: passing Maven tests.

### oop-social-network

Original folder: `lab6 - social`

OOP case study covering people, friendships, groups, graph-like relationships, statistics and Swing GUI login.

Portfolio cleanup included fixes to person modeling, group/friendship logic, statistics and GUI login behavior.

Status: passing Maven tests.

## Excluded projects

### huts_esercitazione

Excluded because it was an incomplete or intermediate version with many placeholder returns.

### lab5-clinic-pt2

Excluded because it was an incomplete duplicate/intermediate version of the clinic project.

### lab6-social

Excluded because it was a duplicate of the social-network project with more placeholder returns and a suspicious syntax pattern detected during inventory.

### lab4-huts

Temporarily excluded from the first public version. It can be reviewed, fixed, tested and added later as `oop-mountain-huts`.

## Cleanup applied

The following items were excluded from the public workspace:

- nested `.git` directories;
- Maven `target/` folders;
- compiled `.class` files;
- Eclipse files `.project` and `.classpath`;
- `.settings` folders;
- GitLab CI files;
- OS junk files;
- temporary diagnostic logs.
