package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CalculationMode
import com.example.ui.components.ChakraEmblem
import com.example.ui.theme.BorderGold
import com.example.ui.theme.Cream
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderHighlight
import com.example.ui.theme.GlassSurface
import com.example.ui.theme.GlassSurfaceSubtle
import com.example.ui.theme.Marigold
import com.example.ui.theme.MarigoldSoft
import com.example.ui.theme.MutedCream

@Composable
fun HomeScreen(
    onSelectMode: (CalculationMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Glass Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                val brandText = buildAnnotatedString {
                    withStyle(SpanStyle(color = Cream)) {
                        append("Prem")
                    }
                    withStyle(SpanStyle(color = Marigold)) {
                        append("Ank")
                    }
                }
                Text(
                    text = brandText,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "PREM KA ANK VIGYAN",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedCream.copy(alpha = 0.8f),
                    letterSpacing = 1.6.sp
                )
            }

            // Glass Badge
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A1A4C).copy(alpha = 0.5f))
                    .border(1.dp, Marigold.copy(alpha = 0.35f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✨",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Animated Spinning Chakra with frosted ring
        ChakraEmblem(
            centerEmoji = "💞",
            isFast = false
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Intro Glass Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.07f),
                            Color.White.copy(alpha = 0.02f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            GlassBorderHighlight,
                            GlassBorder
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Naam, Janam Tithi aur Prem ka Rahasya Jaanein 🔮",
                fontSize = 13.sp,
                color = Cream.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 19.sp
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Mode Selection Options
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CalculationMode.entries.forEach { mode ->
                OptionCard(
                    mode = mode,
                    onClick = { onSelectMode(mode) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom frosted note
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.03f))
                .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Ye app entertainment ke liye hai — asli rishta trust aur pyaar se banta hai 💫",
                fontSize = 11.5.sp,
                color = MutedCream.copy(alpha = 0.75f),
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun OptionCard(
    mode: CalculationMode,
    onClick: () -> Unit,
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
                        Color.White.copy(alpha = 0.22f),
                        Color.White.copy(alpha = 0.06f)
                    )
                ),
                shape = shape
            )
            .shadow(
                elevation = 6.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.5f)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = MarigoldSoft.copy(alpha = 0.3f)),
                onClick = onClick
            )
            .testTag("option_card_${mode.id}"),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
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
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Glass circle around emoji
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.07f))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = mode.icon,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = mode.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Cream
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = mode.subTitle,
                    fontSize = 12.5.sp,
                    color = MutedCream,
                    lineHeight = 17.sp
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Aage",
                tint = MarigoldSoft.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

