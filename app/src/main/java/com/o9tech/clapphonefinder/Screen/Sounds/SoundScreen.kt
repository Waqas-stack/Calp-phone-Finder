package com.o9tech.clapphonefinder.Screen.Sounds

import android.content.Context
import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.o9tech.clapphonefinder.Foreground.DetectionManager
import com.o9tech.clapphonefinder.R
import com.o9tech.clapphonefinder.model.SoundData
import com.o9tech.clapphonefinder.ui.theme.green
import com.o9tech.clapphonefinder.ui.theme.mode_setting
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundScreen(navController: NavHostController) {
//    var volume by remember { mutableStateOf(0.5f) }
//    var selectedDuration by remember { mutableStateOf(null) }

    var selectedDuration by remember { mutableStateOf<String?>(null) }

    val durations = listOf("5s", "15s", "30s", "1m")
    val durationMap = mapOf(
        "5s" to 5000L,
        "15s" to 15000L,
        "30s" to 30000L,
        "1m" to 60000L
    )
//    val soundList = remember {
//        List(21) { index -> "Sound ${index + 1}" }
//    }

//    var selectedSound by remember { mutableStateOf<String?>(null) }
//    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


//    var selectedSound by remember { mutableStateOf<SoundData?>(null) }
//    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    var selectedSound by remember { mutableStateOf<SoundData?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(1.0f) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    BackHandler {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        navController.popBackStack() // or whatever navigation you're using to go back
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }







    val soundlist = mutableListOf(
        SoundData("Steam Air", R.drawable.airhorn, "air-horn-273892.mp3"),
        SoundData("Baby Laugh", R.drawable.laugh, "baby-giggle-85158.mp3"),
        SoundData("Boom", R.drawable.bomb, "grenade-37637.mp3"),
        SoundData("Dog", R.drawable.dog, "dogg.mp3"),
        SoundData("Cat", R.drawable.laughing, "cute-cat-352656.mp3"),
        SoundData("Church", R.drawable.chapel, "churchbell.mp3"),
        SoundData("Door Bell", R.drawable.doorbell, "doorbell.mp3"),
        SoundData("Horn", R.drawable.whistle, "whats_up_people.mp3"),
        SoundData("Drum", R.drawable.drum, "drums.mp3"),
        SoundData("Gun", R.drawable.rifle, "gunsh.mp3"),
        SoundData("Hello", R.drawable.message, "hellos.mp3"),
        SoundData("I'm here", R.drawable.navigation, "here.mp3"),
        SoundData("message", R.drawable.message1, "messahe.mp3"),
        SoundData("morse", R.drawable.morsecode, "whats_up_people.mp3"),
        SoundData("piano", R.drawable.piano, "pianochords.mp3"),
        SoundData("police", R.drawable.policecar, "policesiren.mp3"),
        SoundData("Rhythm", R.drawable.music, "gtrriff.mp3"),
        SoundData("Ship Bell", R.drawable.ship, "shipbell.mp3"),
        SoundData("Beep", R.drawable.digitalclock, "beeps.mp3"),
        SoundData("Warning", R.drawable.alert, "warningalarm.mp3"),
        SoundData("Wind Chimes", R.drawable.chimes, "windchimes.mp3"),
    )


    DisposableEffect(Unit) {
        onDispose {
            // Stop and release MediaPlayer when leaving the screen
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
                title = {
                    Text(
                        text = stringResource(id = R.string.sound),
//                        text = "Sound",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF4CAF50), // Green border
                                shape = RoundedCornerShape(20) // Fully rounded corners
                            )
                            .clip(RoundedCornerShape(20)) // Clip the shape so the ripple matches
                    ) {
                        Text(
                            text = stringResource(id = R.string.apply),
//                            text = "Apply",
                            fontSize = 16.sp,
                            color = Color(0xFF4CAF50) // Optional: Match text color to border
                        )
                    }


                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.keyboard_backspace),
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White,)
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = Color.White
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                ){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_volume_up_24),
                                    contentDescription = "Volume Icon",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.CenterVertically),
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Volume",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomVoluameSlider(
                                modifier = Modifier.fillMaxWidth(),
                                volume = volume,
                                onVolumeChanged = { newVolume ->
                                    volume = newVolume
                                    mediaPlayer?.setVolume(volume, volume) // Apply live volume update
                                }
                            )

