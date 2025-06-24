package com.o9tech.clapphonefinder.Services

import android.media.AudioRecord


//class ClapSoundDetector(
//    private val context: Context,
//    private val onClapDetected: () -> Unit
//) {
//    private var isRunning = false
//    private var thread: Thread? = null
//
//    fun startDetection() {
//        isRunning = true
//        thread = Thread @androidx.annotation.RequiresPermission(android.Manifest.permission.RECORD_AUDIO) {
//            val bufferSize = AudioRecord.getMinBufferSize(
//                44100,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT
//            )
//
//            val recorder = AudioRecord(
//                MediaRecorder.AudioSource.MIC,
//                44100,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize
//            )
//
//            val buffer = ShortArray(bufferSize)
//            recorder.startRecording()
//
//            while (isRunning) {
//                val read = recorder.read(buffer, 0, buffer.size)
//                if (read > 0) {
//                    val maxAmplitude = buffer.maxOrNull()?.toInt() ?: 0
//                    if (maxAmplitude > 15000) { // threshold (tune this)
//                        onClapDetected()
//                        Thread.sleep(1000) // prevent multiple detections
//                    }
//                }
//            }
//
//            recorder.stop()
//            recorder.release()
//        }
//        thread?.start()
//    }
//
//    fun stopDetection() {
//        isRunning = false
//        thread?.interrupt()
//        thread = null
//    }
//}


///use api from musicg library

//import android.media.MediaRecorder
//import android.util.Log
//import androidx.core.app.ActivityCompat
//import com.musicg.api.ClapApi

//class ClapSoundDetector(
//    private val context: Context,
//    private val onClapDetected: () -> Unit
//) {
//    private var isRunning = false
//    private var thread: Thread? = null
//    private lateinit var clapApi: ClapApi
//
//    fun startDetection() {
//        // Runtime permission check
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.RECORD_AUDIO
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.e("ClapSoundDetector", "Mic permission not granted")
//            return
//        }
//
//        isRunning = true
//        thread = Thread {
//            val bufferSize = AudioRecord.getMinBufferSize(
//                44100,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT
//            )
//
//            val recorder = AudioRecord(
//                MediaRecorder.AudioSource.MIC,
//                44100,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize
//            )
//
//            val buffer = ShortArray(bufferSize)
//            recorder.startRecording()
//
//            while (isRunning) {
//                val read = recorder.read(buffer, 0, buffer.size)
//                if (read > 0) {
//                    val maxAmplitude = buffer.maxOrNull()?.toInt() ?: 0
//                    if (maxAmplitude > 15000) { // Tune this threshold
//                        onClapDetected()
//                        Thread.sleep(1000) // delay to avoid multiple triggers
//                    }
//                }
//            }
//
//            recorder.stop()
//            recorder.release()
//        }
//        thread?.start()
//    }
//
//    fun stopDetection() {
//        isRunning = false
//        thread?.interrupt()
//        thread = null
//    }
//}


import com.musicg.api.ClapApi
//
//class ClapSoundDetector(
//    private val context: Context,
//    private val onClapDetected: () -> Unit
//) {
//    private lateinit var clapApi: ClapApi
//    private var isRunning = false
//    private var thread: Thread? = null
//
//    fun startDetection() {
//        isRunning = true
//
//        // Initialize ClapApi
////        clapApi = ClapApi()
//
//        thread = Thread {
//            val sampleRate = 44100
//            val bufferSize = AudioRecord.getMinBufferSize(
//                sampleRate,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT
//            )
//
//            val recorder = AudioRecord(
//                MediaRecorder.AudioSource.MIC,
//                sampleRate,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize
//            )
//
//            val buffer = ByteArray(bufferSize)
//            recorder.startRecording()
//
//            while (isRunning) {
//                val read = recorder.read(buffer, 0, buffer.size)
//                if (read > 0) {
//                    if (clapApi.isClap(buffer)) {
//                        Handler(Looper.getMainLooper()).post {
//                            onClapDetected()
//                        }
//                        Thread.sleep(1000) // avoid multiple detections
//                    }
//                }
//            }
//
//            recorder.stop()
//            recorder.release()
//        }
//
//        thread?.start()
//    }
//
//    fun stopDetection() {
//        isRunning = false
//        thread?.interrupt()
//        thread = null
//    }
//}






