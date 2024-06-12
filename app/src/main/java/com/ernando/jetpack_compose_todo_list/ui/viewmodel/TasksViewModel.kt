package com.ernando.jetpack_compose_todo_list.ui.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernando.jetpack_compose_todo_list.models.Task
import com.ernando.jetpack_compose_todo_list.navigation.NavItem
import com.ernando.jetpack_compose_todo_list.repositories.TasksRepository
import com.ernando.jetpack_compose_todo_list.repositories.toTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val tasksIsDone: List<Task> = emptyList(),
    val task: Task? = null,
    val navItem: NavItem,
    val onChangeNavItem: (NavItem) -> Unit = {},
    val onGetTask: (String) -> Unit = {},
    val onGetAllTasks: () -> Unit = {},
    val onRemoveTask: (Task) -> Unit = {},
    val onToggleIsDone: (Task) -> Unit = {},
    val onCreateTask: (title: String, description: String) -> Unit = { _, _ -> }
)

class TasksViewModel(private val repository: TasksRepository) : ViewModel() {
    
    
    val navItems = listOf(
        NavItem("Tarefas", icon = Icons.Default.List),
        NavItem("Tarefas concluídas", icon = Icons.Default.Check)
    )
    
    private val _uiState = MutableStateFlow(TasksUiState(navItem = navItems[0]))
    val uiState = _uiState.asStateFlow()
    
    
    init {
//        getAllTask()
        onGetAllTasks()
        onGetTask()
        onChangeNavItem()
        onRemoveTask()
        onToggleIsDone()
        onCreateTask()
    }

//    private fun getAllTask() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.tasks.collect { tasks ->
//                val tasksFull = tasks.map { it.toTask() }
//                val tasksIsUnDone = tasksFull.filter { !it.isDone }
//                val tasksIsDone = tasksFull.filter { it.isDone }
//                _uiState.value =
//                    _uiState.value.copy(tasks = tasksIsUnDone, tasksIsDone = tasksIsDone)
//                Log.i("onGetAllTasks", "TasksIsUnDone:${tasksIsUnDone}")
//                Log.i("onGetAllTasksIsDone", "TasksIsDone:${tasksIsDone}")
//            }
//        }
//    }
    
    private fun onGetAllTasks() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onGetAllTasks = {
                    launch(Dispatchers.IO) {
                        var tasksFull = emptyList<Task>()
                        var tasksIsUnDone = tasksFull
                        var tasksIsDone = tasksFull
                        repository.tasks.collect { tasks ->
                            tasksFull = tasks.map { it.toTask() }
                            tasksIsUnDone = tasksFull.filter { !it.isDone }
                            tasksIsDone = tasksFull.filter { it.isDone }
                            _uiState.value = _uiState.value.copy(
                                tasks = tasksIsUnDone, tasksIsDone = tasksIsDone
                            )
                            Log.i("onGetAllTasks", "TasksIsUnDone:${tasksIsUnDone}")
                            Log.i("onGetAllTasksIsDone", "TasksIsDone:${tasksIsDone}")
                        }
                        
                        
                    }
                })
            }
        }
    }
    
    private fun onGetTask() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onGetTask = { id ->
                    val taskUnDone = _uiState.value.tasks.find { it.id == id }
                    val taskIsDone = _uiState.value.tasksIsDone.find { it.id == id }
                    _uiState.value = _uiState.value.copy(task = taskUnDone ?: taskIsDone)
                    Log.i("onGetTask", "Task:${_uiState.value.task}")
                })
                
            }
            
        }
    }
    
    private fun onCreateTask() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onCreateTask = { title, description ->
                    launch(Dispatchers.IO) {
                        repository.save(
                            Task(
                                title = title, description = description
                            )
                        )
                    }
                    
                })
            }
            repository.tasks.collect { tasks ->
                val tasksFull = tasks.map { it.toTask() }
                val tasksIsUnDone = tasksFull.filter { !it.isDone }
                val tasksIsDone = tasksFull.filter { it.isDone }
                
                _uiState.value =
                    _uiState.value.copy(tasks = tasksIsUnDone, tasksIsDone = tasksIsDone)
            }
            
        }
        
    }
    
    
    private fun onToggleIsDone() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onToggleIsDone = { task ->
                    launch(Dispatchers.IO) {
                        repository.toggleIsDone(task)
                        
                        repository.tasks.collect { tasks ->
                            val tasksFull = tasks.map { it.toTask() }
                            val tasksIsUnDone = tasksFull.filter { !it.isDone }
                            val tasksIsDone = tasksFull.filter { it.isDone }
                            
                            _uiState.value =
                                _uiState.value.copy(
                                    tasks = tasksIsUnDone,
                                    tasksIsDone = tasksIsDone
                                )
                            Log.i("onIsToggleIsDone", "TasksIsUnDone:${tasksIsUnDone}")
                            Log.i("onIsToggleIsDone", "TasksIsDone:${tasksIsDone}")
                        }
                    }
                    
                    
                })
            }
        }
        
        
    }
    
    private fun onChangeNavItem() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onChangeNavItem = { navItem ->
                    _uiState.value = _uiState.value.copy(navItem = navItem)
                    Log.i("onChangeNavItem", "NavItem:${navItem}")
                })
            }
            
        }
        
    }
    
    
    private fun onRemoveTask() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onRemoveTask = { task ->
                    launch {
                        repository.tasks.collect { tasks ->
                            val tasksFull = tasks.map { it.toTask() }
                            val tasksIsUnDone = tasksFull.filter { !it.isDone }
                            val tasksIsDone = tasksFull.filter { it.isDone }
                            
                            if (!task.isDone) {
                                _uiState.value = _uiState.value.copy(
                                    tasks = tasksIsUnDone.dropWhile { it.id == task.id },
                                    tasksIsDone = tasksIsDone
                                )
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    tasks = tasksIsUnDone,
                                    tasksIsDone = tasksIsDone.dropWhile { it.id == task.id })
                            }
                        }
                    }
                    
                    
                    
                    Log.i("onRemoveTask", "Tasks:${_uiState.value.task}")
                    Log.i("onRemoveTaskIsDone", "TasksIsDone:${_uiState.value.tasksIsDone}")
                    
                    repository.deleteTask(task)
                })
            }
        }
        
        
    }
    
    
}


