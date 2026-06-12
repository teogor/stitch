# Modular Queries with @RawOperation

While generated repositories handle standard CRUD operations perfectly, complex applications often require specific, reusable business operations. Stitch allows you to extract these into dedicated classes using the `@RawOperation` annotation.

---

## 🛠️ The Problem

In a large DAO, your list of functions can become overwhelming:

```kotlin
@Dao
interface InventoryDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :query")
    fun searchProducts(query: String): Flow<List<Product>>

    // ... 50 more queries
}
```

Injecting the entire `InventoryRepository` just to use one specific search function can make your components harder to test and reason about.

---

## ✨ The Stitch Solution

By annotating a DAO function with `@RawOperation`, Stitch generates a standalone class for that specific query.

### 1. Annotate the DAO function

```kotlin
@Dao
interface InventoryDao {
    @RawOperation
    @Query("SELECT * FROM products WHERE name LIKE :query")
    fun searchProducts(query: String): Flow<List<Product>>
}
```

### 2. Use the Generated Operation

Stitch generates a class named `SearchProductsOperation` (based on your function name). This class implements the `invoke` operator, allowing it to be used like a function.

```kotlin
class ProductViewModel(
    private val searchProducts: SearchProductsOperation
) : ViewModel() {

    val results = searchProducts("my query") // Clean and modular!
}
```

---

## 🧐 Why use Raw Operations?

1.  **Modularity**: Components only depend on the specific actions they perform, following the Interface Segregation Principle.
2.  **Testability**: It's easier to mock a single `SearchProductsOperation` than a massive `InventoryRepository`.
3.  **Readability**: Your code clearly expresses intent by injecting named operations.

---

## ⚙️ Configuration

You can control how operations are generated globally in your `build.gradle.kts`:

```kotlin
stitch {
    // Options: ALL, EXPLICIT, AUTOMATIC, DISABLED
    operationGenerationLevel = OperationGenerationLevel.EXPLICIT
}
```

- **ALL**: Generates a class for every function in every DAO.
- **EXPLICIT** (Default): Only generates classes for functions annotated with `@RawOperation`.
- **AUTOMATIC**: Automatically generates classes for non-QUERY operations (Insert, Update, Delete) and explicit `@RawOperation` functions.
- **DISABLED**: Turns off operation generation entirely.

---

## 🎯 Use Case: Clean Architecture

In Clean Architecture, these generated operations can serve as **Use Cases** or **Interactors**, providing a direct bridge between your UI and the data layer without manual implementation.
