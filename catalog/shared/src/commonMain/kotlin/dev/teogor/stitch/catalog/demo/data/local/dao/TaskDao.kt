package dev.teogor.stitch.catalog.demo.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import dev.teogor.stitch.ExplicitEntities
import dev.teogor.stitch.RawOperation
import dev.teogor.stitch.StitchName
import dev.teogor.stitch.catalog.demo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
@StitchName(
    repository = "TaskRepository",
    implementation = "TaskRepositoryImpl"
) // 🔐 Locks the explicit generated class naming convention
@ExplicitEntities(entities = [TaskEntity::class], isExclusive = true) // 🚀 Forces precise mapping resolution
interface TaskDao {

    @Query("SELECT * FROM tasks_table ORDER BY id DESC")
    fun observeAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(entity: TaskEntity)

    @Delete
    suspend fun deleteTask(entity: TaskEntity)

    @RawOperation // ✨ Instructs Stitch to extract this single action into a dedicated Use-Case Interactor
    @Query("UPDATE tasks_table SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTaskStatus(id: Long, isCompleted: Boolean)
}
