package dev.wendyyanto.jetpackcomposelearn

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.wendyyanto.jetpackcomposelearn.ui.theme.JetpackComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetpackComposeLearnTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun MyScreenContent() {
    var counter by remember {
        mutableStateOf(0)
    }

    var names = mutableListOf("1")

    Column(modifier = Modifier.fillMaxHeight()) {
        InputName {
            names.add(it)
            Log.v("Log", names.toString())
        }
        NamesList(names = names, modifier = Modifier.weight(1f))
        Counter(counter = counter, incrementCount = { newCount ->
            counter = newCount
        })
        if (counter > 5) {
            Text(text = "Finally clicked more than 5 times")
        }
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    val targetColor by animateColorAsState(
        targetValue = if (isSelected) {
            Color.Red
        } else {
            Color.Transparent
        },
        animationSpec = tween(durationMillis = 4000)
    )

    Surface(
        color = targetColor,
        modifier = Modifier.clickable { isSelected = isSelected.not() }) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun Counter(counter: Int, incrementCount: (Int) -> Unit) {
    Button(onClick = { incrementCount(counter + 1) }) {
        Text(text = "I have been click $counter times")
    }
}

@Composable
fun InputName(onSubmitName : (String) -> Unit) {
    var name by remember {
        mutableStateOf("")
    }

    TextField(
        value = name,
        onValueChange = { value ->
            name = value
        },
        label = { Text(text = "Input Name") })

    Button(onClick = {
        onSubmitName(name)
        name = ""
    }) {
        Text(text = "Submit Button")
    }
}

@Composable
fun NamesList(names: List<String>, modifier: Modifier = Modifier) {
    // RecycleAdapter only 4 lines, OMG !
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(items = names) {
            Greeting(name = it)
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}