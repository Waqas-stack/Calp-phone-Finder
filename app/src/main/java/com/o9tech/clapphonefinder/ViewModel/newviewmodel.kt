//package com.o9tech.clapphonefinder.ViewModel
//
//import android.media.AudioRecord
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.musicg.api.ClapApi
//import com.musicg.api.WhistleApi
//import com.musicg.wave.WaveHeader
//import com.o9tech.clapphonefinder.Services.RecorderThread
//import dagger.hilt.android.lifecycle.HiltViewModel
//import java.util.LinkedList
//import javax.inject.Inject
//
//
//@HiltViewModel
//class DetectorViewModel  @Inject constructor() : ViewModel() {
//
//    var numWhistles by mutableStateOf(0)
//    var numClaps by mutableStateOf(0)
//    var clapDetected by mutableStateOf(false)
//    var whistleDetected by mutableStateOf(false)
//    private lateinit var recorder: RecorderThread
//    private lateinit var whistleApi: WhistleApi
//    private lateinit var clapApi: ClapApi
//    private val whistleResultList = LinkedList<Boolean>()
//    private val clapResultList = LinkedList<Boolean>()
//
//    private val whistleCheckLength = 3
//    private val clapCheckLength = 1
//    private val whistlePassScore = 3
//    private val clapPassScore = 1
//
//    fun startDetection(clapValue: String) {
//        recorder = RecorderThread()
//        val audioRecord = recorder.getAudioRecord()
//        val waveHeader = WaveHeader().apply {
//            setChannels(1) // Mono channel
//            setBitsPerSample(16)
//            setSampleRate(audioRecord.sampleRate)
//        }
//
//        whistleApi = WhistleApi(waveHeader)
//        clapApi = ClapApi(waveHeader)
//
//        initBuffer()
//
//        Thread {
//            while (true) {
//                val frameBytes = recorder.getFrameBytes()
//                if (frameBytes != null) {
//                    val isWhistle = whistleApi.isWhistle(frameBytes)
//                    val isClap = clapApi.isClap(frameBytes)
//
//                    if (whistleResultList.first()) {
//                        numWhistles--
//                    }
//                    if (clapResultList.first()) {
//                        numClaps--
//                    }
//                    whistleResultList.removeFirst()
//                    clapResultList.removeFirst()
//
//                    whistleResultList.add(isWhistle)
//                    clapResultList.add(isClap)
//
//                    if (isWhistle) numWhistles++
//                    if (isClap) numClaps++
//
//                    if (numWhistles >= whistlePassScore) {
//                        whistleDetected = true
//                        if (clapValue == "ON") onWhistleDetected()
//                        initBuffer()
//                    }
//
//                    if (numClaps >= clapPassScore) {
//                        Log.e("Sound", "Detected");
//                        clapDetected = true
//                        if (clapValue == "YES") onClapDetected()
//                        initBuffer()
//                    }
//                } else {
//                    resetBuffers()
//                }
//            }
//        }.start()
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
//    private fun resetBuffers() {
//        if (whistleResultList.first()) numWhistles--
//        whistleResultList.removeFirst()
//        whistleResultList.add(false)
//
//        if (clapResultList.first()) numClaps--
//        clapResultList.removeFirst()
//        clapResultList.add(false)
//    }
//
//    private fun onClapDetected() {
//        // Handle clap detected
//    }
//
//    private fun onWhistleDetected() {
//        // Handle whistle detected
//    }
//}