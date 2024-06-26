package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                val dialogOpened = remember { mutableStateOf(false) }
                val goals = remember { mutableStateListOf<Goal>() }
                val currentGoal = remember { mutableStateOf<Goal?>(null) }



                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton =
                        if (currentGoal.value == null) {
                            { FloatingActionButton(onClick = {
                                dialogOpened.value = true
                            }) {
                                Icon(
                                    Icons.Rounded.Add,
                                    contentDescription = stringResource(id = R.string.add_content_desc)
                                )
                            } }
                        } else {
                            {}
                        }
                    ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        if (currentGoal.value == null) {
                            MainScreen(currentGoal = currentGoal, dialogOpened, goals = goals)
                        } else {
                            GoalScreen(goal = currentGoal.value!!, getBack = {
                                currentGoal.value = null
                            })
                        }
                    }
//                    MainScreen(currentGoal = currentGoal, dialogOpened, goals = goals)
                }
            }
        }
    }
}

@Composable
fun MainScreen (
    currentGoal: MutableState<Goal?>,
    dialogOpened: MutableState<Boolean>,
    goals: MutableList<Goal>
) {
    Column(
        content = {

            var newGoalName by remember { mutableStateOf("") }

            Text(
                text = "Your goals",
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            LazyColumn {
                itemsIndexed(goals) {index, item -> GoalCard(
                    goal = item,
                    select = { currentGoal.value = item },
                    delete = { goals.removeAt(index) }
                )}
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
                                        goals.add(
                                            Goal(
                                                index = goals.count(),
                                                name = newGoalName,
                                                tasks = mutableListOf()
                                            )
                                        )
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