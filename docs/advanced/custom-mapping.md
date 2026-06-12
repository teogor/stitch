# Advanced Custom Mapping

The `@MapTo` annotation is a powerful tool for maintaining a clean separation between your database layer and your domain logic. This guide explores advanced mapping scenarios supported by Stitch.

---

## 🏗️ The Mapping Engine

Stitch's mapping engine is designed to be flexible. It doesn't just call a function; it understands types and can perform complex transformations automatically.

---

## ⚡ Suspend Mapping Functions

In modern Android development, mapping logic can sometimes be heavy or require asynchronous calls (e.g., fetching additional data from a cache). Stitch fully supports `suspend` mapping functions.

### How it Works

If your mapping function (extension or in a mapper class) is marked as `suspend`, Stitch will automatically generate the repository implementation as `suspend` as well.

```kotlin
// In your Mapper or Extension
suspend fun UserEntity.toDomain(): User {
    val extraData = fetchExtraData() // Suspending call
    return User(id, name, extraData)
}
```

Stitch detects the `suspend` modifier and ensures the `UserRepository.getAll()` implementation handles the coroutine context correctly.

---

## 📦 Mapping Collections

Stitch automatically handles the transformation of common collection types.

### List Mapping

When a DAO returns a `List<UserEntity>`, Stitch transforms it into a `List<User>` by applying your mapping logic to each item.

### Flow Mapping

Reactive streams are handled natively. `Flow<UserEntity>` becomes `Flow<User>` using the `Flow.map` operator.

### Nested Collections (e.g., `Flow<List<UserEntity>>`)

Stitch efficiently handles nested types. For `Flow<List<UserEntity>>`, it generates:

```kotlin
override fun getAll(): Flow<List<User>> = dao.getAll().map { list ->
    list.map { it.toDomain() }
}
```

---

## 🛠️ Class-Based Mappers

While extension functions are convenient, class-based mappers offer better testability and can be injected with their own dependencies.

```kotlin
class UserMapper @Inject constructor(
    private val preferences: AppPreferences
) {
    fun toDomain(entity: UserEntity): User { ... }
    fun toEntity(domain: User): UserEntity { ... }
}

@Entity
@MapTo(target = User::class, mapper = UserMapper::class)
data class UserEntity( ... )
```

When a `mapper` is specified, Stitch will:
1.  Request the `UserMapper` in the generated repository constructor.
2.  Use the mapper instance for all transformations.
3.  Include the mapper in the generated `StitchModule` if using a supported DI framework.

---

## 🎯 Best Practices

- **Keep it Simple**: Use extension functions for simple, pure mappings.
- **Use Mappers for Logic**: Use class-based mappers if you need to inject dependencies or perform complex logic.
- **Avoid Heavy Work**: Mapping happens on the database dispatcher (typically `Dispatchers.IO`). While `suspend` is supported, try to keep mapping logic as efficient as possible.
