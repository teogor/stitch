# Repositories & Transactions

Stitch automates the creation of the Repository pattern for your Room databases. Instead of manually implementing repository classes to bridge your DAOs and the rest of your app, Stitch generates them for you.

---

## 🛠️ How it Works

When you annotate a Room `@Dao`, Stitch analyzes its functions and generates:
1.  A **Repository Interface** with matching function signatures.
2.  A **Repository Implementation** that delegates calls to the DAO.

### Before Stitch (Manual Setup)

You would typically have to write this boilerplate yourself:

```kotlin
// DAO
@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>
}

// Manual Repository Interface
interface UserRepository {
    fun getAll(): Flow<List<User>>
}

// Manual Repository Implementation
class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override fun getAll() = dao.getAll()
}
```

### With Stitch (Automated)

You only need the DAO. Stitch handles the rest.

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>
}
```

Stitch generates `UserRepository` and `UserRepositoryImpl` automatically during compilation.

---

## 🔄 Transactions

Stitch provides built-in support for database transactions. Every generated repository includes a `transaction` block that allows you to group multiple database operations.

### Usage

```kotlin
userRepository.transaction {
    // These operations run within a single transaction
    insert(User(name = "John"))
    insert(User(name = "Jane"))
}
```

This is powered by Room's underlying transaction mechanism, ensuring atomicity across your repository calls.

---

## 📡 Reactive Data

Stitch seamlessly handles reactive types like `Flow`. If your DAO returns a `Flow<T>`, the generated repository will also return a `Flow<T>`, maintaining the reactive stream from the database to your UI.

```kotlin
// In your ViewModel
val users: StateFlow<List<User>> = userRepository.getAll()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
```

---

## ⚙️ Customization

You can customize the naming of your repositories using the `@StitchName` annotation on your DAO:

```kotlin
@Dao
@StitchName(repository = "AccountManager", implementation = "AccountManagerImpl")
interface UserDao { ... }
```

You can also configure global suffixes (like changing `Repository` to `Store`) in your `stitch` Gradle configuration.
