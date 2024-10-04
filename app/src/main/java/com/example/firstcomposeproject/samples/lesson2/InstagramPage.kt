package com.example.firstcomposeproject.samples.lesson2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InstagramPage(vm: InstagramMainVM) {

    val profiles = vm.models.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn {
            items(
                profiles.value,
                key = { it.id }
            ) { profile ->
                val dismissSate = rememberSwipeToDismissBoxState()

                if (dismissSate.currentValue == SwipeToDismissBoxValue.EndToStart && dismissSate.progress >= .5f) {
                    vm.deleteProfile(profile)
                }

                SwipeToDismissBox(
                    state = dismissSate,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxSize()
                                .animateItem()
                                .padding(16.dp)
                                .background(Color.Red.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Delete",
                                modifier = Modifier.padding(16.dp),
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                    }
                ) {
                    InstagramProfile(
                        profile,
                        onChangeFollowingStatusClick = vm::changeFollowingStatus
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstagramPagePreviewLight() {
    val viewModel = InstagramMainVM()
    FirstComposeProjectTheme(darkTheme = false) {
        InstagramPage(viewModel)
    }

}

@Preview(showBackground = true)
@Composable
fun InstagramPagePreviewDark() {
    val viewModel = InstagramMainVM()

    FirstComposeProjectTheme(darkTheme = true) {
        InstagramPage(viewModel)
    }
}