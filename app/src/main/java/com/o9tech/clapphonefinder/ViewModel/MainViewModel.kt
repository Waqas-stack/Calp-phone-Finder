package com.o9tech.clapphonefinder.ViewModel

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.o9tech.clapphonefinder.Services.DetectorThread
import com.o9tech.clapphonefinder.Services.OnSignalsDetectedListener
import com.o9tech.clapphonefinder.Services.RecorderThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import android.content.Intent
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.lifecycle.viewModelScope
import com.o9tech.clapphonefinder.Foreground.DetectionManager
import com.o9tech.clapphonefinder.Foreground.DetectionManager.flashEnabled
import com.o9tech.clapphonefinder.Foreground.DetectionManager.selectedSoundUri
import com.o9tech.clapphonefinder.Foreground.DetectionManager.stopSound
import com.o9tech.clapphonefinder.Foreground.DetectionManager.vibrationEnabled
import com.o9tech.clapphonefinder.Foreground.ForegroundService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.Handler


//@HiltViewModel
//class MainViewModel  @Inject constructor() : ViewModel() {
//
//
//
//    private val recorderThread = RecorderThread()
////    private lateinit var detectorThread: DetectorThread
//private var detectorThread: DetectorThread? = null
//
//
//    private val _signalDetected = MutableStateFlow(false)
//    val signalDetected = _signalDetected.asStateFlow()
//
//
////    fun startDetection(mode: String = "YES") {
////        if (this::detectorThread.isInitialized) {
////            // Prevent double starting
////            return
////        }
////
////        recorderThread.start()
////
////        detectorThread = DetectorThread(recorderThread, mode)
////        detectorThread?.setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
////            override fun onWhistleDetected() {
////                // _signalDetected.value = true
////            }
////
////            override fun onClapDetected() {
////                _signalDetected.value = true
////            }
////        })
////
////        detectorThread.start()
////    }
//
//
//    fun startDetection(context: Context,mode: String = "YES") {
//        if (detectorThread != null) {
//            // Prevent double starting
//            return
//        }
//
//        recorderThread.start()
//
//        detectorThread = DetectorThread(recorderThread, mode).apply {
//            setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
//                override fun onWhistleDetected() {
//                    // Optional: handle whistle
//                }
//
//                override fun onClapDetected() {
//                    _signalDetected.value = true
//                    playSound(context) // Play sound when clap is detected
//                }
//            })
//            start()
//        }
//    }
//
//
//
////    fun startDetection(mode: String = "YES") {
////        recorderThread.start()
////
////        detectorThread = DetectorThread(recorderThread, mode)
////        detectorThread.setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
////            override fun onWhistleDetected() {
//////                _signalDetected.value = true
////            }
////
////            override fun onClapDetected() {
////                _signalDetected.value = true
////            }
////        })
////
////        detectorThread.start()
////    }
//
////    fun stopDetection() {
////        recorderThread.stopRecording()
////        detectorThread.stopDetection()
////    }
//
////    fun stopDetection() {
////
////        recorderThread.stopRecording()
////        if (this::detectorThread.isInitialized) {
////            detectorThread.stopDetection()
////        }
////        _signalDetected.value = false
////    }
//
//
//    fun stopDetection() {
//        detectorThread?.stopDetection()
//        detectorThread = null
//
//        recorderThread.stopRecording()
//        _signalDetected.value = false
//    }
//
//
//
//
//
//}











