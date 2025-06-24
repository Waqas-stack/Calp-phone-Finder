package com.o9tech.clapphonefinder.Screen.ClapDectionScreen
//
//import android.Manifest
//import android.R
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.content.Intent
//import android.media.AudioFormat
//import android.media.AudioRecord
//import android.media.MediaPlayer
//import android.os.Build
//import android.os.IBinder
//import android.util.Log
//import androidx.annotation.RequiresPermission
//import androidx.core.app.NotificationCompat
//import kotlin.math.sqrt
//
//class ClapDetectionService : android.app.Service() {
//
//    private var audioRecord: AudioRecord? = null
//    private val bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
//    private val threshold: Double = 1000.0 // Threshold for clap detection
//
//    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
//    override fun onCreate() {
//        super.onCreate()
//        createNotificationChannel()
//
//        // Create the notification to run the service in the foreground
//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Clap Detection Service")
//            .setContentText("Listening for claps...")
//            .setSmallIcon(R.drawable.ic_media_play)
//            .build()
//
//        startForeground(1, notification)
//
//        // Start the clap detection process
//        startClapDetection()
//    }
//
//    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
//    private fun startClapDetection() {
//        audioRecord = AudioRecord(
//            AudioFormat.MODE_STREAM,
//            44100, AudioFormat.CHANNEL_IN_MONO,
//            AudioFormat.ENCODING_PCM_16BIT,
//            bufferSize
//        )
//
//        // Start recording audio
//        Thread {
//            audioRecord?.startRecording()
//            val audioData = ShortArray(bufferSize)
//
//            while (true) {
//                val read = audioRecord?.read(audioData, 0, bufferSize) ?: 0
//                if (read > 0) {
//                    val rms = calculateRMS(audioData, read)
//                    if (rms > threshold) {
//                        playRingtone()
//                    }
//                }
//            }
//        }.start()
//    }
//
//    private fun calculateRMS(audioData: ShortArray, length: Int): Double {
//        var sum = 0.0
//        for (i in 0 until length) {
//            sum += audioData[i] * audioData[i]
//        }
//        return sqrt(sum / length.toDouble())
//    }
//
//    private fun playRingtone() {
//        val mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_RINGTONE_URI)
//        mediaPlayer.start()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Clap Detection"
//            val descriptionText = "Channel for Clap Detection Service"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    companion object {
//        const val CHANNEL_ID = "ClapDetectionServiceChannel"
//    }
//}