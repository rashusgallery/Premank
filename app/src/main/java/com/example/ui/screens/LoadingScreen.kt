package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ChakraEmblem
import com.example.ui.theme.MutedCream

@Composable
fun LoadingScreen(
    loadingMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Fast Spinning Chakra
        ChakraEmblem(
            centerEmoji = "✨",
            isFast = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Animated Cosmic Message
        AnimatedContent(
            targetState = loadingMessage,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "loading_text_transition"
        ) { text ->
            Text(
                text = text,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = MutedCream,
                textAlign = TextAlign.Center
            )
        }
    }
}
