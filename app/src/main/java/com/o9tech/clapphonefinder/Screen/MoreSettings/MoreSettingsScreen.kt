package com.o9tech.clapphonefinder.Screen.MoreSettings

import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdSize
import com.o9tech.clapphonefinder.R
import com.o9tech.clapphonefinder.ViewModel.MainViewModel
import com.o9tech.clapphonefinder.adds.BannersAds
import com.o9tech.clapphonefinder.ui.theme.black
import com.o9tech.clapphonefinder.ui.theme.green
import com.o9tech.clapphonefinder.ui.theme.green_bg
import com.o9tech.clapphonefinder.ui.theme.mode_setting
import com.o9tech.clapphonefinder.ui.theme.mode_switch
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreSettingsScreen(navController: NavHostController, mainViewModel: MainViewModel) {

    var selectedOption by remember { mutableStateOf("Average sensitivity") }
    val options = listOf(
        stringResource(id = R.string.sensitivity_very_high),
//        "very High sensitivity",
//        "High sensitivity",
        stringResource(id = R.string.sensitivity_high),

        stringResource(id = R.string.sensitivity_average),

//        "Average sensitivity"
    )
    var showDialog by remember { mutableStateOf(false) }

    var isActive by remember { mutableStateOf(true) }

    val context = LocalContext.current
//    var selectedTime by remember { mutableStateOf("06:00") }
//    var startTime by remember { mutableStateOf("00:00") }

    var startTime by remember { mutableStateOf("00:00") }
    var endTime by remember { mutableStateOf("00:00") }
    var selectedTime by remember { mutableStateOf("00:00") }

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var rating by remember { mutableStateOf(0) }

    var isActives by remember { mutableStateOf(false) }
    var showDialogs by remember { mutableStateOf(false) }
    var tempSwitchState by remember { mutableStateOf(false) }
    val packageName = context.packageName
    val appLink = "https://play.google.com/store/apps/details?id=$packageName"


    //


//    val context = LocalContext.current
    var isSwitchOn by remember { mutableStateOf(false) }
    var startHour by remember { mutableStateOf<Int?>(null) }
    var startMinute by remember { mutableStateOf<Int?>(null) }
    var endHour by remember { mutableStateOf<Int?>(null) }
    var endMinute by remember { mutableStateOf<Int?>(null) }

    val startTimeSelected = startHour != null && startMinute != null
    val endTimeSelected = endHour != null && endMinute != null

    // Auto-run timer once both times are selected and switch is ON
    LaunchedEffect(startTimeSelected, endTimeSelected, isSwitchOn) {
        if (isSwitchOn && startTimeSelected && endTimeSelected) {
            val startCal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, startHour!!)
                set(Calendar.MINUTE, startMinute!!)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val endCal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, endHour!!)
                set(Calendar.MINUTE, endMinute!!)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            // If end is before start, treat it as next day
            if (endCal.before(startCal)) {
                endCal.add(Calendar.DAY_OF_MONTH, 1)
            }

            val durationMillis = endCal.timeInMillis - startCal.timeInMillis

            if (durationMillis > 0) {
                delay(durationMillis) // Wait till time period
                Toast.makeText(context, "⏰ Time Reached!", Toast.LENGTH_LONG).show()
                mainViewModel.stopDetection()

            }
        }
    }



    fun onSensitivityChanged(option: String, context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        when (option) {
            "Very High sensitivity" -> {
                val volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
                Log.d("SoundSensitivity", "Set volume to VERY HIGH")
            }

            "High sensitivity" -> {
                val volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (volume * 0.7).toInt(), 0)
                Log.d("SoundSensitivity", "Set volume to HIGH")
            }

            "Average sensitivity" -> {
                val volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (volume * 0.4).toInt(), 0)
                Log.d("SoundSensitivity", "Set volume to AVERAGE")
            }
        }
    }



    fun calculateSelectedTime(start: String, end: String): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startDate = format.parse(start)
        var endDate = format.parse(end)

        // If endTime is earlier than startTime, assume it's the next day
        if (endDate.before(startDate)) {
            endDate.time += 24 * 60 * 60 * 1000 // add 24 hours
        }

        val durationMillis = endDate.time - startDate.time
        val minutes = durationMillis / (1000 * 60)
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return String.format("%02d:%02d", hours, remainingMinutes)
    }


//    val calendar = Calendar.getInstance()
    val calendarStart = Calendar.getInstance()
    val calendarEnd = Calendar.getInstance()

    val startTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            startTime = String.format("%02d:%02d", hour, minute)
//            selectedTime = calculateSelectedTime(startTime, endTime)
        },
        calendarStart.get(Calendar.HOUR_OF_DAY),
        calendarStart.get(Calendar.MINUTE),
        true
    )

    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            endTime = String.format("%02d:%02d", hour, minute)
