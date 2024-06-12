package com.ernando.jetpack_compose_todo_list.ui.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ernando.jetpack_compose_todo_list.models.Task
import com.ernando.jetpack_compose_todo_list.navigation.NavBarItems
import com.ernando.jetpack_compose_todo_list.ui.theme.JetpackComposeTodoListTheme
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TasksViewModel
import com.ernando.jetpack_compose_todo_list.ui.views.components.AppBarTask
import com.ernando.jetpack_compose_todo_list.ui.views.components.ItemListTask
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel,
    onNavigateToTask: (task: Task?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navItems = tasksViewModel.navItems
    val tasksUiState by tasksViewModel.uiState.collectAsState()
    
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        AppBarTask(title = if (tasksUiState.navItem == navItems[0]) "Tarefas" else "Tarefas Concluídas",
            actions = {
                IconButton(onClick = { onNavigateToTask(null) }) {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = "Adicionar tarefa"
                    )
                }
            })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { onNavigateToTask(null) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar tarefa")
        }
        
    }, bottomBar = {
        BottomAppBar(actions = {
            NavBarItems(navItems = navItems,
                selectedNavItem = tasksUiState.navItem,
                onSelectedNavItem = { navItem ->
                    tasksUiState.onChangeNavItem(navItem)
                    tasksUiState.onGetAllTasks()
                })
        })
        
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ItemListTask(
                tasks = if (tasksUiState.navItem == navItems[0]) tasksUiState.tasks else tasksUiState.tasksIsDone,
                onRemoveTask = { task ->
                    tasksUiState.onRemoveTask(task)
                },
                messageEmpty = if (tasksUiState.navItem == navItems[0]) "Nenhuma tarefa foi adicionada" else "Nenhuma tarefa foi concluída",
                onNavigateToTask = onNavigateToTask,
                onToggleIsDone = tasksUiState.onToggleIsDone,
            )
        }
        
    }
}


@Preview
@Composable
private fun TasksScreenPreview() {
    val viewModel = koinViewModel<TasksViewModel>()
    JetpackComposeTodoListTheme {
        TasksScreen(tasksViewModel = viewModel, onNavigateToTask = {})
    }
}