//class TasksViewModel(private val repository: TasksRepository) : ViewModel() {
//
//
//    val navItems = listOf(
//        NavItem("Tarefas", icon = Icons.Default.List),
//        NavItem("Tarefas concluídas", icon = Icons.Default.Check)
//    )
//
//
//    private val _tasks = MutableStateFlow<MutableList<Task>>(
//        value = mutableListOf(
//            Task(id = "1", title = "title 1", description = "description 1"),
//            Task(id = "2", title = "title 2", description = "description 2")
//        )
//    )
//
//
//    private val _tasksCompleted = MutableStateFlow<MutableList<Task>>(
//        value = mutableListOf()
//    )
//    private val _selectedNavItem = MutableStateFlow(navItems[0])
//
//    //private var tasksFull = _tasks.value.union(_tasksCompleted.value).toMutableList()
//    private var tasksFull = _tasks.value
//
//    val tasks = _tasks.asStateFlow()
//    val tasksCompleted = _tasksCompleted.asStateFlow()
//    val selectedNavItem = _selectedNavItem.asStateFlow()
//
//
//    fun createTask(title: String, description: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            storage.write("tasks", _tasks.updateAndGet { tasks ->
//                tasks.plus(
//                    Task(
//                        title = title,
//                        description = description,
//                    )
//                ).toMutableList()
//            })
//            _tasks.value = storage.read("tasks").toMutableList()
//        }
//    }
//
//
//    fun getTask(id: String): Task? {
//        val task = tasks.value.find { task -> task.id == id }
//        return task;
//    }
//
//    fun getAllTasks() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _tasks.value = storage.read("tasks").toMutableList()
//        }
//    }
//
//    fun updateTask(newTask: Task) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (_tasks.value.contains(newTask)) {
//                _tasks.value = storage.write("tasks", _updateTaskUnCompleted(newTask))
//            } else {
//                _tasks.value = storage.write("tasks", _tasks.value.plus(newTask).toMutableList())
//            }
//        }
//    }
//
//    private fun _updateTaskUnCompleted(newTask: Task): MutableList<Task> {
//        return _tasks.updateAndGet { tasks ->
//            tasks.map {
//                if (it.id == newTask.id) {
//                    it.copy(
//                        id = it.id,
//                        title = newTask.title,
//                        description = newTask.description,
//                        isDone = newTask.isDone
//                    )
//                } else {
//                    it
//                }
//            }.toMutableList()
//        }
//
//    }
//
//    private fun _updateTaskCompleted(newTask: Task): MutableList<Task> {
//        return _tasksCompleted.updateAndGet { tasks ->
//            tasks.map { task ->
//                if (task.id == newTask.id) {
//                    Task(
//                        id = task.id,
//                        title = newTask.title,
//                        description = newTask.description,
//                        isDone = newTask.isDone
//                    )
//                } else {
//                    task
//                }
//            }.toMutableList()
//        }
//    }
//
//    fun changeTasks(navItem: NavItem) {
//        tasksFull = _tasks.value
//        viewModelScope.launch {
//            if (navItem == navItems[0]) {
//                _tasks.value = tasksFull.filter { !it.isDone }.toMutableList()
//            }
//            if (navItem == navItems[1]) {
//                _tasks.value = tasksFull.filter { it.isDone }.toMutableList()
//            }
//
//        }
//    }
//
//
//    fun removeTask(task: Task) {
//        tasksFull =
//            tasksFull.union(_tasksCompleted.value).dropWhile { it.id == task.id }.toMutableList()
//        viewModelScope.launch(Dispatchers.IO) {
//            _tasks.value = storage.write(
//                "tasks",
//                tasksFull.filter { task -> !task.isDone }.toMutableList()
//            )
//
////            _tasksCompleted.value = tasksFull.filter { task -> task.isCompleted }.toMutableList()
//        }
//    }
//
//
//    fun changeSelectedNavItem(navItem: NavItem) {
//        viewModelScope.launch {
//            _selectedNavItem.value = navItem
//        }
//    }
//}

