package dev.teogor.stitch.catalog.demo.di

import dev.teogor.stitch.catalog.demo.data.local.data.repository.TaskRepository
import dev.teogor.stitch.catalog.demo.data.local.di.DatabaseBuilderModule
import dev.teogor.stitch.catalog.demo.data.local.di.StitchModule

/**
 * A lightweight, zero-dependency stub implementation that fulfills the
 * ApplicationComponent contract using manual module registration.
 */
internal object StubApplicationComponent : ApplicationComponent {

    override val taskRepository: TaskRepository by lazy {
        // 1. Get the raw multiplatform database builder
        val databaseBuilder = DatabaseBuilderModule.provideDatabaseBuilder()

        // 2. Feed it into StitchModule to build the AppDatabase instance securely
        val database = StitchModule.provideDataLocalAppDatabase(databaseBuilder)

        // 3. Extract the required DAO layer from the database instance
        val taskDao = StitchModule.provideTaskDao(database)

        // 4. Finally, build and return the Stitch Repository Implementation!
        StitchModule.provideTaskRepository(dao = taskDao, db = database)
    }
}
