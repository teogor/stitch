# Contributing to Stitch 🪡

Thank you for your interest in contributing to Stitch! To maintain high code quality and a clean project history, we follow a strict technical workflow.

## Technical Requirements (SOP)

Stitch uses `pre-commit` to ensure code quality and consistency. To set up your environment, run:

```bash
./gradlew installPreCommit
```

This will install hooks that run during `git commit` (Spotless, API Dump) and `git push` (All Tests).

These requirements apply to all contributors. If you are using an AI agent for development, provide the following prompt in your planning session to ensure compliance:

### AI Planning Prompt
```md
### Technical Requirements (Follow Strictly)
1. **Isolated Branches:** Every distinct feature or cleanup task must have its own branch. Switch branches for unrelated tasks. Never work on `main`.
2. **Incremental Implementation:**
   - Implement in small, logical steps.
   - Update `task.artifact.md` and `implementation_plan.artifact.md` after every step.
   - Commit after every step.
   - **Important:** If a step changes code that requires documentation, update the docs (README, etc.) in the *same* commit.
3. **Quality Gates (Run after every commit/step):**
   - `./gradlew spotlessApply`
   - `./gradlew apiDump`
   - `./gradlew :catalog:androidApp:kspDebugKotlin` (to verify Stitch generation)
4. **Finalization:** Push the branch and create a GitHub PR with a descriptive title and body before moving to the next task or merging.
5. **Task/Plan Integration:** These technical steps (spotless, apiDump, branch creation) must be explicitly listed as line items in the `task.artifact.md` and `implementation_plan.artifact.md` that you generate.

### The Job
[Describe your idea or feature request here]
```

## Workflow Details

### 1. Development Setup
- Ensure you have the Android SDK installed.
- **Install Hooks:** Run `./gradlew installPreCommit` to install the required Git hooks.
- **Manual Verification:** You can manually run the quality gates using `./gradlew spotlessApply`, `./gradlew apiDumpAll`, and `./gradlew allTests`.

### 2. Isolated Branches
- **Branch per Task:** Every distinct feature, cleanup, or bug fix must have its own dedicated branch (e.g., `feature/x`, `cleanup/y`, `docs/z`).
- **No `main` Commits:** Never work directly on the `main` branch. All changes must be merged via Pull Requests.

### 2. Incremental Implementation
- **Atomic Steps:** Implement changes in small, logical increments.
- **Continuous Documentation:** If a change affects functionality, update the relevant documentation (e.g., `README.md`, `DEMO.md`) in the **same commit** as the code change.
- **Commit Often:** Use descriptive commit messages with standard prefixes (e.g., `feat:`, `fix:`, `docs:`, `chore:`).

### 3. Quality Gates
After every step or commit, the following commands **must** be run to ensure project integrity:
- `./gradlew spotlessApply`: Ensures code style compliance.
- `./gradlew apiDump`: Verifies binary compatibility and API consistency across library modules.
- `./gradlew :catalog:androidApp:kspDebugKotlin`: Verifies that Stitch KSP code generation is working correctly.
- `./gradlew :catalog:androidApp:assembleDebug`: Ensures the project compiles successfully.

### 4. Finalization & Pull Requests
- **Remote Push:** Push your branch to the remote repository once the task is complete and all quality gates pass.
- **PR Creation:** Create a Pull Request with a descriptive title and a body explaining the changes.
- `atomic commit`: Commit often with descriptive messages.

## Development Setup
- Stitch uses Kotlin Symbol Processing (KSP) extensively. Always run a build or KSP task after modifying DAOs or Entities to see the generated code.
