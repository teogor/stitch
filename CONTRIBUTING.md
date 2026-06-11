# Contributing to Stitch 🪡

Thank you for your interest in contributing to Stitch! To maintain high code quality and a clean project history, we follow a strict technical workflow.

## Technical Requirements (SOP)

### 1. Isolated Branches
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
- `./gradlew :app:kspDebugKotlin`: Verifies that Stitch KSP code generation is working correctly.
- `./gradlew :app:assembleDebug`: Ensures the project compiles successfully.

### 4. Finalization & Pull Requests
- **Remote Push:** Push your branch to the remote repository once the task is complete and all quality gates pass.
- **PR Creation:** Create a Pull Request with a descriptive title and a body explaining the changes.
- **Clean Cleanup:** Once a PR is merged, the feature branch should be deleted.

## Development Setup
- Ensure you have the Android SDK installed.
- Stitch uses Kotlin Symbol Processing (KSP) extensively. Always run a build or KSP task after modifying DAOs or Entities to see the generated code.
