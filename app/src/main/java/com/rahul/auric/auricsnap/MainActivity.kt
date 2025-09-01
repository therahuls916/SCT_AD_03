// In: MainActivity.kt

package com.rahul.auric.auricsnap

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat // <-- 1. ADD THIS IMPORT
import com.rahul.auric.auricsnap.ui.screens.StopwatchScreen
import com.rahul.auric.auricsnap.ui.theme.AuricSnapTheme
import com.rahul.auric.auricsnap.viewmodels.StopwatchViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: StopwatchViewModel by viewModels()
    private var isInPipMode by mutableStateOf(false)

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "PLAY_PAUSE" -> {
                    if (viewModel.isRunning.value) viewModel.pause() else viewModel.start()
                }
            }
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        isInPipMode = isInPictureInPictureMode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ▼▼▼ 2. REPLACE THE OLD if/else BLOCK WITH THIS SINGLE, SAFE LINE ▼▼▼
        ContextCompat.registerReceiver(
            this,
            broadcastReceiver,
            IntentFilter("PLAY_PAUSE"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        setContent {
            AuricSnapTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isRunning by viewModel.isRunning.collectAsState()

                    LaunchedEffect(isRunning) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            updatePipParams()
                        }
                    }

                    StopwatchScreen(
                        viewModel = viewModel,
                        isInPipMode = this.isInPipMode
                    )
                }
            }
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (viewModel.isRunning.value && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPictureInPictureMode(updatePipParams())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePipParams(): PictureInPictureParams {
        val intent = Intent("PLAY_PAUSE")
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val icon = if (viewModel.isRunning.value) {
            Icon.createWithResource(this, R.drawable.ic_pause)
        } else {
            Icon.createWithResource(this, R.drawable.ic_play)
        }
        val title = if (viewModel.isRunning.value) "Pause" else "Play"

        val remoteAction = RemoteAction(icon, title, title, pendingIntent)

        val params = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(1, 1))
            .setActions(listOf(remoteAction))
            .build()
        setPictureInPictureParams(params)
        return params
    }
}