//class ClapSoundDetector(
//    private val context: Context,
//    private val onClapDetected: () -> Unit
//) {
//    private var isRunning = false
//    private var thread: Thread? = null
//
//    fun startDetection() {
//        isRunning = true
//
//        thread = Thread {
//            val sampleRate = 44100
//            val bufferSize = AudioRecord.getMinBufferSize(
//                sampleRate,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT
//            )
//
//
//
//            // 🛠 Initialize ClapApi inside the thread (before use)
//            val clapApi = ClapApi()
////             lateinit var clapApi: ClapApi
//
//
//            val recorder = AudioRecord(
//                MediaRecorder.AudioSource.MIC,
//                sampleRate,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize
//            )
//
//            val buffer = ByteArray(bufferSize)
//            recorder.startRecording()
//
//            while (isRunning) {
//                val read = recorder.read(buffer, 0, buffer.size)
//                if (read > 0) {
//                    if (clapApi.isClap(buffer)) {
//                        Handler(Looper.getMainLooper()).post {
//                            onClapDetected()
//                        }
//                        Thread.sleep(1000) // to prevent repeated triggers
//                    }
//                }
//            }
//
//            recorder.stop()
//            recorder.release()
//        }
//
//        thread?.start()
//    }
//
//    fun stopDetection() {
//        isRunning = false
//        thread?.interrupt()
//        thread = null
//    }
//}
//



import android.util.Log

import com.musicg.wave.WaveHeader
import java.util.LinkedList

