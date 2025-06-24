package com.o9tech.clapphonefinder.Screen.ClapDectionScreen.Claspinisg
//
//import android.app.Activity
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.content.PermissionChecker
//
//
//@Composable
//fun Clapinfscreen(
//    onAnswer: () -> Unit,
//) {
//
//    val context = LocalContext.current
//    fun isPermissionGranted(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.RECORD_AUDIO
//        ) == PermissionChecker.PERMISSION_GRANTED
//    }
//
//
//     fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            context as Activity,
//            arrayOf(android.Manifest.permission.RECORD_AUDIO),
//            1
//        )
//    }
//
//
//
//
//    Scaffold (
//        content = { paddingValues ->
//            Surface (
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//            ){
//                Column {
//                    Text(text = "Clap Detection App")
//                    Button(onClick = {
//                        if (isPermissionGranted()) {
//                            startClapDetectionService()
//                        } else {
//                            requestPermissions()
//                        }
//                    }) {
//                        Text("Start Clap Detection")
//                    }
//                }
//            }
//        }
//    )
//}