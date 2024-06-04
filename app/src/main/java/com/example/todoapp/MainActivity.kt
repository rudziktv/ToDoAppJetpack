package com.example.todoapp

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                var name by remember {
                    mutableStateOf("")
                }

                var goals = remember { mutableStateListOf<Goal>() }
                var tasks = remember { mutableStateListOf<Task>() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = { FloatingActionButton(onClick = {
                        goals.add(Goal(title = "New goal", tasks = mutableListOf()))
                    }) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = stringResource(id = R.string.add_content_desc)
                        )
                    } }
                    ) { innerPadding ->
                    MainScreen(goals = goals, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Task(val title: String)
data class Goal(var tasks: List<Task>, val title: String)

@Composable
fun MainScreen (goals: MutableList<Goal>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        content = {
            Text(
                text = "Your tasks",
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            LazyColumn {
                items(goals) {item -> GoalCard(goal = item)}
            }
        }
    )
}

@Composable
fun GoalCard(goal: Goal) {
    Card (modifier = Modifier.fillMaxWidth()) {
        Column (modifier = Modifier.padding(12.dp)) {
            Text(text = goal.title)
            LinearProgressIndicator(
                progress = 1f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        Greeting("Android")
    }
}