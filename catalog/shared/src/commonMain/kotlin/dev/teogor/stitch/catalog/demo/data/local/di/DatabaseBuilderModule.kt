package dev.teogor.stitch.catalog.demo.data.local.di

import androidx.room3.RoomDatabase
import dev.teogor.stitch.catalog.demo.data.local.AppDatabase
import dev.teogor.stitch.catalog.demo.data.local.AppDatabaseConstructor
import dev.teogor.stitch.runtime.DatabasePath
import dev.teogor.stitch.runtime.StitchRoom

public object DatabaseBuilderModule {

    public fun provideDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        return StitchRoom.databaseBuilder(
            name = "demo_database.db",
            factory = AppDatabaseConstructor::initialize
        ) {
            pathStrategy = DatabasePath.Internal
            loggingEnabled = true
        }
    }
}
