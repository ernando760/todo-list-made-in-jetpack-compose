package com.ernando.jetpack_compose_todo_list.ui.views.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextFieldTask(label: String, value: String, onValueChange: (value: String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.clip(RoundedCornerShape(5.dp)),
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange
    )
}
