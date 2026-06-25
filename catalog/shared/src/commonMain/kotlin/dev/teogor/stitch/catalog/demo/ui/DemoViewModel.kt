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

package dev.teogor.stitch.catalog.demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.teogor.stitch.catalog.demo.data.local.data.repository.TaskRepository
import dev.teogor.stitch.catalog.demo.di.DemoModule
import dev.teogor.stitch.catalog.demo.domain.model.TaskModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DemoViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
): ViewModel() {
    private val insertTask = DemoModule.insertTask
    private val updateTaskStatus = DemoModule.updateTaskStatus

    val uiTasks: StateFlow<List<TaskModel>> = taskRepository.observeAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun createTask(title: String) {
        viewModelScope.launch {
            insertTask(
                TaskModel(title = title, isCompleted = false),
            )
        }
    }

    fun toggleTaskStatus(taskId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            updateTaskStatus(id = taskId, isCompleted = isCompleted)
        }
    }
}
