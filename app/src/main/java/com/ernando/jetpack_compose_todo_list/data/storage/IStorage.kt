package com.ernando.jetpack_compose_todo_list.data.storage

interface IStorage<T> {
    suspend fun read(key: String): T
    suspend fun write(key: String, value: T): T
    suspend fun delete(key: String)
}