//            selectedTime = calculateSelectedTime(startTime, endTime)
        },
        calendarEnd.get(Calendar.HOUR_OF_DAY),
        calendarEnd.get(Calendar.MINUTE),
        true
    )








    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                title = {
                    Text(
//                        text = "Setting",
                        text = stringResource(id = R.string.setting),

                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(

                        onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.keyboard_backspace),
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(34.dp)
                                .padding(start = 10.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = 2.dp)
                ) {

                    BannersAds(modifier = Modifier.fillMaxWidth(), adSize =  AdSize.LARGE_BANNER)

//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(110.dp)
//                            .clickable {
//                                showDialog = true
//                            }, // Set height as needed
//                        shape = RoundedCornerShape(12.dp), // Slightly rounded corners
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(
//                                    brush = Brush.horizontalGradient(
//                                        colors = listOf(
////                                            Color(0xFF81C784),
//                                            Color(0xFFFFAA4D),
//                                            Color(0xFFFF8600), // Start color (green)
//                                            // End color (light green)
//                                        )
//                                    )
//                                ),
////                            contentAlignment = Alignment.Center
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp),
////                                horizontalArrangement = Arrangement.Center,
////                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Column {
//                                    Text(
//                                        text = stringResource(id = R.string.remove_ads),
////                                        text = "Remove ADs",
//                                        color = Color.White,
//                                        fontWeight = FontWeight.Bold,
////                                        style = MaterialTheme.typography.titleMedium
//                                        fontSize = 22.sp,
//                                    )
//                                    Spacer(modifier = Modifier.height(8.dp))
//                                    Text(
//                                        text = stringResource(id = R.string.remove_ads_desc),
////                                        text = "Remove all ads from the \napplication.",
//                                        color = Color.White.copy(alpha = 0.8f),
//                                        style = MaterialTheme.typography.bodyMedium
//                                    )
//
//
//                                }
//                                Spacer(modifier = Modifier.weight(1f))
//                                Icon(
//                                    painter = painterResource(id = R.drawable.request),
//                                    contentDescription = "flash_on",
//                                    tint = Color.White,
//                                    modifier = Modifier
//                                        .size(64.dp)
//                                )
//
//                            }
//                        }
//                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(id = R.string.command),
//                        "Command",
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(6.dp))


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = mode_setting
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.flash_on),
                                        contentDescription = "flash_on",
                                        tint = Color.Black,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = stringResource(id = R.string.sound_sensitivity),
//                                        text = "Sound Sensitivity",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                options.forEach { optionText ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedOption = optionText }
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = optionText,
                                            modifier = Modifier.weight(1f),
//                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Gray,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W600
                                        )
                                        RadioButton(
                                            selected = selectedOption == optionText,
                                            onClick = {
                                                selectedOption = optionText
                                                onSensitivityChanged(optionText, context)
                                            },
                                            modifier = Modifier.scale(0.8f),
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = Color(0xFF4CAF50), // Green when selected
                                                unselectedColor = Color.Gray
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = mode_setting)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.flash_on),
                                            contentDescription = "flash_on",
                                            tint = Color.Black,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = stringResource(id = R.string.schedule_deactivate),
//                                            text = "Schedule-Deactivate\nFeature",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    }


                                    // Switch UI
                                    Switch(
                                        checked = isSwitchOn,
                                        onCheckedChange = { isSwitchOn = it },
//                                        checked = isActives,
//                                        onCheckedChange = { newValue ->
//                                            tempSwitchState = newValue
//                                            showDialogs = true // Show dialog first
//                                            //
////                                            if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
//////                                                selectedTime = calculateSelectedTime(startTime, endTime)
////                                                selectedTime = endTime
////                                                mainViewModel.scheduleStopAt(endTime)
////                                                mainViewModel.scheduleStopAt(selectedTime)
////                                            }
//
//                                        },
                                        modifier = Modifier.scale(0.8f),
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            uncheckedThumbColor = Color.Gray,
                                            checkedTrackColor = green,
                                            uncheckedTrackColor = mode_switch,
                                            uncheckedBorderColor = Color.Unspecified

                                        ),
                                    )

                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Start & End time buttons
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = stringResource(id = R.string.start),
//                                            text = "Start",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = {
                                                val cal = Calendar.getInstance()
                                                TimePickerDialog(
                                                    context,
                                                    { _, hour, minute ->
                                                        startHour = hour
                                                        startMinute = minute
                                                    },
                                                    cal.get(Calendar.HOUR_OF_DAY),
                                                    cal.get(Calendar.MINUTE),
                                                    true
                                                ).show()
                                            },
                                            enabled = isSwitchOn,
                                            modifier = Modifier.width(100.dp),
                                            shape = RoundedCornerShape(3.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = green_bg)
                                        ) {
//                                            Text(startTime, color = green)
                                            Text(
                                                "${
                                                    startHour?.let {
                                                        String.format(
                                                            "%02d:%02d",
                                                            it,
                                                            startMinute
                                                        ) } ?: "--:--"
                                                }",
                                                color = Color(0xFF4CAF50))
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(32.dp))

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = stringResource(id = R.string.end),
//                                            text = "End",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Button(
                                            onClick = {
                                                val cal = Calendar.getInstance()
                                                TimePickerDialog(
                                                    context,
                                                    { _, hour, minute ->
                                                        endHour = hour
                                                        endMinute = minute
                                                    },
                                                    cal.get(Calendar.HOUR_OF_DAY),
                                                    cal.get(Calendar.MINUTE),
                                                    true
                                                ).show()
                                            },
                                            enabled = isSwitchOn,
