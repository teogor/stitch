<p align="center">
  <img src="https://raw.githubusercontent.com/teogor/stitch/main/docs/images/logo.png" width="128" height="128" alt="Stitch Logo">
</p>

<h1 align="center">Stitch 🪡</h1>

<p align="center">
  <strong>Effortlessly Sew Your Room Persistence Layer Together</strong>
</p>

[![Maven Central](https://img.shields.io/maven-central/v/dev.teogor.stitch/stitch-common?logo=apache-maven&color=007ec6)](https://central.sonatype.com/search?q=dev.teogor.stitch)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Build](https://img.shields.io/github/actions/workflow/status/teogor/stitch/publish.yml?branch=main&logo=github-actions&logoColor=white)](https://github.com/teogor/stitch/actions)
[![License](https://img.shields.io/github/license/teogor/stitch?color=blue)](LICENSE)

---

Tired of writing repetitive Room boilerplate code? **Stitch** is a Kotlin compiler plugin that
automatically generates Repositories, Repository Implementations, and Operations based on your Room
DAOs and entities.

## 🚀 Quick Start

### 1. Apply the Plugin

Add the following to your root `build.gradle.kts`:

```kotlin
plugins {
    id("dev.teogor.stitch") version "1.0.0-alpha03"
}
```

And in your module's `build.gradle.kts`:

```kotlin
plugins {
    id("dev.teogor.stitch")
}

stitch {
    generatedPackageName = "com.your.app.generated"
}
```

### 2. Annotate your DAO

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUsers(): Flow<List<User>>
}
```

Stitch will automatically generate a `UserRepository` and its implementation for you!

## ✨ Key Features

- 🛠️ **Automatic Code Generation:** Generates Repositories and Operations from Room components.
- 📦 **KMP Ready:** Full support for Kotlin Multiplatform projects and Room3.
- 💉 **DI Integration:** Seamlessly integrates with Metro and other DI frameworks.
- 🔄 **Asynchronous Agility:** Built-in support for Coroutines and Flow.
- 🧩 **Customizable:** Fine-tune package names, suffixes, and more via Gradle DSL.

## 📚 Documentation

For detailed guides and reference, visit our [Documentation Site](https://source.teogor.dev/stitch).

- [Getting Started](https://source.teogor.dev/stitch/getting-started)
- [Annotations Reference](https://source.teogor.dev/stitch/reference)
- [Multi-platform Setup](https://source.teogor.dev/stitch/releases/implementation)

## 🛠️ Development

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for setup
instructions and technical requirements.

To set up your local development environment with Git hooks, run:

```bash
./gradlew installPreCommit
```

## 🩷 Support the Project

- ⭐ **Star this repository** to show your support!
- 👤 **Follow [teogor](https://github.com/teogor)** for more open-source tools.
- 🤝 **[Sponsor me](https://github.com/sponsors/teogor)** to help maintain this project.

## 📄 License

```text
Copyright 2024 teogor (Teodor Grigor)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
