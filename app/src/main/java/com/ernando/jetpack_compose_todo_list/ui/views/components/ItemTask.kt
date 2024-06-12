package com.ernando.jetpack_compose_todo_list.ui.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ernando.jetpack_compose_todo_list.models.Task

@Composable
fun ItemTask(
    task: Task, onToggleIsDone: (task: Task) -> Unit
) {
    
    var isDoneState by remember { mutableStateOf(task.isDone) }
    var rememberTask by remember { mutableStateOf(task) }
    
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
        Checkbox(checked = rememberTask.isDone, onCheckedChange = { isDone ->
            rememberTask = task.copy(isDone = isDone)
            onToggleIsDone(rememberTask)
            isDoneState = isDone
        })
        Column {
            Text(
                text = task.title,
                modifier = Modifier.padding(top = 10.dp, bottom = 3.dp, start = 5.dp, end = 15.dp),
                fontWeight = FontWeight.Medium,
                //textDecoration = if (isDoneState) TextDecoration.LineThrough else TextDecoration.None
                textDecoration = if (rememberTask.isDone) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                text = task.description,
                modifier = Modifier.padding(bottom = 3.dp, start = 5.dp, end = 15.dp),
                fontSize = 12.sp,
                color = Color.LightGray,
//                textDecoration = if (isDoneState) TextDecoration.LineThrough else TextDecoration.None
                textDecoration = if (rememberTask.isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        
        
    }
    
}