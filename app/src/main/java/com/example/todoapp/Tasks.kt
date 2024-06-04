package com.example.todoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


data class Task(var id: Int, val name: String, var done: Boolean = false)

@Composable
fun NewTaskDialog(goal: Goal, tasks: MutableList<Task>, dialogOpen: MutableState<Boolean>) {
    var newTaskName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { dialogOpen.value = false },
        content = {
            Card (modifier = Modifier
                .fillMaxWidth()) {
                Column (
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Create new task")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = newTaskName,
                        label = { Text("Name") },
                        onValueChange = {text -> newTaskName = text}
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row (modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = { dialogOpen.value = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        TextButton(onClick = {
                            goal.lastTaskId++
                            if (newTaskName.isNotEmpty()) {
                                tasks.add(Task(id = goal.lastTaskId, name = newTaskName))
                            }
                            newTaskName = ""
                            dialogOpen.value = false
                        }) {
                            Text("Create")
                        }
                    }
                }
            }
        }
    )
}