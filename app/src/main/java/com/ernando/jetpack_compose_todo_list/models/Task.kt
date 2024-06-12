package com.ernando.jetpack_compose_todo_list.models

import java.util.UUID

@Suppress("PLUGIN_IS_NOT_ENABLED")
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    var isDone: Boolean = false
)
