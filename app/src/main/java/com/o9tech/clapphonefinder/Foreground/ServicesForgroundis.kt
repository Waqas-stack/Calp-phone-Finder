package com.o9tech.clapphonefinder.Foreground
//
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import android.app.Service
//import android.os.IBinder
//import com.o9tech.clapphonefinder.R
//
//class ForegroundService : Service() {
//
//    companion object {
//        private const val CHANNEL_ID = "ForegroundServiceChannel"
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        createNotificationChannel()
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        // Start the detection logic in this service, maybe using your MainViewModel
//        // This could be where the clap detection is triggered
//
//        // Creating a simple notification
//        val notification = buildNotification()
//        startForeground(1, notification)
//
//        // Return START_STICKY so that service restarts if killed by the system
//        return START_STICKY
//    }
//
//    private fun buildNotification(): Notification {
//        return NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Clap Detection Service")
//            .setContentText("Detecting claps...")
//            .setSmallIcon(R.drawable.speaker)
//            .build()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Foreground Service Channel"
//            val descriptionText = "Channel for foreground service notifications"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager: NotificationManager =
//                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//}




import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import android.app.Service
import android.content.BroadcastReceiver
import android.os.IBinder
import com.o9tech.clapphonefinder.R

class ForegroundService : Service() {

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Creating a simple notification
        val notification = buildNotification()
        startForeground(1, notification)

        // Return START_STICKY to ensure that the service is restarted if killed by the system
        return START_STICKY
    }

//    private fun buildNotification(): Notification {
//        return NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Clap Detection Service")
//            .setContentText("Detecting claps...")
//            .setSmallIcon(R.drawable.speaker)
//            .build()
//    }


    private fun buildNotification(): Notification {
        val stopIntent = Intent(this, StopServiceReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Clap Detection Service")
            .setContentText("Detecting claps...")
            .setSmallIcon(R.drawable.speaker)
            .addAction(R.drawable.baseline_volume_up_24, "Stop", stopPendingIntent) // Notification Stop Button
            .setOngoing(true) // Prevents swipe-away unless explicitly stopped
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Foreground Service Channel"
            val descriptionText = "Channel for foreground service notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        val restartIntent = Intent(applicationContext, RestartServiceReceiver::class.java)
        sendBroadcast(restartIntent)
    }
    // Fallback to restart the service after being killed by the system
//    override fun onTaskRemoved(rootIntent: Intent?) {
//        super.onTaskRemoved(rootIntent)
//        // Restart the service when it is removed (e.g., killed by the system)
//        val restartServiceIntent = Intent(applicationContext, ForegroundService::class.java)
//        sendBroadcast(restartServiceIntent)
//    }
    override fun onDestroy() {
        super.onDestroy()
        DetectionManager.stopDetection() // 👈 Ensures threads are stopped even if killed differently

    }
}




class RestartServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, ForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}



class StopServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        DetectionManager.stopDetection() // 👈 Stop your threads
        context.stopService(Intent(context, ForegroundService::class.java))
    }
}

