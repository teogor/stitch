# Decoupling with @MapTo

A common practice in clean architecture is to decouple your database entities from your domain models. However, this often leads to manual mapping logic in your repository implementation. Stitch solves this by automating the mapping process using the `@MapTo` annotation.

---

## 🛠️ The Problem

When your database entity looks like this:

```kotlin
@Entity
data class UserEntity(
    @PrimaryKey val id: Long,
    val firstName: String,
    val lastName: String
)
```

But your UI/Domain layer expects this:

```kotlin
data class User(
    val id: Long,
    val fullName: String
)
```

You normally have to manually map between them in your repository:

```kotlin
class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override fun getAll(): Flow<List<User>> = dao.getAll().map { list ->
        list.map { it.toDomain() }
    }
}
```

---

## ✨ The Stitch Solution

With Stitch, you simply annotate your `@Entity` with `@MapTo`.

### 1. Annotate the Entity

```kotlin
@Entity
@MapTo(target = User::class)
data class UserEntity( ... )
```

### 2. Define Mapping Functions

Stitch expects you to provide mapping functions. You can do this via extension functions (the default) or a custom mapper class.

#### Option A: Extension Functions (Default)

```kotlin
fun UserEntity.toDomain() = User(id = id, fullName = "$firstName $lastName")
fun User.toEntity() = UserEntity(id = id, firstName = fullName.split(" ")[0], lastName = fullName.split(" ")[1])
```

Stitch will automatically detect these functions and use them in the generated `UserRepositoryImpl`.

#### Option B: Custom Mapper Class

If you prefer a class-based approach (e.g., for dependency injection):

```kotlin
class UserMapper {
    fun toDomain(entity: UserEntity): User = ...
    fun toEntity(domain: User): UserEntity = ...
}

@Entity
@MapTo(target = User::class, mapper = UserMapper::class)
data class UserEntity( ... )
```

---

## 📦 Automatic Collection Mapping

Stitch isn't limited to single objects. It automatically handles:
- **Lists**: `List<UserEntity>` ↔ `List<User>`
- **Flows**: `Flow<UserEntity>` ↔ `Flow<User>`
- **Nested Collections**: `Flow<List<UserEntity>>` ↔ `Flow<List<User>>`

All mapping is performed asynchronously where appropriate, ensuring your database layer remains efficient and type-safe without the boilerplate.

---

## ⚙️ Advanced Customization

You can override the default mapping function names if you prefer different terminology:

```kotlin
@MapTo(
    target = User::class,
    toDomain = "asDomainModel",
    toEntity = "asDatabaseEntity"
)
```
