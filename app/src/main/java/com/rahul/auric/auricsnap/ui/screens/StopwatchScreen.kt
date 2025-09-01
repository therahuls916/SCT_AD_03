// In: ui/screens/StopwatchScreen.kt

package com.rahul.auric.auricsnap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahul.auric.auricsnap.data.Lap
import com.rahul.auric.auricsnap.ui.theme.AuricSnapTheme
import com.rahul.auric.auricsnap.utils.formatTime
import com.rahul.auric.auricsnap.viewmodels.StopwatchViewModel

@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = viewModel(),
    isInPipMode: Boolean = false
) {
    // ▼▼▼ APPLY THE FIX HERE ▼▼▼
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val laps by viewModel.laps.collectAsState()

    if (isInPipMode) {
        StopwatchPipContent(time = formatTime(elapsedTime))
    } else {
        StopwatchFullContent(
            elapsedTime = elapsedTime,
            isRunning = isRunning,
            laps = laps,
            onStart = { viewModel.start() },
            onPause = { viewModel.pause() },
            onReset = { viewModel.reset() },
            onRecordLap = { viewModel.recordLap() }
        )
    }
}

@Composable
fun StopwatchPipContent(time: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun StopwatchFullContent(
    elapsedTime: Long,
    isRunning: Boolean,
    laps: List<Lap>,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onRecordLap: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = formatTime(elapsedTime),
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = if (isRunning) onPause else onStart) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isRunning) "Pause" else "Start"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onRecordLap, enabled = isRunning) {
                Text("Lap")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onReset) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reset"
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            if (laps.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No laps recorded yet.", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(laps, key = { it.totalTime }) { lap ->
                        LapRow(lap = lap, lapNumber = laps.size - laps.indexOf(lap))
                    }
                }
            }
        }
    }
}

@Composable
fun LapRow(lap: Lap, lapNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Lap $lapNumber", fontSize = 18.sp)
        Text(text = formatTime(lap.lapTime), fontSize = 18.sp)
        Text(text = formatTime(lap.totalTime), fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
    }
}

@Preview(showBackground = true)
@Composable
fun StopwatchScreenPreview() {
    AuricSnapTheme {
        StopwatchFullContent(
            elapsedTime = 12345L,
            isRunning = true,
            laps = listOf(Lap(lapTime = 12345L, totalTime = 12345L)),
            onStart = {},
            onPause = {},
            onReset = {},
            onRecordLap = {}
        )
    }
}