package com.ernando.jetpack_compose_todo_list

import android.app.Application
import com.ernando.jetpack_compose_todo_list.di.appModule
import com.ernando.jetpack_compose_todo_list.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodoListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TodoListApplication)
            
            modules(appModule, storageModule)
        }
        
    }
}