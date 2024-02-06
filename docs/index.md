# Stitch 🪡

## Overview
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/dev.teogor.stitch/bom.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=g%3Adev.teogor.stitch+a%3Aapi&smo=true)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Profile](https://source.teogor.dev/badges/teogor-github.svg)](https://github.com/teogor)
[![Portfolio](https://source.teogor.dev/badges/teogor-dev.svg)](https://source.teogor.dev)

### Effortlessly Sew Your Room Persistence Layer Together

Tired of writing repetitive Room boilerplate code? Stitch, your friendly Kotlin compiler plugin, is here to save the day! It automatically generates essential components based on your Room DAOs and entities, freeing you to focus on your app's core logic.

**Stitch offers a tapestry of benefits:**

* **Automatic Code Generation:** Say goodbye to repetitive boilerplate! Stitch generates Repositories, Repository Implementations, and Operations based on your Room DAOs and entities.
* **Seamless Hilt Integration:** Enjoy a smooth blend of dependency injection with automatically created Hilt modules, ensuring your code remains clean and organized.
* **Tailored to Your Needs:** Customize generated code to perfectly fit your project's unique requirements, ensuring a truly bespoke solution.
* **Asynchronous Agility:** Embrace the power of coroutines with Stitch's efficient data handling, keeping your app responsive and nimble.
* **KSP-powered Efficiency:** Leverage the magic of Kotlin Symbol Processing for accurate and optimized code generation, ensuring seamless integration.

**Who is Stitch for?**

* Developers seeking a **streamlined and efficient approach to Room persistence**.
* Teams aiming for **consistent and maintainable code across their projects**.
* Anyone who wants to **weave their app's magic without getting tangled in boilerplate**.

## Features:

* **Automatic Generation:** Stitch generates essential code components based on your Room DAOs and entities, including:
  * **Repositories:** Provide a high-level interface for interacting with your data.
  * **Repository Implementations:** Execute CRUD operations efficiently using Room's APIs.
  * **Operations:** Represent specific data access actions with clear signatures.
* **Flexible Customization:** Configure generated code to match your project's needs with options like:
  * Customizing base class or interface for repositories.
  * Specifying naming conventions for generated components.
  * Excluding specific entities or DAOs from generation.
* **Dependency Injection Integration:** Stitch seamlessly integrates with Hilt, automatically generating modules for injected repository instances.
* **Coroutine-friendly Operations:** Stitch supports asynchronous data access using coroutines, ensuring a responsive and efficient user experience.
* **Efficient KSP Integration:** Leverages Kotlin Symbol Processing for accurate and optimized code generation based on your project's specific setup.

## Find this repository useful? 🩷

* Support it by joining __[stargazers](https://github.com/teogor/stitch/stargazers)__ for this
  repository. 📁
* Get notified about my new projects by __[following me](https://github.com/teogor)__ on GitHub. 💻
* Interested in sponsoring me? [Support me](sponsor.md) on GitHub! 🤝

# License

```xml
Designed and developed by 2024 teogor (Teodor Grigor)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
