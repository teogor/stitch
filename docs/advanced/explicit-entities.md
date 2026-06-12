# Explicit Entity Association

Stitch uses smart heuristics to associate your Room DAOs with their corresponding Entities. However, in complex projects with custom naming conventions or shared DAOs, you may need to explicitly define these relationships.

---

## 🧐 How Stitch Associates DAOs and Entities

By default, Stitch looks for a naming pattern:
- `UserEntity` + `UserDao` → Associated.
- `User` + `UserDao` → Associated.

If multiple entities are handled by a single DAO, or if names don't match, Stitch needs your help to know which entity to use as the "primary" for repository generation.

---

## 🛠️ The `@ExplicitEntities` Annotation

You can use the `@ExplicitEntities` annotation on your DAO to clearly define the entities it manages.

```kotlin
@Dao
@ExplicitEntities(
    entities = [Product::class],
    isExclusive = true
)
interface InventoryDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>
}
```

### Parameters

- **`entities`**: An array of entity classes associated with this DAO.
- **`isExclusive`**: (Optional) If set to `true`, Stitch will only consider the listed entities for mapping logic, ignoring naming-based heuristics.

---

## 🎯 When to use Explicit Association

### 1. Ambiguous Naming
If you have a DAO named `StorageDao` that handles `Product` entities, Stitch won't automatically associate them. `@ExplicitEntities` bridges this gap.

### 2. Multi-Entity DAOs
If a DAO handles multiple entities (e.g., a `CatalogDao` for both `Product` and `Category`), you can list both. Stitch will prioritize the first entity for repository naming (e.g., `ProductRepository` or `CatalogRepository` depending on other settings).

### 3. Improved Type Safety
By explicitly listing entities, you provide a clear contract for Stitch's code generator, reducing the risk of unintended mapping errors in large codebases.

---

## ⚙️ Configuration Priority

Stitch prioritizes association in this order:
1.  **Explicit**: Using `@ExplicitEntities` on the DAO.
2.  **Naming Heuristics**: Matching entity names with DAO names.
3.  **Fallback**: If no association is found, repository generation for that DAO might be skipped or incomplete.
