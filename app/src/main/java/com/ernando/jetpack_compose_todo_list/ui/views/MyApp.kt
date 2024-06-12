package com.ernando.jetpack_compose_todo_list.ui.views


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ernando.jetpack_compose_todo_list.ui.theme.JetpackComposeTodoListTheme
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TaskViewModel
import com.ernando.jetpack_compose_todo_list.ui.viewmodel.TasksViewModel
import com.ernando.jetpack_compose_todo_list.ui.views.screens.TaskScreen
import com.ernando.jetpack_compose_todo_list.ui.views.screens.TasksScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyApp() {
    val tasksViewModel = koinViewModel<TasksViewModel>()
    val taskViewModel = koinViewModel<TaskViewModel>()
    val uiState by tasksViewModel.uiState.collectAsState()
    val navHost = rememberNavController()
    JetpackComposeTodoListTheme(darkTheme = true) {
        NavHost(navController = navHost, startDestination = "tasks") {
            composable("tasks") {
                TasksScreen(tasksViewModel = tasksViewModel, onNavigateToTask = { task ->
                    navHost.navigate("tasks?taskId=${task?.id ?: ""}")
                })
            }
            composable(
                "tasks?taskId={taskId}",
                arguments = listOf(navArgument("taskId") { defaultValue = "" })
            ) { navBackStackEntry ->
                val taskId = navBackStackEntry.arguments?.getString("taskId")
                if (taskId != null) {
                    uiState.onGetTask(taskId)
                }
                TaskScreen(
//                    task = uiState.task,
                    taskViewModel,
                    tasksViewModel,
                    onBackTasks = { navHost.popBackStack() })
            }
        }
        
        
    }
    
}

@Preview
@Composable
private fun AppPreview() {
    MyApp()
}