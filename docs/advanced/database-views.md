# Working with Database Views

Stitch provides first-class support for Room's `@DatabaseView`. Similar to how it handles entities, Stitch can automatically generate read-only repositories and operations for your database views.

---

## 🧐 What is a Database View?

A Room `@DatabaseView` allows you to define a virtual table based on a SQL query. It's often used to simplify complex joins or aggregate data for presentation.

```kotlin
@DatabaseView("SELECT users.id, users.name, COUNT(orders.id) as orderCount FROM users JOIN orders ON users.id = orders.userId GROUP BY users.id")
data class UserOrderSummary(
    val id: Long,
    val name: String,
    val orderCount: Int
)
```

---

## 🛠️ Stitch Support

Stitch treats `@DatabaseView` classes as read-only components. If you have a DAO that returns your view, Stitch will generate the corresponding repository logic.

### 1. Define your View and DAO

```kotlin
@Dao
interface UserOrderSummaryDao {
    @Query("SELECT * FROM UserOrderSummary")
    fun getAllSummaries(): Flow<List<UserOrderSummary>>
}
```

### 2. Generated Components

Stitch will automatically generate `UserOrderSummaryRepository` and its implementation. Since views are read-only, you'll typically only see `QUERY` operations generated.

---

## 💉 Dependency Injection

Just like with standard entities, Stitch includes your view-based DAOs and repositories in the generated `StitchModule`.

```kotlin
// Automatically provided in StitchModule
@Provides
fun provideUserOrderSummaryRepository(
    dao: UserOrderSummaryDao,
    db: AppDatabase
): UserOrderSummaryRepository {
    return UserOrderSummaryRepositoryImpl(dao, db)
}
```

---

## 🎯 Use Case: Dashboard Data

Database views are perfect for dashboards where you need to combine data from multiple tables. By using Stitch with `@DatabaseView`, you can keep your view-models clean and focused on UI logic, while Stitch handles the plumbing of fetching data from your virtual tables.
