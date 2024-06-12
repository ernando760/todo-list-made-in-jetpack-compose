package com.ernando.jetpack_compose_todo_list.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ernando.jetpack_compose_todo_list.models.Task
import com.ernando.jetpack_compose_todo_list.repositories.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TaskUiState(
    val task: Task? = null,
    val title: String = "",
    val description: String = "",
    val onChangedTitle: (String) -> Unit = {},
    val onChangedDescription: (String) -> Unit = {},
    val onGetTask: (Task) -> Unit = {},
)

class TaskViewModel(private val repository: TasksRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TaskUiState())
    
    val taskUiState = _uiState.asStateFlow()
    
    init {
        getTask()
        onChangedTitle()
        onChangedDescription()
    }
    
    
    private fun getTask() {
        _uiState.update { currentState ->
            currentState.copy(onGetTask = { task ->
                _uiState.value = _uiState.value.copy(task = task)
            })
        }
        Log.i("onGetTask", "Task:${_uiState.value.task}")
    }
    
    
    private fun onChangedTitle() {
        _uiState.update { currentState ->
            currentState.copy(onChangedTitle = { title ->
                _uiState.value = _uiState.value.copy(title = title)
            })
        }
    }
    
    private fun onChangedDescription() {
        _uiState.update { currentState ->
            currentState.copy(onChangedDescription = { description ->
                _uiState.value = _uiState.value.copy(description = description)
            })
        }
    }
    
    
}