@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ViewModel() {




//    private var recorderThread = RecorderThread()
    private var detectorThread: DetectorThread? = null
    private var recorderThread: RecorderThread? = null



    private val _signalDetected = MutableStateFlow(false)
    val signalDetected = _signalDetected.asStateFlow()

    // This flag ensures the service is started only once
    private var isServiceRunning = false


    private val _isDetectionActive = MutableStateFlow(false)
    val isDetectionActive = _isDetectionActive.asStateFlow()


//    fun startDetection(mode: String = "YES") {
//        if (detectorThread != null) {
//            // Prevent double starting of the detection
//            return
//        }
//
//        recorderThread.start()
//
//        detectorThread = DetectorThread(recorderThread, mode).apply {
//            setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
//                override fun onWhistleDetected() {
//                    // Handle whistle detection if necessary
//                }
//
//                override fun onClapDetected() {
//                    _signalDetected.value = true
//                    playSound()
//                    startForegroundService() // Start the foreground service when clap is detected
//                }
//            })
//            start()
//        }
//    }



//its working fine but i want to avoid restarting the service if its already running
    fun startDetectiony(mode: String = "YES") {
        // Avoid restarting if already running
        if (DetectionManager.detectorThread?.isAlive == true) return

        // Create and start recorderThread
        DetectionManager.recorderThread = RecorderThread()
        DetectionManager.recorderThread?.start()

        // Create and start detectorThread
        DetectionManager.detectorThread = DetectorThread(DetectionManager.recorderThread, mode).apply {
            setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
                override fun onWhistleDetected() {
                    _signalDetected.value = false
                }

                override fun onClapDetected() {
                    _signalDetected.value = true
                    playSound(context)
                    startForegroundService()
                }
            })
            start()
        }
    }


    //thisis new

    private var lastClapTime = 0L
    private val clapCooldownMillis = 3000L // 3 seconds



    fun startDetection(mode: String = "YES") {
        if (DetectionManager.detectorThread?.isAlive == true) return

        DetectionManager.recorderThread = RecorderThread().also { it.start() }

        DetectionManager.detectorThread = DetectorThread(DetectionManager.recorderThread, mode).apply {
            setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
                override fun onWhistleDetected() {
                    _signalDetected.value = false
                }
                override fun onClapDetected() {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClapTime >= clapCooldownMillis) {
                        lastClapTime = currentTime
                        _signalDetected.value = true
//                        DetectionManager.playSound(context)
                        DetectionManager.playSoundss(context)
                        startForegroundService()
                    } else {
                        Log.d("Clap", "Ignored due to cooldown")
                    }
                }


//                override fun onClapDetected() {
//                    _signalDetected.value = true
//                    DetectionManager.playSound(context)
//                    startForegroundService()
//                }
            })
            start()
        }
        _isDetectionActive.value = true

    }





    fun scheduleStopAt(selectedTime: String) {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val now = Calendar.getInstance()
        val stopTime = Calendar.getInstance()

        try {
            val parsedTime = formatter.parse(selectedTime)
            stopTime.time = parsedTime!!
            stopTime.set(Calendar.YEAR, now.get(Calendar.YEAR))
            stopTime.set(Calendar.MONTH, now.get(Calendar.MONTH))
            stopTime.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH))

            if (stopTime.before(now)) {
                stopTime.add(Calendar.DAY_OF_MONTH, 1) // If selected time has passed today
            }

            val delayMillis = stopTime.timeInMillis - now.timeInMillis

            viewModelScope.launch {
                delay(delayMillis)
                stopDetection()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Stop detection and service
    fun stopDetection() {
        DetectionManager.stopDetection()
        stopForegroundService()
        _signalDetected.value = false
        _isDetectionActive.value = false

    }








//    fun startDetection(mode: String = "YES") {
//        // Avoid restarting if already running
//        if (detectorThread?.isAlive == true) return
//
//        // Always create a new recorderThread
//        recorderThread = RecorderThread() // <-- recreate thread
//        recorderThread?.start()
//
//        detectorThread = DetectorThread(recorderThread, mode).apply {
//            setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
//                override fun onWhistleDetected() {
//                    _signalDetected.value = false
//                    // optional
//                }
//
//                override fun onClapDetected() {
//                    _signalDetected.value = true
//                    playSound()
//                    startForegroundService() // Start only once
//                }
//            })
//            start()
//        }
//    }


//    fun stopDetection() {
//        // Stop the detector thread and the recorder
//        detectorThread?.stopDetection()
//        detectorThread = null
//        recorderThread?.stopRecording()
//
//        _signalDetected.value = false
//
//        stopForegroundService() // Stop the service when detection is stopped
//    }

//    fun stopDetection() {
//        detectorThread?.interrupt()
//        recorderThread?.interrupt()
//        detectorThread = null
//        recorderThread = null
//    }


    // Start the foreground service only once
//    private fun startForegroundService() {
//        if (!isServiceRunning) {
//            val intent = Intent(context, ForegroundService::class.java)
////            context.startService(intent) // Start the service
//            ContextCompat.startForegroundService(context, intent) // ✅ fix here
//
//            isServiceRunning = true
//        }
//    }

    private fun startForegroundService() {
        val intent = Intent(context, ForegroundService::class.java)
        ContextCompat.startForegroundService(context, intent)
        isServiceRunning = true // Keep this
    }


    // Stop the foreground service when detection is stopped
    private fun stopForegroundService() {
        if (isServiceRunning) {
            val intent = Intent(context, ForegroundService::class.java)
            context.stopService(intent) // Stop the service
            isServiceRunning = false
        }
    }

    private fun playSound() {
        try {
            val assetFileDescriptor = context.assets.openFd("gazette.mp3")
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}



 fun playSound(context: Context) {
    try {
        val assetFileDescriptor = context.assets.openFd("gazette.mp3")
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        mediaPlayer.prepare()
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun playSoundWithEffects(context: Context) {
    try {
        // Read user preferences
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isFlashEnabled = prefs.getBoolean("flash_mode", false)
        val isVibrationEnabled = prefs.getBoolean("vibration_mode", false)

        // Play sound
        val afd = context.assets.openFd("gazette.mp3")
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            prepare()
            start()
        }

        // Vibration
        if (isVibrationEnabled) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(500)
            }
        }

        // Flash
//        if (isFlashEnabled) {
//            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
//                cameraManager.getCameraCharacteristics(id)
//                    .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
//            }
//            cameraId?.let {
//                cameraManager.setTorchMode(it, true)
//                Handler(Looper.getMainLooper()).postDelayed({
//                    cameraManager.setTorchMode(it, false)
//                }, 500)
//            }
//        }


        if (isFlashEnabled) {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                cameraManager.getCameraCharacteristics(id)
                    .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }

            cameraId?.let { id ->
                try {
                    cameraManager.setTorchMode(id, true)

                    // Use a coroutine instead of Handler to avoid Looper issues
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500) // Flash on for 500ms
                        cameraManager.setTorchMode(id, false)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    } catch (e: Exception) {
        e.printStackTrace()
    }
}



fun triggerVibration(context: Context) {
    try {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun triggerFlash(context: Context) {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
        cameraManager.getCameraCharacteristics(id)
            .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
    }

    cameraId?.let {
        try {
            cameraManager.setTorchMode(it, true)
            CoroutineScope(Dispatchers.IO).launch {
                delay(500)
                cameraManager.setTorchMode(it, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}






