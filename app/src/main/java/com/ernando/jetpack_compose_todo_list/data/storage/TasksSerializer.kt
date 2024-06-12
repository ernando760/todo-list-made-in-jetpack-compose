package com.ernando.jetpack_compose_todo_list.data.storage

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.ernando.jetpack_compose_todo_list.models.Task
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
object TasksSerializer : Serializer<List<Task>> {
    override val defaultValue: List<Task> = mutableListOf<Task>()
    
    
    override suspend fun readFrom(input: InputStream): List<Task> {
        try {
            val tasks = Json.decodeFromStream<List<Task>>(input)
            return tasks
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }
    
    override suspend fun writeTo(t: List<Task>, output: OutputStream) {
//        t.plus()
    }
    
}