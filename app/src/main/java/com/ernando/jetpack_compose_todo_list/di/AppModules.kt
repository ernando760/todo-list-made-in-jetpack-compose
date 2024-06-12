package com.ernando.jetpack_compose_todo_list.di

import androidx.room.Room
import com.ernando.jetpack_compose_todo_list.data.database.TodoListDatabase
import com.ernando.jetpack_compose_todo_list.repositories.TasksRepository
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TaskViewModel
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::TasksViewModel)
    viewModelOf(::TaskViewModel)
}

val storageModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TodoListDatabase::class.java, "database-tasks.db"
        ).build()
    }
    
    single {
        get<TodoListDatabase>().taskDao()
    }
    
    singleOf(::TasksRepository)
}