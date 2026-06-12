# Design Philosophy

Stitch was born from a simple observation: modern Android development with Room is powerful, but it involves an incredible amount of repetitive "glue code."

---

## 🧐 The "Boilerplate" Problem

In a typical Clean Architecture setup, you have:
1.  **Room Entities**: Database representations of your data.
2.  **Room DAOs**: Low-level database access logic.
3.  **Domain Models**: Clean representations of data for your business logic.
4.  **Repositories**: Interfaces that abstract the data source from the domain layer.
5.  **Repository Implementations**: The bridge that maps between Entities and Domain models.

For every single database table, you often end up writing the same pattern of interface, implementation, and mapping code. This is not only boring—it's a source of bugs and inconsistency.

---

## ✨ The Stitch Solution: "Effortless Sewing"

The name **Stitch** reflects our core mission: to automatically "sew" your database components into a cohesive, production-ready persistence layer.

### 🛡️ Type Safety First
Stitch leverages the Kotlin compiler and KSP to ensure that your generated code is 100% type-safe. If your Room DAOs change, your Repositories update automatically, and the compiler will catch any breaking changes in your UI layer.

### 🧩 Declarative Persistence
With Stitch, your architecture becomes declarative. Instead of implementing how data moves, you declare the relationship between your Entities and Domain models using annotations like `@MapTo`.

### 💉 Unobtrusive Integration
We believe a library should fit into your existing workflow, not dictate it. That's why Stitch generates standard Kotlin interfaces and classes that work with any DI framework—or no framework at all.

### 🌍 Multi-Platform by Default
The future of Kotlin is Multiplatform. Stitch is built from the ground up to support Room3 KMP, allowing you to share your persistence logic across Android, iOS, and beyond with zero overhead.

---

## 🎯 Our Goal

To empower developers to focus on **building features**, not **writing plumbing**. We want to make high-quality, clean architecture the path of least resistance for every Android and KMP project.
