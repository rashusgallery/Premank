package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MarigoldSoft
import com.example.ui.theme.MutedCream

@Composable
fun AppFooter(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xCC180D30))
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.04f),
                        Color.White.copy(alpha = 0.16f),
                        Color.White.copy(alpha = 0.04f)
                    )
                ),
                shape = RectangleShape
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        contentAlignment = Alignment.Center
    ) {
        val annotatedText = buildAnnotatedString {
            withStyle(SpanStyle(color = MutedCream.copy(alpha = 0.8f), fontSize = 11.sp)) {
                append("Design & Development ")
            }
            withStyle(
                SpanStyle(
                    color = MarigoldSoft,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            ) {
                append("INFO-AI")
            }
            withStyle(SpanStyle(color = MutedCream.copy(alpha = 0.6f), fontSize = 11.sp)) {
                append("  •  ")
            }
            withStyle(SpanStyle(color = MutedCream.copy(alpha = 0.8f), fontSize = 11.sp)) {
                append("Powered by ")
            }
            withStyle(
                SpanStyle(
                    color = MarigoldSoft,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            ) {
                append("RASHUIN")
            }
        }

        Text(
            text = annotatedText,
            textAlign = TextAlign.Center
        )
    }
}

