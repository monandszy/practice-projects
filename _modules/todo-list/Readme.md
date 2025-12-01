# ToDo List (CLI + JDBC)

This module is a Java practice project implementing a small command-driven ToDo application that talks to a PostgreSQL database over JDBC. It focuses on a simple command language, query building, and database interaction using plain JDBC.

## What this project does
- Parses simple command strings (single-line, semicolon-separated) and executes SQL commands against a PostgreSQL database.
- Supports create/read/update/delete operations on tasks, marking tasks completed, and grouped reads.
- Provides two command processors: a list-driven processor (useful for smoke examples) and an interactive scanner-based processor for CLI usage.

## Key classes & packages
- `code.ToDoRunner` — main runner with two modes:
  - List mode: runs a hard-coded list of example commands.
  - Interactive mode: reads lines from `System.in` and processes them.
  - Default DB connection in `ToDoRunner.getDatabaseRunner()` points to `jdbc:postgresql://localhost:5432/todo_list` with username `postgres` and password `meow` (adjust before use).
- `code.databaseRunner.DatabaseRunnerImpl` — JDBC runner that opens connections, executes prepared statements, formats and prints query results, and can create/alter tables.
- `code.commandBuilder` — command parsing and preparation components:
  - `CommandBuilderImpl` orchestrates splitting, type detection, argument extraction, query selection, and argument/query adjustment.
  - Helpers include `Splitter`, `ArgumentExtractor`, `ArgumentAdjuster`, `QueryFactory` and `QueryAdjuster`.
- `code.CommandProcessorScannerImpl` / `code.CommandProcessorListImpl` — processors that feed parsed `Command` objects to the `DatabaseRunner`.
- `code.model` — small model objects: `Command`, `TableData`, `Arguments` describing domain data.

## Command format (examples)
- Single-line commands are semicolon-separated. The project contains sample commands used by `ToDoRunner`:

```
DELETE ALL;
CREATE;NAME=TASK1;DESCRIPTION=SOME DESCRIPTION1;DEADLINE=11.02.2021 20:10;PRIORITY=0
READ;NAME=TASK5
COMPLETED;NAME=TASK1
READ ALL;SORT=DEADLINE,DESC
UPDATE;NAME=TASK2;DESCRIPTION=SOME DESCRIPTION NEW;DEADLINE=12.02.2021 10:10;PRIORITY=81
READ GROUPED;
DELETE;NAME=TASK2
```

- The `CommandBuilder` handles parsing the above into `Command` objects and mapping them to parameterized SQL queries.

Database
- The code expects a PostgreSQL instance reachable at the JDBC URL used in `ToDoRunner`.
- Example DB URL in code: `jdbc:postgresql://localhost:5432/todo_list` (user `postgres`, password `meow`).
- The `DatabaseRunnerImpl` includes a `createTable(String createQuery, String alterQuery)` helper; to initialise the schema you can call this from `ToDoRunner` (a commented call exists in the source).
- SQL reference folder: `src/main/java/sqlProject/sql` and `W4 Project 1 SQL.pdf` contain sample SQL and schema design.
