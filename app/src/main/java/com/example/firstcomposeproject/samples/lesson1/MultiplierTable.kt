package com.example.firstcomposeproject.samples.lesson1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun MultiplierTable() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        repeat(9) { row: Int ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                repeat(9) { column: Int ->

                    val value = (row + 1) * (column + 1)
                    val color = if ((row + 1 + column + 1) % 2 == 0) {
                        Color.Yellow
                    } else {
                        Color.White

                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(color)
                            .border(.5.dp, Color.Black),
                    ) {
                        Text("$value")
                    }
                }
            }
        }
    }

}
