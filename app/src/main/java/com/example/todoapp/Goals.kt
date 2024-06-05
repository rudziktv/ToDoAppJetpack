package com.example.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Goal(var index: Int, var tasks: MutableList<Task>, val name: String, var lastTaskId: Int = 0)

@Composable
fun GoalScreen(goal: Goal, getBack: ()->Unit) {

    val dialogOpen = remember { mutableStateOf(false) }
//    val tasks = remember { mutableStateListOf<Task>() }
//    tasks.addAll(goal.tasks)

//    LaunchedEffect(tasks) {
//        goal.tasks = tasks
//    }

    Column {
        
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth()) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    getBack()
                }) {
                    Icon(Icons.Rounded.KeyboardArrowLeft, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = goal.name, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "21/37", fontSize = 12.sp)
            }
            IconButton(onClick = { dialogOpen.value = true }) {
                Icon(Icons.Rounded.Add, contentDescription = null)
            }
        }
        LinearProgressIndicator(progress = 1f, modifier = Modifier.fillMaxWidth())

        LazyColumn () {
            itemsIndexed(goal.tasks) {index, task ->
                TaskItem(task)
                if (goal.tasks.count() - 1 != index) {
                    Divider()
                }
            }
        }

        if (dialogOpen.value) {
            NewTaskDialog(goal, goal.tasks, dialogOpen)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalCard(goal: Goal, select: () -> Unit) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 6.dp),
        onClick = {
            // open goal
            select()
        }) {

        Column (modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            val count = if (goal.tasks.isEmpty()) 1f else goal.tasks.count()
            var expanded by remember { mutableStateOf(false) }

            Row (modifier = Modifier
                .align(Alignment.End)
                .offset(x = 6.dp)) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        leadingIcon = { Icon(Icons.Rounded.Delete, contentDescription = "") },
                        onClick = { /*TODO*/ })
                }
            }

            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = goal.name,
                    fontSize = 20.sp
                )
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.offset(x = 12.dp, y = (-6).dp)
                ) {
                    Icon(Icons.Rounded.MoreVert, contentDescription = null)
                }

            }



            Spacer(modifier = Modifier.height(12.dp))
            Text(modifier = Modifier.align(Alignment.End),
                fontSize = 12.sp,
                text = "${goal.tasks.count { task -> task.done }}/${goal.tasks.count()}")
            LinearProgressIndicator(
                progress = goal.tasks.count { task -> task.done }/ count.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}