//                            CustomVoluameSlider(
//                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//                                volume = volume,
//                                onVolumeChanged = { newVolume ->
//                                    volume = newVolume
//                                    mediaPlayer?.setVolume(volume, volume) // Apply live volume update
//                                }
//                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                                    contentDescription = "Volume Icon",
                                    modifier = Modifier
                                        .size(24.dp),
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.duration),
//                                    text = "Duration",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f)) // Pushes the duration text to the right
                                Text(
                                    text = selectedDuration ?: "Default", // 👈 fallback value
                                    fontSize = 16.sp,
                                    color = Color.LightGray,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                durations.forEach { duration1 ->
                                    Button(
                                        onClick = {
                                            selectedDuration = duration1

//                                            mediaPlayer?.stop()
//                                            mediaPlayer?.release()
//                                            mediaPlayer = null
//
//                                            val delayMillis = when (duration1) {
//                                                "5s" -> 5000L
//                                                "15s" -> 15000L
//                                                "30s" -> 30000L
//                                                "1m" -> 60000L
//                                                else -> 0L
//                                            }
//
//                                            selectedSound?.let { sound ->
//                                                coroutineScope.launch {
//                                                    delay(delayMillis)
//                                                    val afd = context.assets.openFd(sound.soundUri)
//                                                    mediaPlayer = MediaPlayer().apply {
//                                                        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                                                        prepare()
//                                                        setVolume(volume, volume)
//                                                        start()
//                                                    }
//                                                    isPlaying = true
//                                                }
//                                            }
                                        },
                                        shape = RoundedCornerShape(15), // Fully rounded pill shape
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (selectedDuration == duration1) Color(0xFF4CAF50) else Color.Transparent,
                                            contentColor = if (selectedDuration == duration1) Color.White else Color.Black
                                        ),
                                        border = if (selectedDuration == duration1) null else BorderStroke(1.dp, Color.Black),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 4.dp)
                                    ) {
                                        Text(text = duration1)
                                    }
                                }
                            }


//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                durations.forEach { duration1 ->
//                                    TextButton (
////                                        onClick = { selectedDuration = duration1 },
//                                        onClick = {
//                                            selectedDuration = duration1
//
//                                            // If a sound is playing, restart it after new delay
//                                            mediaPlayer?.stop()
//                                            mediaPlayer?.release()
//                                            mediaPlayer = null
//
//                                            val delayMillis = when (duration1) {
//                                                "5s" -> 5000L
//                                                "15s" -> 15000L
//                                                "30s" -> 30000L
//                                                "1m" -> 60000L
//                                                else -> 0L
//                                            }
//
//                                            selectedSound?.let { sound ->
//                                                coroutineScope.launch {
//                                                    delay(delayMillis)
//                                                    val afd = context.assets.openFd(sound.soundUri)
//                                                    mediaPlayer = MediaPlayer().apply {
//                                                        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                                                        prepare()
//                                                        setVolume(volume, volume)
//                                                        start()
//                                                    }
//                                                    isPlaying = true
//                                                }
//                                            }
//                                        },
//                                        shape = RoundedCornerShape(8.dp),
//                                        colors = ButtonDefaults.buttonColors(
//                                            containerColor = if (selectedDuration == duration1) Color(0xFF4CAF50) else Color.LightGray,
//                                            contentColor = if (selectedDuration == duration1) Color.White else Color.Black
//                                        ),
//                                        modifier = Modifier
//                                            .weight(1f)
//                                            .padding(horizontal = 4.dp)
//                                    ) {
//                                        Text(text = duration1)
//                                    }
//                                }
//                            }
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.tap_to_play_sound),
//                        text = "Tap to play sound",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )

//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(3),
//                        contentPadding = PaddingValues(4.dp),
//                        verticalArrangement = Arrangement.spacedBy(12.dp),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp),
////                        modifier = Modifier.fillMaxSize(),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(max = 1000.dp),
//                        userScrollEnabled = false // disables inner scroll
//
//                    ) {
//                        items(soundlist.size) { index ->
//                            val sound = soundlist[index]
//                            val isSelected = selectedSound == sound
//
//
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .aspectRatio(1f)
//                                    .clickable {
//                                        selectedSound = sound
//                                        isPlaying = true
//                                    },
//                                shape = RoundedCornerShape(12.dp),
//                                colors = CardDefaults.cardColors(containerColor = mode_setting),
//                                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
//                            ) {
//                                Box(modifier = Modifier.fillMaxSize()) {
//
//                                    // Main content layout
//                                    Column(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(8.dp),
//                                        verticalArrangement = Arrangement.SpaceBetween,
//                                        horizontalAlignment = Alignment.CenterHorizontally
//                                    ) {
//                                        Spacer(modifier = Modifier.height(12.dp)) // Space from top for the icon
//
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.request),
//                                            contentDescription = "App Icon",
//                                            modifier = Modifier.size(34.dp),
//                                            tint = green
//                                        )
//
//                                        Text(
//                                            text = sound,
//                                            fontSize = 14.sp,
//                                            color = Color.Black,
//                                            fontWeight = FontWeight.Medium
//                                        )
//                                    }
//
//                                    // Play/Pause button in TopEnd (absolute aligned in Box)
//                                    if (isSelected) {
//
//                                        IconButton(
//                                            onClick = {
//                                                selectedSound = sound
//                                                isPlaying = !isPlaying
//                                            },
//                                            modifier = Modifier
//                                                .align(Alignment.TopEnd)
//                                                .padding(6.dp)
//                                        ) {
//                                            Icon(
//                                                imageVector = if (isPlaying) Icons.Default.Email else Icons.Default.PlayArrow,
//                                                contentDescription = if (isPlaying) "Pause" else "Play",
//                                                tint = Color(0xFF4CAF50),
//                                                modifier = Modifier.size(24.dp)
//                                            )
//                                        }
//
//                                    }
//                                }
//                            }
//
//
////                            Card(
////                                modifier = Modifier
////                                    .fillMaxWidth()
////                                    .aspectRatio(1f)
////                                    .clickable {
////                                        selectedSound = sound
////                                        isPlaying = true
////                                    },
////                                shape = RoundedCornerShape(12.dp),
////                                colors = CardDefaults.cardColors(containerColor = mode_setting),
////                                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
////                            ) {
////                                Box(modifier = Modifier.fillMaxSize()) {
////
////                                    // Centered Icon (Image/Icon of Sound)
////                                    Icon(
////                                        painter = painterResource(id = R.drawable.request),
////                                        contentDescription = "App Icon",
////                                        modifier = Modifier
////                                            .size(34.dp)
////                                            .align(Alignment.Center),
////                                        tint = green
////                                    )
////
////                                    // Top-End Play/Pause IconButton
////                                    if (isSelected) {
////                                        IconButton(
////                                            onClick = {
////                                                selectedSound = sound
////                                                isPlaying = !isPlaying
////                                            },
////                                            modifier = Modifier
////                                                .align(Alignment.TopEnd)
////                                                .padding(6.dp)
////                                        ) {
////                                            Icon(
////                                                imageVector = if (isPlaying) Icons.Default.MailOutline else Icons.Default.PlayArrow,
////                                                contentDescription = if (isPlaying) "Pause" else "Play",
////                                                tint = Color(0xFF4CAF50),
////                                                modifier = Modifier.size(24.dp)
////                                            )
////                                        }
////                                    }
////
////                                    // Bottom-Center Sound Name
////                                    Text(
////                                        text = sound,
////                                        fontSize = 14.sp,
////                                        color = Color.Black,
////                                        fontWeight = FontWeight.Medium,
////                                        modifier = Modifier
////                                            .align(Alignment.BottomCenter)
////                                            .padding(8.dp)
////                                    )
////                                }
////                            }
//
//
////                            Card(
////                                modifier = Modifier
////                                    .fillMaxWidth()
////                                    .aspectRatio(1f)
////                                    .clickable {
////                                        selectedSound = sound
////                                        isPlaying = true
////                                    },
////                                shape = RoundedCornerShape(12.dp),
////                                colors = CardDefaults.cardColors(containerColor = mode_setting),
////                                border = if (isSelected) {
////                                    BorderStroke(2.dp, Color(0xFF4CAF50))
////                                } else {
////                                    null // No border by default
////                                },
////                            ) {
////                                Box(modifier = Modifier.fillMaxSize()) {
////                                    // Top-left play/pause icon
////
////
////
////                                    // Sound image (placeholder)
//////                                    Image(
//////                                        painter = painterResource(id = R.drawable.vip), // replace with your sound image
//////                                        contentDescription = sound,
//////                                        contentScale = ContentScale.Crop,
//////                                        modifier = Modifier
//////                                            .fillMaxWidth().height(30.dp)
//////                                            .padding(16.dp)
//////                                            .clip(RoundedCornerShape(8.dp))
//////                                    )
////                                    Box (
////                                        modifier = Modifier.fillMaxSize(),
////                                        contentAlignment = Alignment.Center
////                                    ){
////                                        Icon(
////                                            painter = painterResource(id = R.drawable.request),
////                                            contentDescription = "App Icon",
////                                            modifier = Modifier
////                                                .size(34.dp),
////                                            tint = green
////
////                                        )
////                                    }
////
////                                    if (isSelected) {
////                                        IconButton(
////                                            onClick = {
////                                                selectedSound = sound
////                                                isPlaying = !isPlaying
////                                            },
////                                            modifier = Modifier
////                                                .align(Alignment.TopEnd)
////                                                .padding(6.dp)
////                                        ) {
////                                            Icon(
////                                                imageVector = if (isPlaying) Icons.Default.MailOutline else Icons.Default.PlayArrow,
////                                                contentDescription = if (isPlaying) "Pause" else "Play",
////                                                tint = Color(0xFF4CAF50),
////                                                modifier = Modifier.size(24.dp)
////                                            )
////                                        }
////                                    }
////
////                                    // Sound name
////                                    Text(
////                                        text = sound,
////                                        fontSize = 14.sp,
////                                        color = Color.Black,
////                                        fontWeight = FontWeight.Medium,
////                                        modifier = Modifier
////                                            .align(Alignment.BottomCenter)
////                                            .padding(8.dp)
////                                    )
////                                }
////                            }
//                        }
//                    }

