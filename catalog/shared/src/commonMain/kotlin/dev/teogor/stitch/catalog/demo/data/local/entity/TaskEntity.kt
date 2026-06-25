package dev.teogor.stitch.catalog.demo.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.teogor.stitch.MapTo
import dev.teogor.stitch.catalog.demo.domain.model.TaskModel

@Entity(tableName = "tasks_table")
@MapTo(target = TaskModel::class) // 👋 Instructs Stitch to map to your clean domain structure
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val isCompleted: Boolean,
)

// Extension functions that Stitch will automatically detect and bind into RepositoryImpl
fun TaskEntity.toDomain(): TaskModel = TaskModel(
    id = id,
    title = title,
    isCompleted = isCompleted,
)

fun TaskModel.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    isCompleted = isCompleted,
)
