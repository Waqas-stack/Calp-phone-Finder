package com.o9tech.clapphonefinder.Screen.ClapDectionScreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.o9tech.clapphonefinder.Foreground.DetectionManager
import com.o9tech.clapphonefinder.Foreground.ForegroundService
import com.o9tech.clapphonefinder.ViewModel.MainViewModel





@Composable
fun ClapDetectionScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val signalDetected by mainViewModel.signalDetected.collectAsState()

    LaunchedEffect(Unit) {
        // Ask for microphone permission if needed
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )
        }
        mainViewModel.startDetection("YES")
    }

//    DisposableEffect(Unit) {
//        // On enter
//        mainViewModel.startDetection("YES")
//
//        onDispose {
//            // On screen leave
//            mainViewModel.stopDetection()
//        }
//    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (signalDetected) "Clap or Whistle Detected!" else "Listening...")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            mainViewModel.stopDetection()
//            DetectionManager.stopDetection()

            // Stop the foreground service
            context.stopService(Intent(context, ForegroundService::class.java))
        }) {
            Text("Stop Detection")
        }

        Button(onClick = {

            mainViewModel.startDetection("YES")
        }) {
            Text("start Detection")
        }
    }
}




//
//
//@Composable
//fun ClapDetectionScreen() {
//    val viewModel: DetectorViewModel = viewModel()
//
//    Column {
//        Text(text = "Clap Detected: ${viewModel.clapDetected}")
//        Spacer(modifier = androidx.compose.ui.Modifier.height(10.dp))
//        Text(text = "Whistle Detected: ${viewModel.whistleDetected}")
//
//        Button(onClick = {
//            viewModel.startDetection(clapValue = "YES")
//        }) {
//            Text(text = "Start Clap Detection")
//        }
//
//        Spacer(modifier = androidx.compose.ui.Modifier.height(10.dp))
//
//        Button(onClick = {
//            viewModel.startDetection(clapValue = "ON")
//        }) {
//            Text(text = "Start Whistle Detection")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewSoundDetection() {
//    ClapDetectionScreen()
//}