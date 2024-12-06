package com.jack.learn.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jack.learn.R

class ComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LearnAndroid)
        super.onCreate(savedInstanceState)
        setContent {
//            Splash(offsetState =100.dp, alphaState =0.5f)
//            SimpleList()
//            CounterScreen()
//            AnimatedVisibilityExample()
//            AnimatedContentExample()
            AnimateColorAsStateExample()
        }


    }
}

@Composable
fun AnimatedVisibilityExample() {
    var showText by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            showText =!showText
        }) {
            Text(text = "Toggle Text")
        }
        AnimatedVisibility(visible = showText,
            enter = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(durationMillis = 500)
            )) {
            Text("This is an animated text", modifier = Modifier.padding(20.dp))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentExample() {
    var selectedOption by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = { selectedOption = 1-selectedOption }) {
            Text("Toggle Option")
        }
        AnimatedContent(targetState = selectedOption,
            transitionSpec = {
                if (targetState > initialState) {
                    fadeIn(
                        animationSpec = tween(durationMillis = 500)
                    ) with fadeOut(
                        animationSpec = tween(durationMillis = 500)
                    )
                } else {
                    fadeIn(
                        animationSpec = tween(durationMillis = 500)
                    ) with fadeOut(
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            }) { targetCount ->
            if (targetCount == 0) {
                Text("Option 0", modifier = Modifier.padding(20.dp))
            } else {
                Text("Option 1", modifier = Modifier.padding(20.dp))
            }
        }
    }
}

@Composable
fun AnimateColorAsStateExample() {
    var isPressed by remember {
        mutableStateOf(false)
    }
    val buttonColor by animateColorAsState(targetValue = if (isPressed) Color.Gray else Color.Blue,
        animationSpec = tween(durationMillis = 500)
    )

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Button(onClick = { isPressed = true },
            modifier = Modifier.clickable { isPressed = false },
            colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)) {
            Text("Press Me")
        }
    }
}

@Composable
fun CounterScreen() {
    var count by remember {
        mutableStateOf(0)
    }
    Column {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
        Button(onClick = { count-- }) {
            Text("Decrement")
        }
//        LaunchedEffect(key1 =, block =)
    }
}



@Preview
@Composable
fun SimpleList() {
    val count by remember {
        mutableStateOf(0)
    }
    val sampleModelData = listOf(
        ListItemModel("Title 1", "This is the description for item 1"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 2", "This is the description for item 2"),
        ListItemModel("Title 3", "This is the description for item 3")
    )
    LazyColumn {
        items(sampleModelData) { modelItem ->
            Column {
                Text(text = modelItem.title)
                Text(text = modelItem.description, modifier = Modifier.clickable {
                    Log.d("wangjie", modelItem.title)
                })
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.mipmap.image),
                        contentDescription = stringResource(id = R.string.app_name)
                    )

                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colors.onSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

@Composable
fun Splash(offsetState: Dp, alphaState: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onPrimary), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.mipmap.image),
                contentDescription = stringResource(id = R.string.app_name)
            )

            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable // 加上@Composable注解 就变成可视化的UI
private fun TabItem(@DrawableRes iconId: Int, title: String) {
    Column(Modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(iconId), title, Modifier.size(28.dp), tint = Purple40)
        Text("发现", fontSize = 18.sp, color = Purple40)
    }
}