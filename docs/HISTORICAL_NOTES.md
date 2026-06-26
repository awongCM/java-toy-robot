# Historical Notes — Pre-Refactor Issues

This document records why the Toy Robot app was difficult to run, test, and maintain **before** the Phase 1 refactor (see PR #2). It is kept as context for future work (Phase 2 and beyond).

---

## Summary

The core domain logic (`Direction`, `Grid`, `Robot`) was largely correct, but **build tooling**, **CLI parsing**, and **test isolation** problems made the project appear broken when run outside IntelliJ.

| Symptom | Root cause |
|---------|------------|
| `mvn test` reported success but ran **0 tests** | JUnit 5 tests with no Surefire JUnit 5 configuration |
| Tests passed individually, failed as a suite | Shared singleton state (`Grid`, `Robot`) leaked between tests |
| `PLACE 0,0,NORTH` failed at runtime | CLI split parameters on `", "` (comma + space) instead of `","` |
| No clear way to run from the terminal | README only documented IntelliJ steps (with typos) |
| Java version confusion | `pom.xml` declared Java 20 in properties but compiled with Java 11 |

---

## 1. Tests did not run under Maven

### What we saw

```text
Tests run: 0, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Maven reported success, but **no tests executed**.

### Why

- Test classes used **JUnit Jupiter** (`org.junit.jupiter.api.Test`).
- `pom.xml` depended on both JUnit 4 and JUnit 5, with `junit-jupiter` pinned to `RELEASE`.
- **maven-surefire-plugin** was not configured for JUnit 5, so Surefire defaulted to the JUnit 4 provider and silently skipped Jupiter tests.

### Why it seemed to work in IntelliJ

IntelliJ runs JUnit 5 tests directly via its own test runner, bypassing Maven Surefire. That hid the fact that `mvn test` was a no-op.

---

## 2. Tests were order-dependent (flaky suite)

### What we saw

Developers noted in `GridTest` and `TextInputInterfaceTest`:

> need to investigate why running tests individually passes but failed when running together

### Why

`Robot` and `Grid` were implemented as **singletons**:

```java
// Robot.java — single shared instance for the entire JVM
private static Robot _instance;
public static Robot getInstance() { ... }

// Grid.java — same pattern
private static Grid _instance;
public static Grid getInstance() { ... }

// TextInputInterface.java — static reference held for the life of the process
public static final Grid grid = Grid.getInstance();
```

Each test that placed or moved the robot left state behind for the next test. `TextInputInterfaceTest` also captured `System.out` in a static buffer that was never cleared between tests.

`GridTest` used `new Grid()` per test, but every `Grid` still shared the **same** `Robot` singleton, so isolation was illusory.

---

## 3. PLACE command parsing failed on real input

### What we saw

Valid challenge input such as `PLACE 0,0,NORTH` could throw:

```text
The PLACE command must have three parameters: X,Y,DIRECTION
```

### Why

`TextInputInterface.placePosition()` split the parameter string on `", "` (comma **and** space):

```java
String[] locationParams = paramsAsString.split(", ");
```

The Robot Challenge format uses commas **without** spaces (`0,0,NORTH`). That split produced a single token, failed the length check, and rejected the command.

Some test paths happened to work because `getCommandWithOrWithoutParams()` round-tripped parameters through `Arrays.toString()`, which injects spaces (`"0, 0, NORTH"`). That masked the bug for tests that used the same workaround, but not for real stdin input.

---

## 4. App was not runnable from the command line

### What we saw

The README instructed:

1. Open IntelliJ
2. Right-click `java/com.andywong.cli/TextInputInferface` *(typo)*
3. Run `TextInputfae` *(typo)*

There was no documented Maven or `java` command. `pom.xml` had no `exec-maven-plugin` or `mainClass` configuration.

### Other CLI rough edges

- `getLineFeedFromInput()` printed the raw command list to stdout (`System.out.println(commands)`), polluting output during interactive use.
- A broad `catch (Exception e)` swallowed errors with a typo: `"something when wrong"`.

---

## 5. Build configuration inconsistencies

### `pom.xml` problems (pre-refactor)

| Setting | Value A | Value B |
|---------|---------|---------|
| `maven.compiler.source` / `target` in `<properties>` | `1.20` | — |
| `maven-compiler-plugin` `<source>` / `<target>` | — | `11` |

The project also listed `junit:junit:4.13.2` alongside `junit-jupiter:RELEASE`, which is non-reproducible and unnecessary when all tests use Jupiter.

---

## 6. What Phase 1 fixed (and what it did not)

### Fixed in Phase 1

- Maven Surefire + JUnit 5 → all **39 tests** run via `mvn test`
- Java 21 alignment and pinned JUnit version
- `mvn compile exec:java` to run the CLI
- `PLACE` parsing accepts `0,0,NORTH` (comma-separated, trimmed)
- `resetForTesting()` helpers and per-test stdout reset
- README updated with correct Maven instructions

### Intentionally deferred (Phase 2+)

- Removing singletons in favour of dependency injection
- Spec-aligned behaviour (ignore invalid moves and pre-`PLACE` commands instead of throwing)
- File-based input (`commands.txt`)
- Canonical integration tests from the official challenge examples
- Domain cleanup (`Location` `hashCode`, `Integer` vs `int`, injectable table size)

---

## References

- [Refactor roadmap](REFACTOR_ROADMAP.md) — phased plan; Phase 1–2 done, Phase 3 next
- [Robot Challenge spec (community mirror)](https://github.com/luke-zhou/robot-challenge)
- Phase 1 PR: https://github.com/awongCM/java-toy-robot/pull/2
