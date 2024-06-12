package com.ernando.jetpack_compose_todo_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ernando.jetpack_compose_todo_list.ui.views.MyApp

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        
        enableEdgeToEdge()
        
        setContent {
            MyApp()
        }
    }
    
}