//                                            onClick = { endTimePickerDialog.show() },
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.width(100.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = green_bg) // your green_bg
                                        ) {
//                                            Text(endTime, color = Color(0xFF4CAF50)) // your green
                                            Text(
                                                "${
                                                    endHour?.let {
                                                        String.format(
                                                            "%02d:%02d",
                                                            it,
                                                            endMinute
                                                        )
                                                    } ?: "--:--"
                                                }",
                                                color = Color(0xFF4CAF50))

                                        }
                                        Spacer(modifier = Modifier.height(3.dp))
                                    }
                                }
                            }
                        }
                    }




                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "About",
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("LanuageScreen")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_language_24),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Language",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_arrow_right),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(34.dp)
                            )


                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showSheet = true

                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_star_rate_24),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = stringResource(id = R.string.rate_me),
//                                text = "Rate Me",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_arrow_right),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(34.dp)
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
//                            navController.navigate("TimeRangeSelector")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_feedback_24),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
//                                text = stringResource(id = R.string.feedback),
                                text = "More Apps",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_arrow_right),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(34.dp)
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
//                            navController.navigate("PrivacyPolicyScreen")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_privacy_tip_24),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = stringResource(id = R.string.privacy_policy),
//                                text = "Privacy Policy",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_arrow_right),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(34.dp)
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = mode_setting),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
//                            openAppInPlayStore(context)
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_vesion_2_24),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = stringResource(id = R.string.version),
//                                text = "Version",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_arrow_right),
                                contentDescription = "flash_on",
                                tint = black,
                                modifier = Modifier
                                    .size(34.dp)
                            )

                        }
                    }
                }

            }
        }
    )
    if (showDialog) {
        Dialog(

            onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
//                            text = "Remove Ads",
                            text = stringResource(id = R.string.remove_ads),

                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        IconButton(onClick = { showDialog = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
//                        text = "Subscribe to remove all ads and enjoy an uninterrupted experience.",
                        text = stringResource(id = R.string.subscribe_remove_ads),

                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Handle subscription */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text(
                            text = stringResource(id = R.string.go_premium),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
    if (showSheet) {
        ModalBottomSheet(
            contentColor = Color.Black,
            containerColor = Color.White,
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
//                            text = "Rate This App",
                            text = stringResource(id = R.string.rate_this_app),

                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = { showSheet = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Star Rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(5) { index ->
                            IconButton(onClick = {
                                rating = if (rating == index + 1) index else index + 1
                            }) {
                                Icon(
                                    imageVector = if (index < rating) Icons.Default.Star else Icons.Outlined.Star,
                                    contentDescription = "Star",
//                                    tint = Color(0xFFFFC107),
                                    tint = if (index < rating) Color(0xFFFFC107) else Color.Gray,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
//                        repeat(5) { index ->
//                            IconButton(onClick = { rating = index + 1 }) {
//                                Icon(
//                                    imageVector = if (index < rating) Icons.Default.Star else Icons.Outlined.Star,
//                                    contentDescription = "Star",
//                                    tint = Color(0xFFFFC107),
//                                    modifier = Modifier.size(36.dp)
//                                )
//                            }
//                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            // Handle rating submit

                            shareText(context, appLink)
//                            openAppInPlayStore(context)
                            showSheet = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text(
                            text = stringResource(id = R.string.submit),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    if (showDialogs) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialogs = false },
            title = {
                Text(
                    text = stringResource(id = R.string.confirmation),
                )
            },
            text = { Text("Are you sure you want to ${if (tempSwitchState) "activate" else "deactivate"} this feature?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        isActives = tempSwitchState
                        showDialogs = false
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.yes),
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialogs = false
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                    )
                }
            }
        )
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun MoreSettingsScreenPreview() {
//    // Create a mock or remember nav controller for preview
//    val navController = rememberNavController()
//    MoreSettingsScreen(navController = navController, mainViewModel = mainViewModel)
//}


fun shareText(context: Context, text: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share via")
    context.startActivity(shareIntent)
}


fun openAppInPlayStore(context: Context) {
    val packageName = context.packageName
    val uri = "market://details?id=$packageName".toUri()
    val goToMarket = Intent(Intent.ACTION_VIEW, uri).apply {
        addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
    }

    try {
        context.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        // Fallback if Play Store is not installed
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}