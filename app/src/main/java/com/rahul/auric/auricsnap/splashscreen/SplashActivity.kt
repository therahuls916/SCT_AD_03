package com.rahul.auric.auricsnap.splashscreen


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.rahul.auric.auricsnap.MainActivity
import com.rahul.auric.auricsnap.R
import com.rahul.auric.auricsnap.ui.theme.AuricSnapTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AuricSnapTheme {
                SplashScreenContent()
            }
        }

        // Use a coroutine to wait for 2 seconds
        lifecycleScope.launch {
            delay(2000) // Wait for 2 seconds
            // Start MainActivity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            // Finish SplashActivity so the user can't go back to it
            finish()
        }
    }
}

@Composable
fun SplashScreenContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Logo is  here. You can control the size with the modifier.
            Image(
                painter = painterResource(id = R.drawable.stopwatch1),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}