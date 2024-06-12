package com.ernando.jetpack_compose_todo_list.ui.views.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ernando.jetpack_compose_todo_list.ui.theme.JetpackComposeTodoListTheme
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TaskViewModel
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TasksViewModel
import com.ernando.jetpack_compose_todo_list.ui.views.components.AppBarTask
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
//    task: Task?,
    taskViewModel: TaskViewModel,
    tasksViewModel: TasksViewModel,
    onBackTasks: () -> Unit
) {
    
    
    val taskUiState by taskViewModel.taskUiState.collectAsState()
    val tasksUiState by tasksViewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            AppBarTask(title = tasksUiState.task?.title ?: "Nova tarefa", navigationIcon = {
                IconButton(onClick = { onBackTasks() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volta para lista de tarefas"
                    )
                }
            }, actions = {
                if (taskUiState.title.isNotEmpty() && taskUiState.description.isNotEmpty()) {
                    IconButton(onClick = {
                        MainScope().launch {
                            tasksUiState.onCreateTask(taskUiState.title, taskUiState.description)
                        }
                        onBackTasks()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Salvar a edição"
                        )
                    }
                }
                
            })
        },
    ) { innerPadding ->
        EditTask(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            titleValue = tasksUiState.task?.title ?: taskUiState.title,
            descriptionValue = tasksUiState.task?.description ?: taskUiState.description,
            onEditTitleTask = {
                taskUiState.onChangedTitle(it)
            },
            onEditDescriptionTask = {
                taskUiState.onChangedDescription(it)
            })
        
    }
}

@Composable
fun EditTask(
    titleValue: String = "",
    descriptionValue: String = "",
    onEditTitleTask: (title: String) -> Unit = {},
    onEditDescriptionTask: (description: String) -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    
    Column(modifier.padding(horizontal = 15.dp)) {
        
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Titulo") },
            value = titleValue,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent
            ),
            onValueChange = { onEditTitleTask(it) }
        )
        
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent
            ),
            placeholder = { Text(text = "Descrição") },
            value = descriptionValue,
            onValueChange = {
                onEditDescriptionTask(it)
            }
        )
        
    }
}

@Preview
@Composable
private fun TaskScreenPreview() {
    val taskViewModel = koinViewModel<TaskViewModel>()
    val tasksViewModel = koinViewModel<TasksViewModel>()
    JetpackComposeTodoListTheme {
        TaskScreen(
//            task = Task(id = "wdwd", title = "dwdasa", description = "wdwdwdw"),
            taskViewModel,
            tasksViewModel,
            onBackTasks = {})
    }
    
}

@Suppress("VisualLintAtfColorblindCheck", "VisualLintAccessibilityTestFramework")
@Preview
@Composable
private fun EditTaskPreview() {
    EditTask(
        titleValue = "Title",
        descriptionValue = "Description",
        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
    )
}