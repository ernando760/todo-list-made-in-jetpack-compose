package com.ernando.jetpack_compose_todo_list.ui.views.components


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ernando.jetpack_compose_todo_list.models.Task

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ItemListTask(
    tasks: List<Task>,
    onRemoveTask: (task: Task) -> Unit,
    messageEmpty: String = "Nenhuma tarefa foi adicionada",
    onToggleIsDone: (task: Task) -> Unit,
    onNavigateToTask: (task: Task) -> Unit
) {
    if (tasks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = messageEmpty, fontWeight = FontWeight.Bold)
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsTasks(
                tasks = tasks,
                onRemoveTask = onRemoveTask,
                onNavigateToTask = onNavigateToTask,
                onToggleIsDone = onToggleIsDone
            )
        }
    }
}

@JvmOverloads
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.itemsTasks(
    tasks: List<Task>,
    onRemoveTask: (task: Task) -> Unit,
    onNavigateToTask: (task: Task) -> Unit,
    onToggleIsDone: (task: Task) -> Unit,
    modifier: Modifier = Modifier
) {
    
    items(tasks.size) {
        val task = tasks[it]
        Box(
            modifier = modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .fillMaxWidth()
                .combinedClickable(onClick = { onNavigateToTask(task) },
                    onLongClick = { onRemoveTask(task) })
        ) {
            ItemTask(
                task = task, onToggleIsDone = onToggleIsDone
            )
        }
    }
    
    
}