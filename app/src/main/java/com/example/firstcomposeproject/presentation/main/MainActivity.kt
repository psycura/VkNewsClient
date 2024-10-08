package com.example.firstcomposeproject.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.example.firstcomposeproject.domain.entities.AuthState

import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            KoinAndroidContext() {
                FirstComposeProjectTheme {
                    val viewModel: MainViewModel = koinViewModel()
                    val authState = viewModel.authState.collectAsState()

                    val launcher = rememberLauncherForActivityResult(
                        contract = VK.getVKAuthActivityResultContract()
                    ) {
                        viewModel.performAuthResult()
                    }

                    when (authState.value) {
                        is AuthState.Authorized -> {
                            VkNewsMainScreen()
                        }

                        AuthState.NotAuthorized -> {
                            LoginScreen {
                                launcher.launch(
                                    listOf(
                                        VKScope.WALL,
                                        VKScope.FRIENDS,
                                        VKScope.GROUPS
                                    )
                                )
                            }
                        }

                        else -> {}
                    }

                }
            }
        }
    }
}


