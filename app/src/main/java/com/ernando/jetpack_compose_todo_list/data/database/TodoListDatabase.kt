package com.ernando.jetpack_compose_todo_list.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ernando.jetpack_compose_todo_list.data.database.dao.TaskDao
import com.ernando.jetpack_compose_todo_list.data.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}