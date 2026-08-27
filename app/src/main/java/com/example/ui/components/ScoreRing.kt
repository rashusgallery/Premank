package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Cream
import com.example.ui.theme.Marigold
import com.example.ui.theme.MutedCream
import kotlin.math.roundToInt

@Composable
fun ScoreRing(
    targetScore: Int,
    subtitle: String = "HARMONY",
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(targetScore) {
        animatedProgress.animateTo(
            targetValue = targetScore / 100f,
            animationSpec = tween(
                durationMillis = 1400,
                easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
            )
        )
    }

    val currentScoreInt = (animatedProgress.value * 100).roundToInt()

    Box(
        modifier = modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(145.dp)) {
            val strokeWidthPx = 10.dp.toPx()
            val diameter = size.minDimension - strokeWidthPx
            val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
            val arcSize = Size(diameter, diameter)

            // Background Track
            drawArc(
                color = Color(0x1AF5EDE0),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidthPx)
            )

            // Foreground Progress Arc (starts from top, -90 degrees)
            val sweep = 360f * animatedProgress.value
            if (sweep > 0f) {
                drawArc(
                    color = Marigold,
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(
                        width = strokeWidthPx,
                        cap = StrokeCap.Round
                    )
                )
            }
        }

        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$currentScoreInt%",
                fontSize = 38.sp,
                fontWeight = FontWeight.Light,
                color = Cream
            )
            Text(
                text = subtitle.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.8.sp,
                color = MutedCream.copy(alpha = 0.85f)
            )
        }
    }
}

