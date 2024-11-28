package com.jack.learn.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jack.learn.R

class ComposeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LearnAndroid)
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Column {
                    TabItem(R.mipmap.image,"发现")
                }
                Row {
                    Text("as")
                    Text("as")

                }
            }
        }
    }
}

@Composable // 加上@Composable注解 就变成可视化的UI
private fun TabItem(@DrawableRes iconId: Int, title: String) {
    Column(Modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(iconId), title,Modifier.size(28.dp), tint = Purple40)
        Text("发现", fontSize = 18.sp, color = Purple40)
    }
}