// In: viewmodels/StopwatchViewModel.kt

package com.rahul.auric.auricsnap.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.auric.auricsnap.data.Lap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime = _elapsedTime.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()


    private val _laps = MutableStateFlow<List<Lap>>(emptyList())
    val laps = _laps.asStateFlow()

    init {
        viewModelScope.launch {
            var startTime = System.currentTimeMillis()
            isRunning.collect { running ->
                if (running) {
                    startTime = System.currentTimeMillis() - _elapsedTime.value
                    while (_isRunning.value) {
                        _elapsedTime.value = System.currentTimeMillis() - startTime
                        delay(10L)
                    }
                }
            }
        }
    }

    fun start() { _isRunning.value = true }
    fun pause() { _isRunning.value = false }

    fun reset() {
        _isRunning.value = false
        _elapsedTime.value = 0L
        _laps.value = emptyList() // Also reset the laps
    }

    // FUNCTION TO RECORD A LAP
    fun recordLap() {
        if (!_isRunning.value) return

        val totalTimeOfPreviousLaps = _laps.value.sumOf { it.lapTime }
        val currentLapTime = _elapsedTime.value - totalTimeOfPreviousLaps

        val newLap = Lap(
            lapTime = currentLapTime,
            totalTime = _elapsedTime.value
        )

        _laps.value = listOf(newLap) + _laps.value
    }
}