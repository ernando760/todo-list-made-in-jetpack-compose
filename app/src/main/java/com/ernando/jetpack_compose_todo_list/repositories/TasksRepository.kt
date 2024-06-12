package com.ernando.jetpack_compose_todo_list.repositories

import com.ernando.jetpack_compose_todo_list.data.database.dao.TaskDao
import com.ernando.jetpack_compose_todo_list.data.database.entities.TaskEntity
import com.ernando.jetpack_compose_todo_list.models.Task
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TasksRepository(private val dao: TaskDao) {
    val tasks get() = dao.getAll()
    
    
    suspend fun save(task: Task) = withContext(IO) {
        dao.save(task.toTaskEntity())
    }
    
    suspend fun toggleIsDone(task: Task) = withContext(IO) {
        val entity = task.copy(isDone = !task.isDone).toTaskEntity()
        dao.save(entity)
    }
    
    fun deleteTask(task: Task) = dao.deleteTask(task.toTaskEntity())
    
}

fun Task.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    isDone = this.isDone
)

fun TaskEntity.toTask() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    isDone = this.isDone
)