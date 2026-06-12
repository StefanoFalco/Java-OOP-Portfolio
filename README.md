# Java Object-Oriented Programming Portfolio

Curated collection of Java object-oriented programming case studies developed from academic coursework and exam-style exercises.

The repository groups multiple independent Java/Maven projects focused on domain modeling, collections, maps, exception handling, data aggregation, statistics, GUI interaction, and test-driven implementation.

This is a cleaned portfolio version: duplicated folders, incomplete intermediate versions, nested Git repositories, build artefacts, compiled classes, IDE metadata and original CI files have been removed.

## Included projects

| Folder | Case study | Main topics | Status |
|---|---|---|---|
| `oop-tv-series-database` | TV Series Database | domain modeling, seasons, episodes, users, reviews, statistics | Passing tests |
| `oop-project-review-scheduler` | Project Review Scheduler | groups, time slots, preferences, review assignment, statistics | Passing tests |
| `oop-university-management` | University Management | students, courses, exams, averages, awards, logging | Passing tests |
| `oop-medical-clinic` | Medical Clinic | patients, doctors, assignments, file loading, exception handling, statistics | Passing tests |
| `oop-social-network` | Social Network | people, friendships, groups, graph-like relations, statistics, GUI login | Passing tests |

## Repository structure

    .
    |-- README.md
    |-- PROJECTS.md
    |-- TESTING.md
    |-- oop-tv-series-database/
    |-- oop-project-review-scheduler/
    |-- oop-university-management/
    |-- oop-medical-clinic/
    |-- oop-social-network/
    |-- .github/workflows/java-ci.yml
    |-- .gitignore
    |-- .gitattributes

## Skills demonstrated

- Java programming
- Object-oriented design
- Domain modeling
- Collections and maps
- Stream-based data aggregation
- Exception handling
- Maven project management
- Unit-test driven implementation
- Basic Swing GUI handling
- Clean repository curation for public portfolio use

## Build and test

Each project is independent.

From a project folder:

    mvn clean test

Example:

    cd oop-tv-series-database
    mvn clean test

To test all projects locally, run the commands listed in TESTING.md.

## Continuous integration

The repository includes a GitHub Actions workflow that runs Maven tests on all included projects using Java 17.

## Publication note

This repository is intended as a professional portfolio version of academic Java/OOP exercises.

The following material is intentionally excluded:

- nested `.git` folders;
- Maven `target/` folders;
- compiled `.class` files;
- IDE metadata such as `.project`, `.classpath`, `.settings`;
- GitLab CI files from the original coursework environment;
- incomplete duplicate project versions.

## License

No open-source license has been selected. Reuse, redistribution, or derivative work should be agreed with the author.
