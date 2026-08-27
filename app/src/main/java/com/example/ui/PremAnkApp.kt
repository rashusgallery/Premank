package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.AppScreen
import com.example.PremAnkViewModel
import com.example.ui.components.AppFooter
import com.example.ui.components.CosmicBackground
import com.example.ui.screens.FormScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoadingScreen
import com.example.ui.screens.ResultScreen

@Composable
fun PremAnkApp(
    viewModel: PremAnkViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CosmicBackground {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            bottomBar = {
                AppFooter()
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.statusBars),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 480.dp)
                ) {
                    AnimatedContent(
                        targetState = uiState.currentScreen,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(350)) togetherWith
                                    fadeOut(animationSpec = tween(250))
                        },
                        label = "screen_transition"
                    ) { screen ->
                        when (screen) {
                            AppScreen.HOME -> {
                                HomeScreen(
                                    onSelectMode = { mode ->
                                        viewModel.selectMode(mode)
                                    }
                                )
                            }
                            AppScreen.FORM -> {
                                FormScreen(
                                    mode = uiState.selectedMode,
                                    nameA = uiState.nameA,
                                    dobA = uiState.dobA,
                                    nameB = uiState.nameB,
                                    dobB = uiState.dobB,
                                    validationError = uiState.validationError,
                                    onNameAChange = { viewModel.updateNameA(it) },
                                    onDobAChange = { viewModel.updateDobA(it) },
                                    onNameBChange = { viewModel.updateNameB(it) },
                                    onDobBChange = { viewModel.updateDobB(it) },
                                    onSubmit = { viewModel.submitForm() },
                                    onBack = { viewModel.navigateToHome() }
                                )
                            }
                            AppScreen.LOADING -> {
                                LoadingScreen(
                                    loadingMessage = uiState.loadingMessage
                                )
                            }
                            AppScreen.RESULT -> {
                                uiState.matchResult?.let { result ->
                                    ResultScreen(
                                        result = result,
                                        onRestart = { viewModel.navigateToHome() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