//                    SoundGridScreen(context)
//                    SoundGridScresen(context)


                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth().heightIn(max = 1000.dp),
                    ) {
                        items(soundlist.size) { index ->
                            val sound = soundlist[index]
                            val isSelected = selectedSound == sound

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f).clickable {
                                        selectedSound = sound
                                        isPlaying = true

                                        DetectionManager.selectedSoundUri = sound.soundUri // save for detection use

                                        // Stop any current playing sound
                                        mediaPlayer?.stop()
                                        mediaPlayer?.release()

                                        val afd = context.assets.openFd(sound.soundUri)

                                        if (DetectionManager.detectorThread?.isAlive == true) {
                                            // If detection is ON → use DetectionManager to play (and override)
                                            DetectionManager.playSound(context)
                                        } else {
                                            // If detection is OFF → play for preview only
                                            mediaPlayer = MediaPlayer().apply {
                                                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                                                prepare()
                                                setVolume(volume, volume)
                                                start()
                                                setOnCompletionListener {
                                                    it.release()
                                                    isPlaying = false
                                                    mediaPlayer = null
                                                }
                                            }
                                        }
                                    }

//                                    .clickable {
//                                        selectedSound = sound
//                                        isPlaying = true
//
//                                        // 👉 Set selected music for DetectionManager
//                                        DetectionManager.selectedSoundUri = sound.soundUri
//
//                                        // Stop previously playing sound
//                                        mediaPlayer?.stop()
//                                        mediaPlayer?.release()
//
//
//                                        // Convert selectedDuration to milliseconds using your map
//                                        val delayMillis = selectedDuration?.let { durationMap[it] } ?: 0L
//
//                                        // Delay and play
//                                        coroutineScope.launch {
//                                            delay(delayMillis) // ✅ delay here
//
//                                            val afd = context.assets.openFd(sound.soundUri)
//                                            mediaPlayer = MediaPlayer().apply {
//                                                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                                                prepare()
//                                                setVolume(volume, volume)
//                                                start()
//                                            }
//                                        }
//
//                                        // Play the newly selected sound immediately (for preview)
////                                        val afd = context.assets.openFd(sound.soundUri)
////                                        mediaPlayer = MediaPlayer().apply {
////                                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
////                                            prepare()
////                                            setVolume(volume, volume)
////                                            start()
////                                        }
//                                    }
,
//                                    .clickable {
//                                        selectedSound = sound
//                                        isPlaying = true
//                                        mediaPlayer?.stop()
//                                        mediaPlayer?.release()
//
//                                        val afd = context.assets.openFd(sound.soundUri)
//                                        mediaPlayer = MediaPlayer().apply {
//                                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                                            prepare()
//                                            setVolume(volume, volume) // Set initial volume
//                                            start()
//                                        }
//                                    },
//                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = mode_setting),
                                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(12.dp))

                                        Icon(
                                            painter = painterResource(id = sound.soundIcon),
                                            contentDescription = sound.soundName,
                                            modifier = Modifier.size(43.dp),
                                            tint = Color.Unspecified
                                        )

                                        Text(
                                            text = sound.soundName,
                                            fontSize = 14.sp,
                                            color = if (isSelected) Color.Black else Color.Gray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    if (isSelected) {
                                        IconButton(
                                            onClick = {
                                                if (isPlaying) {
                                                    mediaPlayer?.pause()
                                                } else {
                                                    mediaPlayer?.start()
                                                }
                                                isPlaying = !isPlaying
                                            },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(6.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(
                                                    id = if (isPlaying) R.drawable.pause_circle_24 else R.drawable.play_arrow_24
                                                ),
//                                                painter = painterResource(id = R.drawable.keyboard_backspace),

//                                                imageVector = if (isPlaying) Icons.Default.MailOutline else Icons.Default.PlayArrow,
                                                contentDescription = if (isPlaying) "Pause" else "Play",
                                                tint = Color(0xFF4CAF50),
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }


//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(3),
//                        contentPadding = PaddingValues(4.dp),
//                        verticalArrangement = Arrangement.spacedBy(12.dp),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp),
//                        modifier = Modifier.fillMaxWidth().heightIn(max = 1000.dp),
//                    ) {
//                        items(soundlist.size) { index ->
//                            val sound = soundlist[index]
//                            val isSelected = selectedSound == sound
//
//
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .aspectRatio(1f)
//                                    .clickable {
////                                        // Toggle isSelected in the model
////                                        soundlist[index] = sound.copy(
////                                            isSelected = !sound.isSelected
////                                        )
//                                        selectedSound = sound
//
//                                        mediaPlayer?.stop()
//                                        mediaPlayer?.release()
//
//                                        val afd = context.assets.openFd(sound.soundUri)
//                                        mediaPlayer = MediaPlayer().apply {
//                                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                                            prepare()
//                                            start()
//                                        }
//
//                                    },
//                                shape = RoundedCornerShape(12.dp),
//                                colors = CardDefaults.cardColors(containerColor = mode_setting),
//                                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
//                            ) {
//                                Box(modifier = Modifier.fillMaxSize()) {
//                                    Column(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(8.dp),
//                                        verticalArrangement = Arrangement.SpaceBetween,
//                                        horizontalAlignment = Alignment.CenterHorizontally
//                                    ) {
//                                        Spacer(modifier = Modifier.height(12.dp))
//
//                                        Icon(
//                                            painter = painterResource(id = sound.soundIcon),
//                                            contentDescription = sound.soundName,
//                                            modifier = Modifier.size(34.dp),
//                                            tint = green
//                                        )
//
//                                        Text(
//                                            text = sound.soundName,
//                                            fontSize = 14.sp,
//                                            color = Color.Black,
//                                            fontWeight = FontWeight.Medium
//                                        )
//                                    }
//
//                                    if (isSelected) {
//                                        IconButton(
//                                            onClick = {
//                                                // Toggle play/pause
//                                                isPlaying = !isPlaying
//                                            },
//                                            modifier = Modifier
//                                                .align(Alignment.TopEnd)
//                                                .padding(6.dp)
//                                        ) {
//                                            Icon(
//                                                imageVector = if (isPlaying) Icons.Default.Email else Icons.Default.PlayArrow,
//                                                contentDescription = if (isPlaying) "Pause" else "Play",
//                                                tint = Color(0xFF4CAF50),
//                                                modifier = Modifier.size(24.dp)
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SoundScreenPreview() {
    val navController = rememberNavController()
    SoundScreen(navController)
}






@Composable
fun CustomVoluameSlider(
    modifier: Modifier = Modifier,
    volume: Float,
    onVolumeChanged: (Float) -> Unit
) {
    val thumbRadius = 8.dp
    val trackHeight = 6.dp

    var sliderWidth by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .height(thumbRadius * 2)
            .onGloballyPositioned { coordinates ->
                sliderWidth = coordinates.size.width.toFloat()
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val newVolume = (change.position.x / sliderWidth).coerceIn(0f, 1f)
                    onVolumeChanged(newVolume)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val trackPx = trackHeight.toPx()
            val thumbPx = thumbRadius.toPx()
            val centerY = size.height / 2
            val thumbX = volume * size.width

            // Inactive Track
            drawRoundRect(
                color = Color.LightGray,
                topLeft = Offset(0f, centerY - trackPx / 2),
                size = Size(size.width, trackPx),
                cornerRadius = CornerRadius(trackPx / 2, trackPx / 2)
            )

            // Active Track
            drawRoundRect(
                color = Color(0xFF4CAF50),
                topLeft = Offset(0f, centerY - trackPx / 2),
                size = Size(thumbX, trackPx),
                cornerRadius = CornerRadius(trackPx / 2, trackPx / 2)
            )

            // Thumb
            drawCircle(
                color = Color(0xFF4CAF50),
                radius = thumbPx,
                center = Offset(thumbX, centerY)
            )
        }
    }
}










@Composable
fun SoundGridScresen(context: Context) {
    var selectedSound by remember { mutableStateOf<SoundData?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(1.0f) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }


    val soundList = listOf(
        SoundData("Iu", R.drawable.speaker, "before_i_decay.mp3"),
        SoundData("RM", R.drawable.speaker, "chizuru.mp3"),
        SoundData("jungkook", R.drawable.speaker, "chuchu_lovely_muni_ext.mp3"),
        SoundData("messi", R.drawable.speaker, "filth_in_the_beauty.mp3"),
        SoundData("jimin", R.drawable.speaker, "gazette.mp3"),
        SoundData("CardiB", R.drawable.speaker, "parasite_anime_op.mp3"),
        SoundData("cha", R.drawable.speaker, "the_invisible_wall.mp3"),
        SoundData("cha", R.drawable.speaker, "whats_up_people.mp3"),
    )

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Column {
        // Sound grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth().heightIn(max = 1000.dp),
        ) {
            items(soundList.size) { index ->
                val sound = soundList[index]
                val isSelected = selectedSound == sound

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            selectedSound = sound
                            isPlaying = true

                            mediaPlayer?.stop()
                            mediaPlayer?.release()

                            val afd = context.assets.openFd(sound.soundUri)
                            mediaPlayer = MediaPlayer().apply {
                                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                                prepare()
                                setVolume(volume, volume) // Set initial volume
                                start()
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                    border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(12.dp))

                            Icon(
                                painter = painterResource(id = sound.soundIcon),
                                contentDescription = sound.soundName,
                                modifier = Modifier.size(34.dp),
                                tint = Color.Green
                            )

                            Text(
                                text = sound.soundName,
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        if (isSelected) {
                            IconButton(
                                onClick = {
                                    if (isPlaying) {
                                        mediaPlayer?.pause()
                                    } else {
                                        mediaPlayer?.start()
                                    }
                                    isPlaying = !isPlaying
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.MailOutline else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlaying) "Pause" else "Play",
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Volume slider
        CustomVoluameSlider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            volume = volume,
            onVolumeChanged = { newVolume ->
                volume = newVolume
                mediaPlayer?.setVolume(volume, volume) // Apply live volume update
            }
        )
    }
}





//@Composable
//fun SoundGridScreen(context: Context) {
//    val soundList = listOf(
//        SoundData("Iu", R.drawable.speaker, "before_i_decay.mp3"),
//        SoundData("RM", R.drawable.speaker, "chizuru.mp3"),
//        SoundData("Jungkook", R.drawable.speaker, "chuchu_lovely_muni_ext.mp3"),
//        // Add more...
//    )
////waqas
//    var selectedSound by remember { mutableStateOf<SoundData?>(null) }
//    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            mediaPlayer?.release()
//        }
//    }
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(400.dp), // Make sure height is bounded
//        contentPadding = PaddingValues(8.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        items(soundList.size) { index ->
//            val sound = soundList[index]
//            val isSelected = selectedSound == sound
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f)
//                    .clickable {
//                        selectedSound = sound
//
//                        mediaPlayer?.stop()
//                        mediaPlayer?.release()
//
//                        val afd = context.assets.openFd(sound.soundUri)
//                        mediaPlayer = MediaPlayer().apply {
//                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                            prepare()
//                            start()
//                        }
//                    },
//                shape = RoundedCornerShape(12.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(8.dp),
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Icon(
//                        painter = painterResource(id = sound.soundIcon),
//                        contentDescription = sound.soundName,
//                        tint = Color(0xFF4CAF50),
//                        modifier = Modifier.size(34.dp)
//                    )
//                    Text(
//                        text = sound.soundName,
//                        fontSize = 14.sp,
//                        color = Color.Black,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//            }
//        }
//    }
//}




//@Composable
//fun SoundGridScreen(context: Context) {
//    val soundList = listOf(
//        SoundData("Iu", R.drawable.speaker, "before_i_decay.mp3"),
//        SoundData("RM", R.drawable.speaker, "chizuru.mp3"),
//        SoundData("jungkook", R.drawable.speaker, "chuchu_lovely_muni_ext.mp3"),
//        SoundData("messi", R.drawable.speaker, "filth_in_the_beauty.mp3"),
//        SoundData("jimin", R.drawable.speaker, "gazette.mp3"),
//        SoundData("CardiB", R.drawable.speaker, "parasite_anime_op.mp3"),
//        SoundData("cha", R.drawable.speaker, "the_invisible_wall.mp3"),
//        SoundData("cha", R.drawable.speaker, "whats_up_people.mp3"),
//    )
//
//    var selectedSound by remember { mutableStateOf<SoundData?>(null) }
//    var isPlaying by remember { mutableStateOf(false) }
//    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            mediaPlayer?.release()
//        }
//    }
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        contentPadding = PaddingValues(4.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .heightIn(max = 1000.dp)
//    ) {
//        items(soundList.size) { index ->
//            val sound = soundList[index]
//            val isSelected = selectedSound == sound
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f)
//                    .clickable {
//                        // Just select sound on card tap
//                        selectedSound = sound
//                    },
//                shape = RoundedCornerShape(12.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
//            ) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(8.dp),
//                        verticalArrangement = Arrangement.SpaceBetween,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        Icon(
//                            painter = painterResource(id = sound.soundIcon),
//                            contentDescription = sound.soundName,
//                            modifier = Modifier.size(34.dp),
//                            tint = green
//                        )
//
//                        Text(
//                            text = sound.soundName,
//                            fontSize = 14.sp,
//                            color = Color.Black,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//
//                    if (isSelected) {
//                        IconButton(
//                            onClick = {
//                                if (isPlaying) {
//                                    mediaPlayer?.pause()
//                                } else {
//                                    if (mediaPlayer == null || !mediaPlayer!!.isPlaying) {
//                                        val afd = context.assets.openFd(sound.soundUri)
//                                        mediaPlayer = MediaPlayer().apply {
//                                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                                            prepare()
//                                            start()
//                                        }
//                                    } else {
//                                        mediaPlayer?.start()
//                                    }
//                                }
//                                isPlaying = !isPlaying
//                            },
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .padding(6.dp)
//                        ) {
//                            Icon(
//                                imageVector = if (isPlaying) Icons.Default.Email else Icons.Default.PlayArrow,
//                                contentDescription = if (isPlaying) "Pause" else "Play",
//                                tint = Color(0xFF4CAF50),
//                                modifier = Modifier.size(24.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}



//@Composable
//fun SoundGridScreen(context: Context) {
//    val soundList = listOf(
//        SoundData("Iu", R.drawable.speaker, "before_i_decay.mp3"),
//        SoundData("RM", R.drawable.speaker, "chizuru.mp3"),
//        SoundData("jungkook", R.drawable.speaker, "chuchu_lovely_muni_ext.mp3"),
//        SoundData("messi", R.drawable.speaker, "filth_in_the_beauty.mp3"),
//        SoundData("jimin", R.drawable.speaker, "gazette.mp3"),
//        SoundData("CardiB", R.drawable.speaker, "parasite_anime_op.mp3"),
//        SoundData("cha", R.drawable.speaker, "the_invisible_wall.mp3"),
//        SoundData("cha", R.drawable.speaker, "whats_up_people.mp3"),
//    )
//
//    var selectedSound by remember { mutableStateOf<SoundData?>(null) }
//    var isPlaying by remember { mutableStateOf(false) }
//    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            mediaPlayer?.release()
//        }
//    }
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        contentPadding = PaddingValues(4.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .heightIn(max = 1000.dp)
//    ) {
//        items(soundList.size) { index ->
//            val sound = soundList[index]
//            val isSelected = selectedSound == sound
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f)
//                    .clickable {
//                        selectedSound = sound
//                        isPlaying = true
//
//                        // Release any existing player
//                        mediaPlayer?.stop()
//                        mediaPlayer?.release()
//
//                        // Play selected sound
//                        val afd = context.assets.openFd(sound.soundUri)
//                        mediaPlayer = MediaPlayer().apply {
//                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                            prepare()
//                            start()
//                        }
//                    },
//                shape = RoundedCornerShape(12.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                border = if (isSelected) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
//            ) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(8.dp),
//                        verticalArrangement = Arrangement.SpaceBetween,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        Icon(
//                            painter = painterResource(id = sound.soundIcon),
//                            contentDescription = sound.soundName,
//                            modifier = Modifier.size(34.dp),
//                            tint = Color(0xFF4CAF50)
//                        )
//
//                        Text(
//                            text = sound.soundName,
//                            fontSize = 14.sp,
//                            color = Color.Black,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//
//                    if (isSelected) {
//                        IconButton(
//                            onClick = {
//                                if (isPlaying) {
//                                    mediaPlayer?.pause()
//                                } else {
//                                    mediaPlayer?.start()
//                                }
//                                isPlaying = !isPlaying
//                            },
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .padding(6.dp)
//                        ) {
//                            Icon(
//                                imageVector = if (isPlaying) Icons.Default.Email else Icons.Default.PlayArrow,
//                                contentDescription = if (isPlaying) "Pause" else "Play",
//                                tint = Color(0xFF4CAF50),
//                                modifier = Modifier.size(24.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//