//
//class DetectorThread(
//    private val recorderThread: RecorderThread,
//    private val clapValue: String // e.g. "YES" to enable clap detection
//) : Thread() {
//
//    @Volatile
//    private var runningThread: Thread? = null
//
//    private var numClaps = 0
//    private val clapCheckLength = 1
//    private val clapPassScore = 1
//    private val clapResultList = LinkedList<Boolean>()
//
//    private val waveHeader: WaveHeader
//    private val clapApi: ClapApi
//
//    init {
//        val audioRecord: AudioRecord = recorderThread.getAudioRecord()
//
//
//
//        val bitsPerSample = when (audioRecord.audioFormat) {
//            2 -> 16
//            3 -> 8
//            else -> 0
//        }
//
//        val channels = if (audioRecord.channelConfiguration == 16) 1 else 0
//
//        waveHeader = WaveHeader().apply {
//            setChannels(channels)
//            setBitsPerSample(bitsPerSample)
//            setSampleRate(audioRecord.sampleRate)
//        }
//
//        clapApi = ClapApi(waveHeader)
//    }
//
//    private fun initBuffer() {
//        numClaps = 0
//        clapResultList.clear()
//        repeat(clapCheckLength) {
//            clapResultList.add(false)
//        }
//    }
//
//    override fun start() {
//        runningThread = Thread(this)
//        runningThread?.start()
//    }
//
//    fun stopDetection() {
//        runningThread = null
//    }
//
//    override fun run() {
//        try {
//            initBuffer()
//            val currentThread = Thread.currentThread()
//            while (runningThread == currentThread) {
//                val frameBytes = recorderThread.getFrameBytes() ?: continue
//
//                val isClap = clapApi.isClap(frameBytes)
//
//                if (clapResultList.firstOrNull() == true) {
//                    numClaps--
//                }
//                if (clapResultList.isNotEmpty()) {
//                    clapResultList.removeFirst()
//                }
//                clapResultList.add(isClap)
//                if (isClap) {
//                    numClaps++
//                }
//
//                if (numClaps >= clapPassScore) {
//                    Log.e("Sound", "Clap Detected")
//                    initBuffer()
//                    if (clapValue == "YES") {
//                        onClapDetected()
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun onClapDetected() {
//        Log.d("DetectorThread", "Clap detected!")
//        // Yahan se aap UI ya kisi aur ko notify kar sakte hain
//    }
//}
//




import com.musicg.api.WhistleApi


//class DetectorThread(
//    private val recorder: RecorderThread,
//    private val clapValue: String // e.g. "YES" or "ON"
//) : Thread() {
//
//    @Volatile
//    private var runningThread: Thread? = null
//
//    private var numWhistles = 0
//    private var numClaps = 0
//
//    private val whistleCheckLength = 3
//    private val clapCheckLength = 1
//
//    private val whistlePassScore = 3
//    private val clapPassScore = 1
//
//    private val whistleResultList = LinkedList<Boolean>()
//    private val clapResultList = LinkedList<Boolean>()
//
//    private val waveHeader: WaveHeader
//    private val whistleApi: WhistleApi
//    private val clapApi: ClapApi
//
//    private var onSignalsDetectedListener: OnSignalsDetectedListener? = null
//
//    init {
//        val audioRecord: AudioRecord = recorder.getAudioRecord()
//
//        val bitsPerSample = when (audioRecord.audioFormat) {
//            2 -> 16
//            3 -> 8
//            else -> 0
//        }
//
//        val channels = if (audioRecord.channelConfiguration == 16) 1 else 0
//
//        waveHeader = WaveHeader().apply {
//            setChannels(channels)
//            setBitsPerSample(bitsPerSample)
//            setSampleRate(audioRecord.sampleRate)
//        }
//
//        whistleApi = WhistleApi(waveHeader)
//        clapApi = ClapApi(waveHeader)
//    }
//
//    private fun initBuffer() {
//        numWhistles = 0
//        numClaps = 0
//        whistleResultList.clear()
//        clapResultList.clear()
//        repeat(whistleCheckLength) {
//            whistleResultList.add(false)
//        }
//        repeat(clapCheckLength) {
//            clapResultList.add(false)
//        }
//    }
//
//    override fun start() {
//        runningThread = Thread(this)
//        runningThread?.start()
//    }
//
//    fun stopDetection() {
//        runningThread = null
//    }
//
//    override fun run() {
//        try {
//            initBuffer()
//            val currentThread = Thread.currentThread()
//            while (runningThread == currentThread) {
//                val frameBytes = recorder.getFrameBytes() ?: continue
//
//                val isWhistle = whistleApi.isWhistle(frameBytes)
//                val isClap = clapApi.isClap(frameBytes)
//
//                if (whistleResultList.firstOrNull() == true) {
//                    numWhistles--
//                }
//                if (clapResultList.firstOrNull() == true) {
//                    numClaps--
//                }
//                if (whistleResultList.isNotEmpty()) {
//                    whistleResultList.removeFirst()
//                }
//                if (clapResultList.isNotEmpty()) {
//                    clapResultList.removeFirst()
//                }
//
//                whistleResultList.add(isWhistle)
//                clapResultList.add(isClap)
//
//                if (isWhistle) numWhistles++
//                if (isClap) numClaps++
//
//                if (numWhistles >= whistlePassScore) {
//                    Log.e("Sound", "Whistle Detected")
//                    initBuffer()
//                    if (clapValue == "ON") {
//                        onWhistleDetected()
//                    }
//                }
//
//                if (numClaps >= clapPassScore) {
//                    Log.e("Sound", "Clap Detected")
//                    initBuffer()
//                    if (clapValue == "YES") {
//                        onClapDetected()
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun onClapDetected() {
//        Log.d("DetectorThread", "Clap detected!")
//        onSignalsDetectedListener?.onClapDetected()
//    }
//
//    private fun onWhistleDetected() {
//        Log.d("DetectorThread", "Whistle detected!")
//        onSignalsDetectedListener?.onWhistleDetected()
//    }
//
//    fun setOnSignalsDetectedListener(listener: OnSignalsDetectedListener) {
//        this.onSignalsDetectedListener = listener
//    }
//}
//
//interface OnSignalsDetectedListener {
//    fun onWhistleDetected()
//    fun onClapDetected()
//}
//





//class DetectorThread(
//    private val recorder: RecorderThread?,
//    private val clapValue: String
//) : Thread() {
//
//    private var _thread: Thread? = null
//    private var numWhistles = 0
//    private var numClaps = 0
//    private val whistleCheckLength = 3
//    private val clapCheckLength = 1
//    private val whistlePassScore = 3
//    private val clapPassScore = 1
//
//    private val whistleResultList = LinkedList<Boolean>()
//    private val clapResultList = LinkedList<Boolean>()
//
//    private var onSignalsDetectedListener: OnSignalsDetectedListener? = null
//
//    private val waveHeader: WaveHeader
//    private val whistleApi: WhistleApi
//    private val clapApi: ClapApi
//
//    init {
//        val audioRecord: AudioRecord = recorder!!.getAudioRecord()
//        val bitsPerSample = when (audioRecord.audioFormat) {
//            2 -> 16
//            3 -> 8
//            else -> 0
//        }
//        val channels = if (audioRecord.channelConfiguration == 16) 1 else 1
//
//        waveHeader = WaveHeader().apply {
//            setChannels(channels)
//            setBitsPerSample(bitsPerSample)
//            setSampleRate(audioRecord.sampleRate)
//        }
//
//        whistleApi = WhistleApi(waveHeader)
//        clapApi = ClapApi(waveHeader)
//    }
//
//    private fun initBuffer() {
//        numWhistles = 0
//        numClaps = 0
//        whistleResultList.clear()
//        clapResultList.clear()
//        repeat(whistleCheckLength) { whistleResultList.add(false) }
//        repeat(clapCheckLength) { clapResultList.add(false) }
//    }
//
//    override fun start() {
//        _thread = Thread(this)
//        _thread?.start()
//    }
//
//    fun stopDetection() {
//        _thread = null
//    }
//
////    override fun run() {
////        try {
////            initBuffer()
////            val currentThread = Thread.currentThread()
////            while (_thread == currentThread) {
////                val frameBytes = recorder.getFrameBytes()
////                if (frameBytes != null) {
//////                    val isWhistle = whistleApi.isWhistle(frameBytes)
//////                    val isClap = clapApi.isClap(frameBytes)
//////                    val amplitude = getFrameBytes()
////                    val frameBytes: ByteArray? = recorder.getFrameBytes()
////                    val isClap = clapApi.isClap(frameBytes) // threshold — tune based on testing
////
//////                    if (whistleResultList.first) numWhistles--
////                    if (clapResultList.first) numClaps--
////
//////                    whistleResultList.removeFirst()
////                    clapResultList.removeFirst()
////
//////                    whistleResultList.add(isWhistle)
////                    clapResultList.add(isClap)
////
//////                    if (isWhistle) numWhistles++
////                    if (isClap) numClaps++
////
//////                    if (numWhistles >= whistlePassScore && clapValue == "ON") {
//////                        Log.e("Sound", "Whistle Detected")
//////                        initBuffer()
//////                        onWhistleDetected()
//////                    }
////
////                    if (numClaps >= clapPassScore && clapValue == "YES") {
////                        Log.e("Sound", "Clap Detected")
////                        initBuffer()
////                        onClapDetected()
////                    }
////                }
////            }
////        } catch (e: Exception) {
////            e.printStackTrace()
////        }
////    }
//
//
//
//    override fun run() {
//        try {
//            initBuffer()
//            val currentThread = Thread.currentThread()
//            while (_thread == currentThread) {
//                val frameBytes = recorder?.getFrameBytes()
//                if (frameBytes != null) {
////                    val isWhistle = whistleApi.isWhistle(frameBytes)
//                    val isClap = clapApi.isClap(frameBytes)
//
//                    // Remove old results
////                    if (whistleResultList.first) numWhistles--
//                    if (clapResultList.first) numClaps--
////                    whistleResultList.removeFirst()
//                    clapResultList.removeFirst()
//
//                    // Add new results
////                    whistleResultList.add(isWhistle)
//                    clapResultList.add(isClap)
////                    if (isWhistle) numWhistles++
//                    if (isClap) numClaps++
//
//
//
//                    // Clap detected
//                    if (numClaps >= clapPassScore && clapValue == "YES") {
//                        Log.e("Sound", "Clap Detected")
//                        initBuffer()
//                        onClapDetected()
//                    }
//                } else {
//                    // Handle null frameBytes (i.e., silence)
//
//                    if (clapResultList.first) numClaps--
//                    clapResultList.removeFirst()
//                    clapResultList.add(false)
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//
////    override fun run() {
////        try {
////            initBuffer()
////            val currentThread = Thread.currentThread()
////            while (_thread == currentThread) {
////                val frameBytes = recorder.getFrameBytes()
////                if (frameBytes != null) {
////                    val isWhistle = whistleApi.isWhistle(frameBytes)
////                    val isClap = clapApi.isClap(frameBytes)
////
////                    if (whistleResultList.first) numWhistles--
////                    if (clapResultList.first) numClaps--
////
////                    whistleResultList.removeFirst()
////                    clapResultList.removeFirst()
////
////                    whistleResultList.add(isWhistle)
////                    clapResultList.add(isClap)
////
////                    if (isWhistle) numWhistles++
////                    if (isClap) numClaps++
////
////                    if (numWhistles >= whistlePassScore && clapValue == "ON") {
////                        Log.e("Sound", "Whistle Detected")
////                        initBuffer()
////                        onWhistleDetected()
////                    }
////
////                    if (numClaps >= clapPassScore && clapValue == "YES") {
////                        Log.e("Sound", "Clap Detected")
////                        initBuffer()
////                        onWhistleDetected()
////                    }
////                }
////            }
////        } catch (e: Exception) {
////            e.printStackTrace()
////        }
////    }
//
//    private fun onWhistleDetected() {
//        onSignalsDetectedListener?.onWhistleDetected()
//    }
//    private fun onClapDetected() {
//        onSignalsDetectedListener?.onClapDetected()
//    }
//
//    fun setOnSignalsDetectedListener(listener: OnSignalsDetectedListener) {
//        onSignalsDetectedListener = listener
//    }
//}
//
//
//
interface OnSignalsDetectedListener {
    fun onWhistleDetected()
    fun onClapDetected()
}
//
//
//fun calculateAmplitude(frameBytes: ByteArray): Int {
//    var sum = 0L
//    val sampleCount = frameBytes.size / 2  // 2 bytes per sample (16-bit PCM)
//    var i = 0
//
//    while (i < frameBytes.size) {
//        // Convert two bytes to one 16-bit sample (little endian)
//        val sample = (frameBytes[i].toInt() and 0xFF) or (frameBytes[i + 1].toInt() shl 8)
//        val signedSample = if (sample > 32767) sample - 65536 else sample  // convert to signed
//        sum += kotlin.math.abs(signedSample.toLong())
//        i += 2
//    }
//
//    return (sum / sampleCount).toInt()
//}
//
//
//
//fun getFrameBytes(): ByteArray? {
//     lateinit var audioRecord: AudioRecord
//     lateinit var buffer: ByteArray
//     val frameByteSize = 2048
//
//
//    audioRecord.read(buffer, 0, frameByteSize)
//    var sum = 0
//    var i = 0
//    while (i < frameByteSize) {
//        val sample = ((buffer[i + 1].toInt() shl 8) or (buffer[i].toInt() and 0xFF)).toShort()
//        sum += kotlin.math.abs(sample.toInt())
//        i += 2
//    }
//
//    val average = (sum / (frameByteSize / 2)).toFloat()
//    return if (average < 30f) null else buffer
//}
//



class DetectorThread(
    private val recorder: RecorderThread?,
    private val clapValue: String
) : Thread() {

    @Volatile
    private var isRunning = true  // <-- This is key

    private var numWhistles = 0
    private var numClaps = 0
    private val whistleCheckLength = 3
    private val clapCheckLength = 1
    private val whistlePassScore = 3
    private val clapPassScore = 1

    private val whistleResultList = LinkedList<Boolean>()
    private val clapResultList = LinkedList<Boolean>()

    private var onSignalsDetectedListener: OnSignalsDetectedListener? = null

    private val waveHeader: WaveHeader
    private val whistleApi: WhistleApi
    private val clapApi: ClapApi

    init {
        val audioRecord: AudioRecord = recorder!!.getAudioRecord()
        val bitsPerSample = when (audioRecord.audioFormat) {
            2 -> 16
            3 -> 8
            else -> 0
        }
        val channels = if (audioRecord.channelConfiguration == 16) 1 else 1

        waveHeader = WaveHeader().apply {
            setChannels(channels)
            setBitsPerSample(bitsPerSample)
            setSampleRate(audioRecord.sampleRate)
        }

        whistleApi = WhistleApi(waveHeader)
        clapApi = ClapApi(waveHeader)
    }

    fun stopDetection() {
        isRunning = false
        interrupt()  // Interrupts sleep/wait/blocking I/O if needed
    }

    override fun run() {
        try {
            initBuffer()
            while (isRunning) {
                val frameBytes = recorder?.getFrameBytes()
                if (frameBytes != null) {
                    val isClap = clapApi.isClap(frameBytes)

                    if (clapResultList.first) numClaps--
                    clapResultList.removeFirst()

                    clapResultList.add(isClap)
                    if (isClap) numClaps++

                    if (numClaps >= clapPassScore && clapValue == "YES") {
                        Log.e("Sound", "Clap Detected")
                        initBuffer()
                        onClapDetected()
                    }
                } else {
                    if (clapResultList.first) numClaps--
                    clapResultList.removeFirst()
                    clapResultList.add(false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initBuffer() {
        numWhistles = 0
        numClaps = 0
        whistleResultList.clear()
        clapResultList.clear()
        repeat(whistleCheckLength) { whistleResultList.add(false) }
        repeat(clapCheckLength) { clapResultList.add(false) }
    }

    private fun onClapDetected() {
        onSignalsDetectedListener?.onClapDetected()
    }

    fun setOnSignalsDetectedListener(listener: OnSignalsDetectedListener) {
        onSignalsDetectedListener = listener
    }
}

