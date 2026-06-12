# Stitch vs. Manual Implementation

Choosing between manual implementation and a code-generation library like Stitch is a matter of balancing control vs. productivity. This guide compares the two approaches to help you decide.

---

## ⚖️ At a Glance

| Feature | Manual Implementation | Stitch |
| :--- | :--- | :--- |
| **Development Speed** | Slow (High Boilerplate) | Fast (Zero Boilerplate) |
| **Consistency** | Varies by developer | 100% Consistent |
| **Type Safety** | High (manual) | High (automated) |
| **Maintenance** | High (multiple files to update) | Low (update only DAO/Entity) |
| **Learning Curve** | None | Low (learn annotations) |
| **KMP Support** | Manual setup required | Automated |

---

## 🛠️ The Manual Approach

In a manual setup, adding a new database table requires creating and maintaining at least 3-4 different files:
1.  The DAO.
2.  The Repository interface.
3.  The Repository implementation.
4.  The DI binding.

**Pros:**
- Complete control over every line of code.
- No dependency on a compiler plugin.

**Cons:**
- High chance of "copy-paste" errors.
- Refactoring (e.g., renaming a field) requires updating code in multiple layers.
- Inconsistent implementation styles across a large team.

---

## ✨ The Stitch Approach

With Stitch, you define your schema and data access logic once in your Room components. The rest is generated.

**Pros:**
- **Productivity**: Save hours of development time on every project.
- **Single Source of Truth**: Your Room components are the source of truth for your entire data layer.
- **Team Alignment**: Every repository in your project follows the same battle-tested implementation pattern.
- **KMP Ready**: Complex multi-platform setup for Room3 is handled automatically.

**Cons:**
- Dependency on KSP (adds a small amount to build time).
- Code is generated (some developers prefer to see every file in their `src` folder).

---

## 🎯 When to choose Stitch?

**Stitch is a perfect fit if:**
- You value **Clean Architecture** but hate the boilerplate.
- You are building a **Kotlin Multiplatform** project.
- You want to ensure **consistency** across a large codebase.
- You want your team to focus on **business logic** rather than infrastructure.

**Manual implementation might be better if:**
- Your project has extremely unique data access requirements that don't fit the Repository pattern.
- You have a strict policy against using code-generation libraries.
