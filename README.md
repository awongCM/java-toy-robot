# java-toy-robot
Toy Robot Challenge

## Instructions

### Run the app

From the project root:

```bash
mvn compile exec:java
```

Then enter commands (one per line) as described in the [Robot Challenge spec](https://github.com/luke-zhou/robot-challenge). End input with a blank line.

Example:

```
PLACE 0,0,NORTH
MOVE
REPORT
```

### Run tests

```bash
mvn test
```

## Environment

- Java 21
- Maven
- JUnit 5

## Historical notes

For context on why the app and test suite were unreliable before the Phase 1 refactor (broken `mvn test`, flaky tests, `PLACE` parsing, IntelliJ-only run instructions), see [docs/HISTORICAL_NOTES.md](docs/HISTORICAL_NOTES.md).
