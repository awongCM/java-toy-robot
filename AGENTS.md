# AGENTS.md

## Cursor Cloud specific instructions

This is a single-module Java 21 / Maven CLI app (the "Toy Robot" challenge). There is only one service: the command-line program.

- Build/test/run commands are documented in `README.md` (`mvn test`, `mvn compile exec:java`). The app's main class is `com.andywong.cli.TextInputInterface`.
- There is no separate lint step; correctness is enforced by the JUnit 5 test suite (`mvn test`, 78 tests).
- The CLI reads commands from stdin, one per line, and **stops at the first blank line**. When running non-interactively, pipe input that ends with a blank line, e.g. `printf 'PLACE 0,0,NORTH\nMOVE\nREPORT\n\n' | mvn -q compile exec:java`. `REPORT` is what prints output; without it the program produces nothing.
- Maven (3.8.x) is a system dependency provided by the VM image, not by the repo. Java 21 is the required JDK.
