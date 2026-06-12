# Reference Guide

This guide provides a detailed reference for the annotations and configuration options available in Stitch.

---

## 🏷️ Annotations

### `@MapTo`
Marks a Room Entity to be mapped to a domain model in the generated repository.

- **`target`**: The domain model class to map to.
- **`toDomain`**: Name of the function to convert entity to domain model (default: `"toDomain"`).
- **`toEntity`**: Name of the function to convert domain model to entity (default: `"toEntity"`).
- **`mapper`**: (Optional) A mapper class to use for conversion instead of extension functions.

### `@RawOperation`
Instructs Stitch to generate a separate operation class for a DAO method.

- **`generate`**: Whether to generate the operation class (default: `true`).

### `@StitchName`
Provides custom names for generated repositories and implementations.

- **`repository`**: Custom name for the generated repository interface.
- **`implementation`**: Custom name for the generated repository implementation class.

### `@StitchIgnore`
Marks a DAO, Entity, or function to be ignored by Stitch code generation.

### `@StitchDatabase`
Configures a Room database for automatic builder generation.

- **`fileName`**: The name of the database file (e.g., `"app.db"`).

### `@ExplicitEntities`
Explicitly defines the entities a DAO interacts with for improved type safety.

- **`entities`**: List of entity classes associated with the DAO.
- **`isExclusive`**: Whether only the listed entities are managed by this DAO (default: `false`).

### `@Operation`
Marks a class as an Operation (internally used).

### `@OperationSignature`
Marks a function as an Operation Signature (internally used).

---

## ⚙️ Gradle Configuration

Configure Stitch by adding a `stitch` block in your module's `build.gradle.kts`:

```kotlin
stitch {
    generatedPackageName = "dev.teogor.stitch.sample.generated"
    repositorySuffix = "Repository"
    operationSuffix = "Operation"
}
```

### Properties

| Property | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `generatedPackageName` | `String` | Required | The base package for all generated code. |
| `addDocumentation` | `Boolean` | `true` | Whether to generate KDoc for generated classes. |
| `enableOperationGeneration` | `Boolean` | `true` | Global toggle for operation generation. |
| `operationGenerationLevel` | `Enum` | `EXPLICIT` | `ALL`, `EXPLICIT`, `AUTOMATIC`, or `DISABLED`. |
| `repositorySuffix` | `String` | `"Repository"` | Suffix for generated repository interfaces. |
| `operationSuffix` | `String` | `"Operation"` | Suffix for generated operation classes. |
| `diFramework` | `Enum` | `METRO` | DI framework to target (`METRO`, `HILT`, `CUSTOM`). |
| `repositoryBaseClass` | `String?` | `null` | Fully qualified name of a base class for repositories. |
| `visibility` | `Enum` | `PUBLIC` | Visibility of generated code (`PUBLIC`, `INTERNAL`). |
| `enableRepositoryImplGeneration` | `Boolean` | `true` | Whether to generate implementation classes. |
| `enableKmpSupport` | `Boolean` | `false` | Enables Kotlin Multiplatform (KMP) support. |
| `enableDatabaseBuilderGeneration` | `Boolean` | `false` | Toggles generation of `getDatabaseBuilder()`. |

### Package Overrides

| Property | Default Path |
| :--- | :--- |
| `repositoryPackage` | `${basePackage}.data.repository` |
| `repositoryImplPackage` | `${basePackage}.data.repository.impl` |
| `operationPackage` | `${basePackage}.database.operation` |
| `diPackage` | `${basePackage}.di` |

---

## 💉 Dependency Injection

Stitch generates DI modules automatically based on the `diFramework` setting.

### Metro (Default)
Generates `@DependencyContainer` and binding modules compatible with the [Metro](https://github.com/teogor/metro) library.

### Hilt
Generates standard Dagger/Hilt `@Module` and `@InstallIn` annotations.

### Custom
Allows you to specify a custom `injectAnnotation` if you are using a different DI framework.
