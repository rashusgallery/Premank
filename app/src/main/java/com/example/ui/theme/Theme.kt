package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PremAnkColorScheme = darkColorScheme(
  primary = Marigold,
  onPrimary = InkNight,
  primaryContainer = CardPurpleSoft,
  onPrimaryContainer = MarigoldSoft,
  secondary = Peacock,
  onSecondary = Cream,
  tertiary = Kumkum,
  onTertiary = Cream,
  background = InkNight,
  onBackground = Cream,
  surface = CardPurple,
  onSurface = Cream,
  surfaceVariant = CardPurpleSoft,
  onSurfaceVariant = MutedCream,
  outline = BorderGold,
  outlineVariant = BorderGold
)

@Composable
fun PremAnkTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = PremAnkColorScheme,
    typography = Typography,
    content = content
  )
}

