package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.CosmicPurpleDark
import com.example.ui.theme.InkNight
import com.example.ui.theme.NebulaCenter
import kotlin.random.Random

data class Star(
    val xRatio: Float,
    val yRatio: Float,
    val radius: Float,
    val baseAlpha: Float,
    val phaseOffset: Float
)

@Composable
fun CosmicBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "stars_twinkle")
    val twinkleFactor by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "twinkle"
    )

    // Precompute random stars to avoid recomposition jitter
    val stars = remember {
        val random = Random(42)
        List(65) {
            Star(
                xRatio = random.nextFloat(),
                yRatio = random.nextFloat(),
                radius = random.nextFloat() * 1.6f + 0.8f,
                baseAlpha = random.nextFloat() * 0.4f + 0.4f,
                phaseOffset = random.nextFloat()
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        InkNight,
                        CosmicPurpleDark
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw upper-left cosmic nebula glow (20% 30%)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NebulaCenter.copy(alpha = 0.65f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.2f, size.height * 0.3f),
                    radius = size.width * 0.8f
                ),
                center = Offset(size.width * 0.2f, size.height * 0.3f),
                radius = size.width * 0.8f
            )

            // Draw lower-right cosmic nebula glow (80% 70%)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A1A4C).copy(alpha = 0.60f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.8f, size.height * 0.7f),
                    radius = size.width * 0.85f
                ),
                center = Offset(size.width * 0.8f, size.height * 0.7f),
                radius = size.width * 0.85f
            )

            // Draw twinkling stars
            stars.forEach { star ->
                val x = star.xRatio * size.width
                val y = star.yRatio * size.height
                val starAlpha = (star.baseAlpha * (0.6f + 0.4f * ((twinkleFactor + star.phaseOffset) % 1f)))
                    .coerceIn(0.15f, 1f)

                drawCircle(
                    color = Color(0xFFF5EDE0).copy(alpha = starAlpha),
                    radius = star.radius,
                    center = Offset(x, y)
                )
            }
        }

        content()
    }
}
