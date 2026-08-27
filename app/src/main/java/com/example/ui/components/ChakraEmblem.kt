package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardPurple
import com.example.ui.theme.CardPurpleSoft
import com.example.ui.theme.Marigold
import com.example.ui.theme.MarigoldSoft
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ChakraEmblem(
    modifier: Modifier = Modifier,
    centerEmoji: String = "💞",
    isFast: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "chakra_rotation")
    val duration = if (isFast) 1800 else 22000

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing)
        ),
        label = "chakra_angle"
    )

    val glowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16000, easing = LinearEasing)
        ),
        label = "glow_angle"
    )

    Box(
        modifier = modifier
            .size(150.dp)
            .drawBehind {
                // Subtle conic ambient glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Marigold.copy(alpha = 0.25f),
                            Color.Transparent
                        ),
                        radius = size.width * 0.5f
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Rotating Ring with 9 Number Nodes
        Box(
            modifier = Modifier
                .size(150.dp)
                .rotate(rotationAngle),
            contentAlignment = Alignment.Center
        ) {
            val radiusPx = 60f // dp distance from center

            for (i in 0..8) {
                val num = i + 1
                val angleDeg = i * 40.0
                val angleRad = Math.toRadians(angleDeg)
                val offsetX = (radiusPx * cos(angleRad)).dp
                val offsetY = (radiusPx * sin(angleRad)).dp

                Box(
                    modifier = Modifier
                        .offset(x = offsetX, y = offsetY)
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.12f),
                                    Color(0xFF2A1A4C).copy(alpha = 0.7f)
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.35f),
                                    Marigold.copy(alpha = 0.4f)
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$num",
                        color = MarigoldSoft,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Center Emblem Circle
        Box(
            modifier = Modifier
                .size(68.dp)
                .shadow(
                    elevation = 14.dp,
                    shape = CircleShape,
                    ambientColor = Marigold.copy(alpha = 0.4f),
                    spotColor = Marigold
                )
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.15f),
                            Color(0xFF2A1A4C).copy(alpha = 0.85f)
                        )
                    )
                )
                .border(
                    1.5.dp,
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.5f),
                            Marigold
                        )
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = centerEmoji,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
