package com.ernando.jetpack_compose_todo_list.data.storage

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ernando.jetpack_compose_todo_list.models.Task
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


private val Context.dataStore by preferencesDataStore(name = "tasks")

class TasksStorageImpl(private val context: Context) : IStorage<List<Task>> {
    
    
    private val keysMap = mapOf(
        Pair(first = "tasks", second = stringPreferencesKey("tasks"))
    
    )
    
    
    override suspend fun read(key: String): List<Task> {
        try {
            val tasksData = context.dataStore.data.map {
                if (keysMap[key] != null) {
                    val tasksDecode =
                        Json.decodeFromString<List<Task>>(it[keysMap[key]!!] ?: "[]")
                    tasksDecode.toMutableList()
                }
                mutableListOf<Task>()
            }
            
            val tasks = tasksData.toList()
            return tasks.first()
        } catch (e: Exception) {
            Log.e("ERROR ${::TasksStorageImpl.name} -> read function", "${e.message}")
            return mutableListOf();
        }
    }
    
    override suspend fun write(key: String, value: List<Task>): List<Task> {
        try {
            context.dataStore.edit { preferences ->
                if (keysMap[key] != null) {
                    val valueString = Json.encodeToString(value)
                    preferences[keysMap[key]!!] = valueString
                }
                mutableListOf<Task>()
            }
            
            
            val tasks = read(key)
            return tasks
        } catch (e: Exception) {
            Log.e("ERROR ${::TasksStorageImpl.name} -> write function", "${e.message}")
            return mutableListOf();
        }
    }
    
    override suspend fun delete(key: String) {
        try {
            context.dataStore.data.map { preferences ->
                if (keysMap[key] != null) {
                    preferences[keysMap[key]!!]?.toMutableList()?.clear()
                }
                mutableListOf<Task>()
            }
            
        } catch (e: Exception) {
            throw Exception("ERROR ${::TasksStorageImpl.name} -> delete function ==> ${e.message}")
        }
    }
    
    
}