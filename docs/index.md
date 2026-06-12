# Stitch 🪡

**Effortlessly Sew Your Room Persistence Layer Together**

Stitch is a Kotlin compiler plugin designed to eliminate the repetitive boilerplate code associated with the Room persistence library. It automatically generates high-level components based on your DAOs and entities, allowing you to focus on building your app's unique features.

---

## 🧐 Why Stitch?

Managing a database layer often involves writing extensive boilerplate:
- **Repositories** to abstract data access.
- **Repository Implementations** that call Room DAOs.
- **Operation classes** for specific business logic.
- **Dependency Injection** bindings to connect everything.

Stitch automates all of this. By analyzing your Room components, it "sews" together the necessary classes, ensuring consistency and saving hours of development time.

---

## ✨ Core Value Propositions

### 🛠️ Automatic Code Generation
Say goodbye to manual repository implementation. Stitch creates interfaces and classes that perfectly match your Room DAOs.

### 📦 Kotlin Multiplatform (KMP) Ready
Built for the modern Android ecosystem, Stitch fully supports KMP and the latest features of Room3.

### 💉 First-Class DI Support
Generate platform-agnostic dependency injection modules for Metro, Hilt, or custom frameworks automatically.

### 🔄 Coroutines & Flow
First-class support for asynchronous data streams using Kotlin Coroutines and Flow out of the box.

---

## 🚀 Get Started

Ready to simplify your Room layer?

1.  **[Installation](releases/implementation.md)** - Add the plugin to your project.
2.  **[Reference Guide](reference.md)** - Explore available annotations and configurations.
3.  **[Changelog](releases/changelog/1.0.0-alpha02.md)** - See what's new in the latest version.

---

## 🤝 Community & Support

- **GitHub:** [teogor/stitch](https://github.com/teogor/stitch)
- **Issues:** [Report a bug or suggest a feature](https://github.com/teogor/stitch/issues)
- **Discussions:** [Join the conversation](https://github.com/teogor/stitch/discussions)

---

## 🩷 Support the Project

If you find Stitch useful, please consider [starring the repository](https://github.com/teogor/stitch) or [sponsoring the project](sponsor.md)!
