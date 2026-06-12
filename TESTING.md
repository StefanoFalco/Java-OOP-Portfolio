# Testing

Each project is an independent Maven project.

## Requirements

- Java 17
- Apache Maven 3.x

## Test all projects locally

From the repository root:

    cd oop-tv-series-database
    mvn clean test
    cd ..

    cd oop-project-review-scheduler
    mvn clean test
    cd ..

    cd oop-university-management
    mvn clean test
    cd ..

    cd oop-medical-clinic
    mvn clean test
    cd ..

    cd oop-social-network
    mvn clean test
    cd ..

## Verified projects

The following projects were locally verified with `mvn clean test` before publication:

| Project | Status |
|---|---|
| `oop-tv-series-database` | Passing |
| `oop-project-review-scheduler` | Passing |
| `oop-university-management` | Passing |
| `oop-medical-clinic` | Passing |
| `oop-social-network` | Passing |

## Continuous integration

The GitHub Actions workflow in `.github/workflows/java-ci.yml` runs the same Maven test command for each project.
