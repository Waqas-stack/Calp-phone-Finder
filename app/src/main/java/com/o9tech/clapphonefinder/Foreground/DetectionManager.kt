package com.o9tech.clapphonefinder.Foreground

import android.content.Context
import android.media.MediaPlayer
import com.o9tech.clapphonefinder.Services.DetectorThread
import com.o9tech.clapphonefinder.Services.RecorderThread
import com.o9tech.clapphonefinder.ViewModel.triggerFlash
import com.o9tech.clapphonefinder.ViewModel.triggerVibration

//object DetectionManager {
//    var detectorThread: DetectorThread? = null
//    var recorderThread: RecorderThread? = null
//
//    fun stopDetection() {
//        detectorThread?.interrupt()
//        recorderThread?.interrupt()
//        detectorThread = null
//        recorderThread = null
//    }
//}




object DetectionManassger {
    var detectorThread: DetectorThread? = null
    var recorderThread: RecorderThread? = null

    fun stopDetection() {
        detectorThread?.stopDetection()
        recorderThread?.stopRecording() // if you have this logic

        recorderThread?.interrupt() // if using thread-based RecorderThread
        detectorThread = null
        recorderThread = null
    }
}





object DetectionManager {
    var detectorThread: DetectorThread? = null
    var recorderThread: RecorderThread? = null
    private var mediaPlayer: MediaPlayer? = null

    var flashEnabled = false
    var vibrationEnabled = false



    var selectedSoundUri: String? = null

    fun playSound(context: Context) {
        stopSound() // stop if already playing

        val soundFile = selectedSoundUri ?: "gazette.mp3" // Default fallback


        try {
            val afd = context.assets.openFd(soundFile)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                isLooping = false
                start()
                setOnCompletionListener {
                    it.release()
                    mediaPlayer = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playSoundss(context: Context) {
//        var mediaPlayer = MediaPlayer()

        stopSound()

        val soundFile = selectedSoundUri ?: "beeps.mp3"

        // ✅ Flash
        if (flashEnabled) {
            triggerFlash(context)
        }

        // ✅ Vibration
        if (vibrationEnabled) {
            triggerVibration(context)
        }

        try {
            val afd = context.assets.openFd(soundFile)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                isLooping = false
                start()
                setOnCompletionListener {
                    it.release()
                    mediaPlayer = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopSound() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaPlayer = null
        }
    }

    fun stopDetection() {
        detectorThread?.stopDetection()
        recorderThread?.stopRecording()
        recorderThread?.interrupt()
        detectorThread = null
        recorderThread = null

        stopSound() // 🔇 stop audio when stopping detection
    }
}

