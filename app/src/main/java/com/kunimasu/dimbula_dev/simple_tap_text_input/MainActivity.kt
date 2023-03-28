package com.kunimasu.dimbula_dev.simple_tap_text_input

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kunimasu.dimbula_dev.simple_tap_text_input.ui.theme.SimpletapinputtextTheme

// hogehoge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpletapinputtextTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MainScreen() {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val todoList: SnapshotStateList<String> by remember { mutableStateOf(mutableStateListOf()) }
    var text: String by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            enabled = true,
            indication = null,
            onClick = { focusManager.clearFocus() }
        )
    ) {
        TopAppBar(
            title = { Text("Todo List") }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(todoList) { i, todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height + 4.dp.toPx()
                            drawLine(
                                Color.DarkGray,
                                Offset(0f, y),
                                Offset(size.width, y),
                                strokeWidth
                            )
                        }
                ) {
                    Text(
                        todo,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.size(16.dp))
                    Button(
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White,
                            disabledContentColor = Color.LightGray
                        ),
                        onClick = {
                            todoList.removeAt(i)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text("DONE")
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                maxLines = 1,
                singleLine = true,
                onValueChange = { text = it },
                label = { Text("ToDo") },
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (text.isEmpty()) return@Button
                    todoList.add(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically).focusRequester(focusRequester)
            ) {
                Text("ADD")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpletapinputtextTheme {
        MainScreen()
    }
}