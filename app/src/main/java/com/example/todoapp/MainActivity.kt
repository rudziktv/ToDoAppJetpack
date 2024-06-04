package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.ui.theme.ToDoAppTheme
import java.io.Console

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                var name by remember {
                    mutableStateOf("")
                }

                var dialogOpened = remember { mutableStateOf(false) }
                var goals = remember { mutableStateListOf<Goal>() }
                var tasks = remember { mutableStateListOf<Task>() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = { FloatingActionButton(onClick = {
//                        goals.add(Goal(title = "New goal", tasks = mutableListOf()))
                        dialogOpened.value = true
                    }) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = stringResource(id = R.string.add_content_desc)
                        )
                    } }
                    ) { innerPadding ->
                    MainScreen(dialogOpened, goals = goals, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Task(val title: String, var done: Boolean)
data class Goal(var tasks: List<Task>, val title: String)

@Composable
fun MainScreen (dialogOpened: MutableState<Boolean>, goals: MutableList<Goal>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        content = {

            var newGoalName by remember { mutableStateOf("") }

            Text(
                text = "Your goals",
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            LazyColumn {
                items(goals) {item -> GoalCard(goal = item)}
            }
            if (dialogOpened.value)
                Dialog(
                    onDismissRequest = { dialogOpened.value = false },
                    content = {
                        Card (modifier = Modifier
                            .fillMaxWidth()) {
                            Column (
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = "Create new goal")
                                Spacer(modifier = Modifier.height(8.dp))
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = newGoalName,
                                    label = { Text("Name") },
                                    onValueChange = {text -> newGoalName = text}
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row (modifier = Modifier.align(Alignment.End)) {
                                    TextButton(onClick = { dialogOpened.value = false }) {
                                        Text("Cancel")
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    TextButton(onClick = {
                                        goals.add(Goal(title = newGoalName, tasks = mutableListOf()))
                                        newGoalName = ""
                                        dialogOpened.value = false
                                    }) {
                                        Text("Create")
                                    }
                                }
                            }
                        }
                    }
                )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalCard(goal: Goal) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 6.dp),
        onClick = {
            // open goal
        }) {
        Column (modifier = Modifier.padding(12.dp)) {
            var count = if (goal.tasks.isEmpty()) 1f else goal.tasks.count()
            Text(text = goal.title, fontSize = 20.sp)
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

@Composable
fun GoalScreen(goal: Goal) {}