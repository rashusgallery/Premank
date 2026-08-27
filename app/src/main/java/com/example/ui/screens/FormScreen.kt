package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CalculationMode
import com.example.ui.theme.CardPurple
import com.example.ui.theme.Cream
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderHighlight
import com.example.ui.theme.InkNight
import com.example.ui.theme.Kumkum
import com.example.ui.theme.Marigold
import com.example.ui.theme.MarigoldSoft
import com.example.ui.theme.MutedCream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    mode: CalculationMode,
    nameA: String,
    dobA: String,
    nameB: String,
    dobB: String,
    validationError: String?,
    onNameAChange: (String) -> Unit,
    onDobAChange: (String) -> Unit,
    onNameBChange: (String) -> Unit,
    onDobBChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    var showDatePickerA by remember { mutableStateOf(false) }
    var showDatePickerB by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        // Back Button in Frosted Pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                .clickable(onClick = onBack)
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .testTag("back_button_form")
        ) {
            Text(
                text = "← Peeche",
                color = MutedCream,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Form Title
        Text(
            text = mode.formTitle,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            color = Cream
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Form Subtitle
        Text(
            text = mode.formSub,
            fontSize = 13.sp,
            color = MutedCream,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Person A Block
        PersonInputCard(
            title = "Aap 🌙",
            name = nameA,
            dob = dobA,
            namePlaceholder = "Apna naam likhein",
            nameTestTag = "name_a_input",
            dobTestTag = "dob_a_input",
            onNameChange = onNameAChange,
            onOpenDatePicker = {
                focusManager.clearFocus()
                showDatePickerA = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Person B Block
        PersonInputCard(
            title = "Saathi 💫",
            name = nameB,
            dob = dobB,
            namePlaceholder = "Unka naam likhein",
            nameTestTag = "name_b_input",
            dobTestTag = "dob_b_input",
            onNameChange = onNameBChange,
            onOpenDatePicker = {
                focusManager.clearFocus()
                showDatePickerB = true
            }
        )

        if (validationError != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Kumkum.copy(alpha = 0.18f))
                    .border(1.dp, Kumkum.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "⚠️ $validationError",
                    color = Color(0xFFFFB3B3),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // CTA Submit Button with Gradient
        val buttonShape = RoundedCornerShape(20.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = buttonShape,
                    ambientColor = Kumkum.copy(alpha = 0.5f),
                    spotColor = Kumkum
                )
                .clip(buttonShape)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Kumkum, Marigold)
                    )
                )
                .clickable {
                    focusManager.clearFocus()
                    onSubmit()
                }
                .testTag("submit_button")
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ANK DEKHEIN ✨",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = InkNight
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }

    // Date Picker Modal for Person A
    if (showDatePickerA) {
        AppDatePickerDialog(
            title = "Apni Janam Tithi Chunein",
            onDateSelected = { selectedDateString ->
                onDobAChange(selectedDateString)
                showDatePickerA = false
            },
            onDismiss = { showDatePickerA = false }
        )
    }

    // Date Picker Modal for Person B
    if (showDatePickerB) {
        AppDatePickerDialog(
            title = "Saathi Ki Janam Tithi Chunein",
            onDateSelected = { selectedDateString ->
                onDobBChange(selectedDateString)
                showDatePickerB = false
            },
            onDismiss = { showDatePickerB = false }
        )
    }
}

@Composable
fun PersonInputCard(
    title: String,
    name: String,
    dob: String,
    namePlaceholder: String,
    nameTestTag: String,
    dobTestTag: String,
    onNameChange: (String) -> Unit,
    onOpenDatePicker: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(28.dp)

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
            ),
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
                .padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MarigoldSoft
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Name Field
            Text(
                text = "Naam",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
                color = MutedCream
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                placeholder = {
                    Text(
                        text = namePlaceholder,
                        color = MutedCream.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Naam",
                        tint = MarigoldSoft.copy(alpha = 0.75f),
                        modifier = Modifier.size(18.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0x33120823),
                    unfocusedContainerColor = Color(0x33120823),
                    focusedBorderColor = Marigold,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                    focusedTextColor = Cream,
                    unfocusedTextColor = Cream,
                    cursorColor = Marigold
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(nameTestTag)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // DOB Field
            Text(
                text = "Janam Tithi",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
                color = MutedCream
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0x33120823))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
                    .clickable(onClick = onOpenDatePicker)
                    .padding(horizontal = 14.dp, vertical = 14.dp)
                    .testTag(dobTestTag)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Janam Tithi",
                        tint = MarigoldSoft.copy(alpha = 0.75f),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = if (dob.isNotEmpty()) dob else "YYYY-MM-DD chunein",
                        color = if (dob.isNotEmpty()) Cream else MutedCream.copy(alpha = 0.5f),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    title: String,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val todayMillis = remember { System.currentTimeMillis() }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= todayMillis
            }
            override fun isSelectableYear(year: Int): Boolean {
                return year <= 2026
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        sdf.timeZone = TimeZone.getTimeZone("UTC")
                        onDateSelected(sdf.format(Date(millis)))
                    }
                }
            ) {
                Text("Theek Hai", color = Marigold, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Radd Karein", color = MutedCream)
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color(0xFF241640)
        )
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    text = title,
                    modifier = Modifier.padding(16.dp),
                    color = MarigoldSoft,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFF241640),
                titleContentColor = MarigoldSoft,
                headlineContentColor = Cream,
                weekdayContentColor = MutedCream,
                subheadContentColor = Cream,
                yearContentColor = Cream,
                currentYearContentColor = Marigold,
                selectedYearContentColor = InkNight,
                selectedYearContainerColor = Marigold,
                dayContentColor = Cream,
                selectedDayContentColor = InkNight,
                selectedDayContainerColor = Marigold,
                todayDateBorderColor = Marigold,
                todayContentColor = MarigoldSoft
            )
        )
    }
}

