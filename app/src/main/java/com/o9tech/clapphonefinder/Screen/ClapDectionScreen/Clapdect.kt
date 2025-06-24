package com.o9tech.clapphonefinder.Screen.ClapDectionScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ClapDetectionScreen(clapDetected: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (clapDetected) Color.Green else Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (clapDetected) "👏 Clap Detected!" else "Listening...",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
