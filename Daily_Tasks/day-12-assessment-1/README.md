# Day 12 — Assessment 1: Core Java Checkpoint

## Overview
A plain Java console-based Kanban Task Manager demonstrating object-oriented programming, exception handling, and collections.

## Features
1. Create a task with an automatically assigned ID.
2. View all tasks and their current statuses.
3. Move a task between `TODO`, `IN_PROGRESS`, and `DONE`.
4. Handle invalid menu choices, task IDs, titles, and statuses.

## Files
- `Task.java` — task model and status validation.
- `TaskService.java` — task creation, lookup, listing, and movement.
- `Main.java` — interactive console menu.
- `TaskServiceTest.java` — seven automated checks.

## Compile and run
Open PowerShell in this directory and run:

```powershell
javac Task.java TaskService.java Main.java
java Main
```

## Run automated tests

```powershell
javac Task.java TaskService.java TaskServiceTest.java
java -ea TaskServiceTest
```

Expected output:

`All 7 TaskService tests passed!`

## Demo flows
- Create tasks and verify their assigned IDs.
- View all tasks.
- Move a task from `TODO` to `IN_PROGRESS` and verify the updated status.

## Scope
This is an in-memory console exercise. Tasks are not persisted after the program exits. It demonstrates two core Kanban workflows and is not a replacement for the full Spring Boot application.
