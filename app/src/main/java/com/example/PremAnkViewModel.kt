package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CalculationMode
import com.example.model.MatchResult
import com.example.model.NumerologyEngine
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AppScreen {
    HOME,
    FORM,
    LOADING,
    RESULT
}

data class PremAnkUiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val selectedMode: CalculationMode = CalculationMode.LOVE,
    val nameA: String = "",
    val dobA: String = "",
    val nameB: String = "",
    val dobB: String = "",
    val validationError: String? = null,
    val loadingMessage: String = "Sitaron se sanket le rahe hain...",
    val matchResult: MatchResult? = null
)

class PremAnkViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PremAnkUiState())
    val uiState: StateFlow<PremAnkUiState> = _uiState.asStateFlow()

    private val loadingMessages = listOf(
        "Sitaron se sanket le rahe hain...",
        "Naam ka ank nikal rahe hain...",
        "Janam tithi ka hisaab laga rahe hain...",
        "Prem ka rishta jod rahe hain...",
        "Bas kuch pal aur..."
    )

    private var loadingJob: Job? = null

    fun selectMode(mode: CalculationMode) {
        _uiState.update {
            it.copy(
                selectedMode = mode,
                nameA = "",
                dobA = "",
                nameB = "",
                dobB = "",
                validationError = null,
                currentScreen = AppScreen.FORM
            )
        }
    }

    fun updateNameA(name: String) {
        _uiState.update { it.copy(nameA = name.take(40), validationError = null) }
    }

    fun updateDobA(dob: String) {
        _uiState.update { it.copy(dobA = dob, validationError = null) }
    }

    fun updateNameB(name: String) {
        _uiState.update { it.copy(nameB = name.take(40), validationError = null) }
    }

    fun updateDobB(dob: String) {
        _uiState.update { it.copy(dobB = dob, validationError = null) }
    }

    fun navigateToHome() {
        loadingJob?.cancel()
        _uiState.update { it.copy(currentScreen = AppScreen.HOME, validationError = null) }
    }

    fun submitForm() {
        val state = _uiState.value
        val nameA = state.nameA.trim()
        val dobA = state.dobA.trim()
        val nameB = state.nameB.trim()
        val dobB = state.dobB.trim()

        if (nameA.isEmpty()) {
            _uiState.update { it.copy(validationError = "Kripya apna naam darj karein.") }
            return
        }
        if (dobA.isEmpty()) {
            _uiState.update { it.copy(validationError = "Kripya apni janam tithi chunein.") }
            return
        }
        if (nameB.isEmpty()) {
            _uiState.update { it.copy(validationError = "Kripya saathi ka naam darj karein.") }
            return
        }
        if (dobB.isEmpty()) {
            _uiState.update { it.copy(validationError = "Kripya saathi ki janam tithi chunein.") }
            return
        }

        // Validate dates are not future
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val dateA = dateFormat.parse(dobA)
            val dateB = dateFormat.parse(dobB)
            val today = Date()
            if (dateA != null && dateA.after(today)) {
                _uiState.update { it.copy(validationError = "Aapki janam tithi bhavishya ki nahi ho sakti.") }
                return
            }
            if (dateB != null && dateB.after(today)) {
                _uiState.update { it.copy(validationError = "Saathi ki janam tithi bhavishya ki nahi ho sakti.") }
                return
            }
        } catch (_: Exception) {
            // Ignore parsing error if format varies
        }

        // Proceed to loading screen
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.LOADING,
                loadingMessage = loadingMessages[0],
                validationError = null
            )
        }

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            var msgIndex = 0
            val startTime = System.currentTimeMillis()
            val totalDuration = 4500L

            while (System.currentTimeMillis() - startTime < totalDuration) {
                delay(900L)
                msgIndex = (msgIndex + 1) % loadingMessages.size
                _uiState.update { it.copy(loadingMessage = loadingMessages[msgIndex]) }
            }

            // Calculate score
            val result = NumerologyEngine.calculateScore(
                mode = state.selectedMode,
                nameA = nameA,
                dobA = dobA,
                nameB = nameB,
                dobB = dobB
            )

            _uiState.update {
                it.copy(
                    currentScreen = AppScreen.RESULT,
                    matchResult = result
                )
            }
        }
    }
}
