package com.o9tech.clapphonefinder.Foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.o9tech.clapphonefinder.R
import com.o9tech.clapphonefinder.Services.DetectorThread
import com.o9tech.clapphonefinder.Services.OnSignalsDetectedListener
import com.o9tech.clapphonefinder.Services.RecorderThread

class ClapDetectionService : Service() {

    private lateinit var recorderThread: RecorderThread
    private var detectorThread: DetectorThread? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundNotification()
        startDetection()
    }

    private fun startForegroundNotification() {
        val channelId = "CLAP_CHANNEL"
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Clap Detection Running")
            .setContentText("Listening for claps...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Clap Detection",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        startForeground(1, notification)
    }

    private fun startDetection() {
        recorderThread = RecorderThread()
        recorderThread.start()

        detectorThread = DetectorThread(recorderThread, "YES").apply {
            setOnSignalsDetectedListener(object : OnSignalsDetectedListener {
                override fun onWhistleDetected() {}
                override fun onClapDetected() {
                    Log.d("ClapDetectionService", "Clap Detected!")

//                    playSound(context)
                    // Optionally: Broadcast intent or show toast or play sound
                }
            })
            start()
        }
    }

    private fun stopDetection() {
        detectorThread?.stopDetection()
        recorderThread.stopRecording()
    }

    override fun onDestroy() {
        stopDetection()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

//    override fun onBind(intent: Intent?): IBinder? = null
}
