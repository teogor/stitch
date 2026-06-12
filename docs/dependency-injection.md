# Dependency Injection Integration

Stitch takes the hassle out of manually wiring your database components by automatically generating Dependency Injection (DI) modules. Whether you're using Metro, Hilt, or a custom setup, Stitch ensures your repositories and DAOs are ready for injection.

---

## 🛠️ Automated Module Generation

Stitch generates a `StitchModule` class that provides:
1.  **Database Instances**: Automatically builds and provides your Room database.
2.  **DAO Providers**: Provides instances of your DAOs retrieved from the database.
3.  **Repository Providers**: Automatically wires DAOs into your generated repositories.

---

## 💉 Supported Frameworks

You can specify your preferred DI framework in your `build.gradle.kts`:

```kotlin
stitch {
    // Options: METRO, HILT, DAGGER, CUSTOM, NONE
    diFramework = DiFramework.METRO
}
```

### 1. Metro (Default)

Stitch is designed to work seamlessly with the [Metro](https://github.com/teogor/metro) library. It generates:
- `@BindingContainer` annotations.
- `@Provides` and `@SingleIn` annotations for repository and DAO instances.

### 2. Hilt / Dagger

If you're using Hilt or standard Dagger, Stitch generates:
- `@Module` and `@InstallIn(SingletonComponent::class)` (for Hilt).
- `@Provides` and `@Singleton` annotations.

### 3. Custom DI

If you use a different framework (like Koin or Kodein), you can set `diFramework = DiFramework.CUSTOM`. You can also provide a custom injection annotation:

```kotlin
stitch {
    diFramework = DiFramework.CUSTOM
    injectAnnotation = "com.myapp.Inject"
}
```

---

## 🏗️ How to use the generated Module

### Hilt Example

Once Stitch generates the module, you can immediately start injecting your repositories:

```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository // Injected automatically!
) : ViewModel() { ... }
```

### Metro Example

In a Metro-powered project, the generated `StitchModule` is automatically detected and integrated into your dependency graph.

---

## 📡 Dispatchers and Scoping

Stitch ensures that database operations are performed on the correct threads. By default, it uses `Dispatchers.IO` for all database interactions.

You can customize the dispatcher name in your configuration:

```kotlin
stitch {
    ioDispatcherName = "AppDispatchers.IO"
}
```

This allows you to maintain consistent threading policies across your entire application.
