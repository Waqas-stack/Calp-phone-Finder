package com.o9tech.clapphonefinder.Services

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class ClapDetector {

    private val sampleRate = 44100
    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    private var isListening = false

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startListening(onClapDetected: suspend () -> Unit) {
        if (isListening) return

        isListening = true
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        val buffer = ShortArray(bufferSize)
        audioRecord.startRecording()

        CoroutineScope(Dispatchers.Default).launch {
            while (isListening) {
                val readCount = audioRecord.read(buffer, 0, buffer.size)
                if (readCount > 0) {
                    val maxAmplitude = buffer.take(readCount).maxOf { abs(it.toInt()) }

                    // Adjust threshold as needed based on environment
                    if (maxAmplitude > 20000) {
                        withContext(Dispatchers.Main) {
                            onClapDetected()
                        }
                        delay(1000)
                    }
                }
            }

            audioRecord.stop()
            audioRecord.release()
        }
    }

    fun stopListening() {
        isListening = false
    }
}
