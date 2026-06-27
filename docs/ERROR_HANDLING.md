# Error handling

This project separates **parsing errors** (fail fast) from **domain no-ops** (silently ignored per the challenge spec).

## Layer summary

| Layer | Package | Invalid input | Mechanism |
|-------|---------|---------------|-----------|
| CLI / parsing | `com.andywong.cli` | Malformed `PLACE`, unknown command, unreadable file | `CommandParseException` |
| Application | `com.andywong.application` | Off-table move, invalid placement, pre-`PLACE` command | Swallows domain exceptions; no-op |
| Domain | `com.andywong.domain` | Out-of-bounds placement, fall-off move, unplaced robot | `IllegalArgumentException` / `IllegalStateException` (internal) |

## CLI — fail fast

`CommandParser` and `TextInputInterface` throw `CommandParseException` when:

- The command token is not one of `PLACE`, `MOVE`, `LEFT`, `RIGHT`, `REPORT`
- `PLACE` is missing parameters or has the wrong shape
- Coordinates are not integers
- A command file cannot be read

`TextInputInterface.main` catches these and prints `Something went wrong: …` so a bad line does not crash the whole session.

## Application — spec silent ignore

`RobotSimulator` wraps `Table` and catches:

- `IllegalArgumentException` from invalid `place` (out of bounds)
- `IllegalStateException` from `move` / `left` / `right` when unplaced or would fall

`report()` returns `Optional.empty()` when the robot is not placed.

## Domain — throws internally

`Table` validates placement and movement with standard Java exceptions. Callers outside tests should go through `RobotSimulator`, which translates throws into spec-compliant ignores.

Direct use of `Table` in unit tests is intentional: tests assert the internal contract before the simulator layer applies spec semantics.
