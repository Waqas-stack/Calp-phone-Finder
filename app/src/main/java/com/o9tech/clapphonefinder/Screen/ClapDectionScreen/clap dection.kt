////package com.o9tech.clapphonefinder.Screen.ClapDectionScreen
//package com.o9tech.clapphonefinder
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.content.PermissionChecker
//import com.o9tech.clapphonefinder.Screen.ClapDectionScreen.Claspinisg.ClapDetectionService
//
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            Column (
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
//                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
//            ){
//                Text(text = "Clap Detection App")
//                Button(onClick = {
//                    if (isPermissionGranted()) {
//                        startClapDetectionService()
//                    } else {
//                        requestPermissions()
//                    }
//                }) {
//                    Text("Start Clap Detection")
//                }
//            }
//        }
//    }
//
//    // Start the Clap Detection Service
//    private fun startClapDetectionService() {
//        val serviceIntent = Intent(this, ClapDetectionService::class.java)
//        startService(serviceIntent)
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