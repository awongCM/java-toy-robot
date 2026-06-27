# AGENTS.md

## Cursor Cloud specific instructions

This is a single-module Java 21 / Maven CLI app (the "Toy Robot" challenge). There is only one service: the command-line program.

- Build/test/run commands are documented in `README.md` (`mvn test`, `mvn compile exec:java`). The app's main class is `com.andywong.cli.TextInputInterface`.
- There is no separate lint step; correctness is enforced by the JUnit 5 test suite (`mvn test`, 78 tests).
- The CLI reads commands from stdin, one per line, and **stops at the first blank line**. When running non-interactively, pipe input that ends with a blank line, e.g. `printf 'PLACE 0,0,NORTH\nMOVE\nREPORT\n\n' | mvn -q compile exec:java`. `REPORT` is what prints output; without it the program produces nothing.
- Maven (3.8.x) is a system dependency provided by the VM image, not by the repo. Java 21 is the required JDK.

## Overnight Cloud Agent runs

### Definition of done

1. `mvn test` passes (all 78 JUnit 5 tests).
2. Changes are limited to the smallest correct fix for a failing test or a clearly scoped improvement.
3. Work lands on a feature branch (`overnight/*` or `agent/*`), never on `main`.
4. Open a PR only when there is a concrete, reviewable change. If nothing needs fixing, stop without opening a PR.

### Commands

| Task | Command |
|------|---------|
| Install deps / compile tests | `mvn -q -B dependency:resolve test-compile` |
| Run full test suite | `mvn test` |
| Quick compile check | `mvn -q -B compile` |
| Run CLI (non-interactive) | `printf 'PLACE 0,0,NORTH\nMOVE\nREPORT\n\n' \| mvn -q compile exec:java` |

### PR conventions

- Branch: `overnight/YYYY-MM-DD-short-topic` or `agent/fix-<topic>`.
- Title prefix: `[overnight]` — e.g. `[overnight] Fix MOVE edge case at table boundary`.
- Body: what failed, what changed, test command run, and any follow-up for a human reviewer.
- Do not merge. Do not request reviewers unless explicitly instructed.

### Safety rails

See `.cursor/rules/overnight-agents.mdc`. In short: max 5 files per run, no workflow/secrets/major dependency changes, no destructive git commands, stop when blocked instead of guessing.

## Overnight Cloud Agent instructions

This repo is the **pilot** for scheduled overnight Cloud Agent runs.

### Definition of done

1. `mvn test` passes (78 JUnit 5 tests).
2. Changes are on a feature branch, not `main`.
3. A PR is opened only when there is a concrete fix or improvement worth review.
4. PR title prefix: `[overnight]` (e.g. `[overnight] Fix failing move boundary test`).

### Test and verify

```bash
mvn test
mvn -q compile exec:java   # interactive CLI; pipe input ending with blank line for non-interactive
```

### Branch and PR conventions

- Branch names: `overnight/YYYYMMDD-short-description` or `agent/short-description`.
- One focused change set per run; avoid drive-by refactors.
- PR body: what failed or what was audited, what changed, test command output summary.
- Never merge your own PR; leave it for human review.

### Scope for automated runs

- **In scope:** fix failing tests, small bug fixes, doc typos tied to failing behavior, minimal lint-free correctness fixes.
- **Out of scope:** dependency upgrades, CI/workflow changes, new features, multi-file refactors, changes to `pom.xml` unless required for a test fix.

### Safety

See `.cursor/rules/overnight-agents.mdc` for hard constraints. When unsure, stop and document blockers in the run transcript rather than shipping speculative changes.
