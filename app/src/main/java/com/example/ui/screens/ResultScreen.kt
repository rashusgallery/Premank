package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MatchResult
import com.example.ui.components.ScoreRing
import com.example.ui.theme.Cream
import com.example.ui.theme.Marigold
import com.example.ui.theme.MarigoldSoft
import com.example.ui.theme.MutedCream
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(
    result: MatchResult,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var showScore by remember { mutableStateOf(false) }
    var showPros by remember { mutableStateOf(false) }
    var showCons by remember { mutableStateOf(false) }
    var showRemedies by remember { mutableStateOf(false) }
    var showWish by remember { mutableStateOf(false) }
    var showRestart by remember { mutableStateOf(false) }

    LaunchedEffect(result) {
        showScore = true
        delay(600)
        showPros = true
        delay(600)
        showCons = true
        delay(600)
        showRemedies = true
        delay(600)
        showWish = true
        delay(500)
        showRestart = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        // Back Pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                .clickable(onClick = onRestart)
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .testTag("back_button_result")
        ) {
            Text(
                text = "← Naya Ank",
                color = MutedCream,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Step 1: Main Glass Score Card
        AnimatedVisibility(
            visible = showScore,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 4 }
        ) {
            val shape = RoundedCornerShape(28.dp)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.22f),
                                Color.White.copy(alpha = 0.06f)
                            )
                        ),
                        shape = shape
                    )
                    .shadow(
                        elevation = 8.dp,
                        shape = shape,
                        ambientColor = Color.Black.copy(alpha = 0.3f),
                        spotColor = Color.Black.copy(alpha = 0.5f)
                    )
                    .testTag("result_score_card"),
                shape = shape,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color(0xFF2A1A4C).copy(alpha = 0.45f)
                                )
                            )
                        )
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = result.mode.resultLabel.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = MarigoldSoft,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Animated circular progress score
                    ScoreRing(
                        targetScore = result.score,
                        subtitle = "HARMONY"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Verdict Badge in frosted pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.06f))
                            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = result.tier.verdict,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Cream,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Tier Label
                    Text(
                        text = result.tier.label,
                        fontSize = 13.sp,
                        color = MutedCream.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Step 2: Pros (Khoobiyan)
        AnimatedVisibility(
            visible = showPros,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 4 }
        ) {
            ResultListCard(
                title = "✅ Khoobiyan",
                items = result.tier.pros,
                testTag = "result_pros_card"
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Step 3: Cons (Dhyan Dene Yogya)
        AnimatedVisibility(
            visible = showCons,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 4 }
        ) {
            ResultListCard(
                title = "🌱 Dhyan Dene Yogya",
                items = result.tier.cons,
                testTag = "result_cons_card"
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Step 4: Remedies (Upaay)
        AnimatedVisibility(
            visible = showRemedies,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 4 }
        ) {
            ResultListCard(
                title = "🪔 Upaay",
                items = result.tier.remedies,
                testTag = "result_remedies_card"
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Step 5: Shubhkamna (Wish)
        AnimatedVisibility(
            visible = showWish,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 4 }
        ) {
            val shape = RoundedCornerShape(24.dp)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                Marigold.copy(alpha = 0.4f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        ),
                        shape = shape
                    )
                    .shadow(
                        elevation = 6.dp,
                        shape = shape,
                        ambientColor = Marigold.copy(alpha = 0.2f),
                        spotColor = Marigold.copy(alpha = 0.3f)
                    )
                    .testTag("result_wish_card"),
                shape = shape,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Marigold.copy(alpha = 0.08f),
                                    Color(0xFF2A1A4C).copy(alpha = 0.5f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Text(
                        text = "🙏 Shubhkamna",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        color = MarigoldSoft
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = result.wishText,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 22.sp,
                        color = Cream
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Step 6: Restart Button
        AnimatedVisibility(
            visible = showRestart,
            enter = fadeIn(tween(400))
        ) {
            val shape = RoundedCornerShape(20.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(Color.White.copy(alpha = 0.05f))
                    .border(1.dp, Marigold.copy(alpha = 0.6f), shape)
                    .clickable(onClick = onRestart)
                    .padding(vertical = 16.dp)
                    .testTag("restart_button"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "FIR SE DEKHEIN 🔄",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    color = MarigoldSoft
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun ResultListCard(
    title: String,
    items: List<String>,
    testTag: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = shape
            )
            .shadow(
                elevation = 4.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.4f)
            )
            .testTag(testTag),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.07f),
                            Color(0xFF2A1A4C).copy(alpha = 0.40f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                color = MarigoldSoft
            )

            Spacer(modifier = Modifier.height(12.dp))

            items.forEach { itemText ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        color = MarigoldSoft,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = itemText,
                        fontSize = 13.5.sp,
                        color = Cream.copy(alpha = 0.95f),
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

