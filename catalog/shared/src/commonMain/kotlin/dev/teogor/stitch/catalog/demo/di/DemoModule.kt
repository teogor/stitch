/*
 * Copyright 2026 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.stitch.catalog.demo.di

import dev.teogor.stitch.catalog.demo.data.local.AppDatabase
import dev.teogor.stitch.catalog.demo.data.local.AppDatabaseConstructor
import dev.teogor.stitch.catalog.demo.data.local.data.repository.TaskRepository
import dev.teogor.stitch.catalog.demo.data.local.data.repository.impl.TaskRepositoryImpl
import dev.teogor.stitch.catalog.demo.data.local.database.operation.TaskInsertTaskOperation
import dev.teogor.stitch.catalog.demo.data.local.database.operation.TaskUpdateTaskStatusOperation
import dev.teogor.stitch.runtime.StitchRoom

object DemoModule {
    private val database: AppDatabase by lazy {
        StitchRoom.databaseBuilder(
            name = "demo_database.db",
            factory = AppDatabaseConstructor::initialize,
        ) {
            loggingEnabled = true
            logger = { println("DemoLog: $it") }
        }.build()
    }

    val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(
            dao = database.taskDao(),
            db = database,
        )
    }

    val updateTaskStatus by lazy {
        TaskUpdateTaskStatusOperation(taskRepository)
    }

    val insertTask by lazy {
        TaskInsertTaskOperation(taskRepository)
    }
}
