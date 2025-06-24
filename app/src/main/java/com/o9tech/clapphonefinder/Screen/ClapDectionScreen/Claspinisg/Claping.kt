//package com.o9tech.clapphonefinder.Screen.ClapDectionScreen.Claspinisg
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.content.PermissionChecker
//import dagger.hilt.android.AndroidEntryPoint
//
//
////@AndroidEntryPoint
////class MainActivity : ComponentActivity() {
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContent {
////            // UI in Jetpack Compose
////            Column {
////                Text(text = "Clap Detection App")
////                Button(onClick = {
////                    if (isPermissionGranted()) {
////                        startClapDetectionService()
////                    } else {
////                        requestPermissions()
////                    }
////
////                }
////
////                ) {
////                    Text("Start Clap Detection")
////                }
////            }
////        }
////    }
////
////    // Start the Clap Detection Service
////    private fun startClapDetectionService() {
////        val serviceIntent = Intent(this, ClapDetectionService::class.java)
////        startService(serviceIntent)
////    }
////
////    // Check if the necessary permissions are granted
////    private fun isPermissionGranted(): Boolean {
////        return ContextCompat.checkSelfPermission(
////            this,
////            android.Manifest.permission.RECORD_AUDIO
////        ) == PermissionChecker.PERMISSION_GRANTED
////    }
////
////    // Request permissions
////        private fun requestPermissions() {
////            ActivityCompat.requestPermissions(
////                this,
////                arrayOf(android.Manifest.permission.RECORD_AUDIO),
////                1
////            )
////        }
////
////    // Handle permissions request result
////    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
////            startClapDetectionService()
////        } else {
////            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
////        }
////    }
////}
//
//
//
//
//
/////
//
//
//
////
////
////package com.example.clapdetector
////
////import android.content.Intent
////import android.os.Bundle
////import android.widget.Button
////import android.widget.Toast
////import androidx.activity.ComponentActivity
////import androidx.activity.compose.setContent
////import androidx.core.app.ActivityCompat
////import androidx.core.content.ContextCompat
////import androidx.core.content.PermissionChecker
////
////class MainActivity : ComponentActivity() {
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContent {
////            // UI in Jetpack Compose
////            Column {
////                Text(text = "Clap Detection App")
////                Button(onClick = {
////                    if (isPermissionGranted()) {
////                        startClapDetectionService()
////                    } else {
////                        requestPermissions()
////                    }
////                }) {
////                    Text("Start Clap Detection")
////                }
////            }
////        }
////    }
////
////    // Start the Clap Detection Service
////    private fun startClapDetectionService() {
////        val serviceIntent = Intent(this, ClapDetectionService::class.java)
////        startService(serviceIntent)
////    }
////
////    // Check if the necessary permissions are granted
////    private fun isPermissionGranted(): Boolean {
////        return ContextCompat.checkSelfPermission(
////            this,
////            android.Manifest.permission.RECORD_AUDIO
////        ) == PermissionChecker.PERMISSION_GRANTED
////    }
////
////    // Request permissions
////    private fun requestPermissions() {
////        ActivityCompat.requestPermissions(
////            this,
////            arrayOf(android.Manifest.permission.RECORD_AUDIO),
////            1
////        )
////    }
////
////    // Handle permissions request result
////    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
////            startClapDetectionService()
////        } else {
////            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
////        }
////    }
////}
//
//
//
//@AndroidEntryPoint
//class ClapDetectionActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//
//            Scaffold (
//                modifier = Modifier.fillMaxSize(),
//                content = { it
//                    // Main content of the screen
//                    Column (
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        Text(text = "Clap Detection App")
//
//                        // Start Service Button
//                        Button(onClick = {
//                            if (isPermissionGranted()) {
//                                startClapDetectionService()
//                            } else {
//                                requestPermissions()
//                            }
//                        }) {
//                            Text("Start Clap Detection")
//                        }
//
//                        Text(text = "Clap Detection App")
//
//
//                        // Stop Service Button
//                        Button(onClick = {
//                            stopClapDetectionService()
//                        }) {
//                            Text("Stop Clap Detection")
//                        }
//                    }
//                }
//            )
//            // UI in Jetpack Compose
//
//        }
//    }
//
//    // Start the Clap Detection Service
//    private fun startClapDetectionService() {
//        val serviceIntent = Intent(this, ClapDetectionService::class.java)
//        startService(serviceIntent)
//    }
//
//    // Stop the Clap Detection Service
//    private fun stopClapDetectionService() {
//        val serviceIntent = Intent(this, ClapDetectionService::class.java)
//        stopService(serviceIntent)
//    }
//
//    // Check if the necessary permissions are granted
//    private fun isPermissionGranted(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            android.Manifest.permission.RECORD_AUDIO
//        ) == PermissionChecker.PERMISSION_GRANTED
//    }
//
//    // Request permissions
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(android.Manifest.permission.RECORD_AUDIO),
//            1
//        )
//    }
//
//    // Handle permissions request result
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
//            startClapDetectionService()
//        } else {
//            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//}