package com.o9tech.clapphonefinder.Screen

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

//@Composable
//fun TimeRangeSelector( navController: NavHostController) {
//    val context = LocalContext.current
//    var isEnabled by remember { mutableStateOf(false) }
//    var startTime by remember { mutableStateOf<LocalTime?>(null) }
//    var endTime by remember { mutableStateOf<LocalTime?>(null) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = "Schedule Deactivation", fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.weight(1f))
//            Switch(checked = isEnabled, onCheckedChange = { isEnabled = it })
//        }
//
//        if (isEnabled) {
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(onClick = {
//                showTimePicker(context) { time -> startTime = time }
//            }) {
//                Text(text = startTime?.toString() ?: "Select Start Time")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Button(onClick = {
//                showTimePicker(context) { time -> endTime = time }
//            }) {
//                Text(text = endTime?.toString() ?: "Select End Time")
//            }
//
//            if (startTime != null && endTime != null) {
//                val durationInMinutes = ((endTime!!.hour * 60 + endTime!!.minute) -
//                        (startTime!!.hour * 60 + startTime!!.minute)).let {
//                    if (it < 0) it + 24 * 60 else it // handles overnight difference
//                }
//
//                val hours = durationInMinutes / 60
//                val minutes = durationInMinutes % 60
//
//                Toast.makeText(context, "Duration: $hours hour(s) $minutes minute(s)", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }
//}
//
//fun showTimePicker(context: Context, onTimeSelected: (LocalTime) -> Unit) {
//    val now = LocalTime.now()
//    val timePicker = TimePickerDialog(
//        context,
//        { _, hour: Int, minute: Int ->
//            onTimeSelected(LocalTime.of(hour, minute))
//        },
//        now.hour,
//        now.minute,
//        true
//    )
//    timePicker.show()
//}
//


//@Composable
//fun TimeRangeSelector() {
//    val context = LocalContext.current
//    var isEnabled by remember { mutableStateOf(false) }
//
//    var startTime by remember { mutableStateOf<LocalTime?>(null) }
//    var endTime by remember { mutableStateOf<LocalTime?>(null) }
//
//    var showStartPicker by remember { mutableStateOf(false) }
//    var showEndPicker by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth()
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("Enable Time Selection", modifier = Modifier.weight(1f))
//            Switch(checked = isEnabled, onCheckedChange = {
//                isEnabled = it
//                startTime = null
//                endTime = null
//            })
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { if (isEnabled) showStartPicker = true },
//            enabled = isEnabled,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = startTime?.toString() ?: "Select Start Time")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { if (isEnabled) showEndPicker = true },
//            enabled = isEnabled,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = endTime?.toString() ?: "Select End Time")
//        }
//
//        // When both times selected, calculate duration
//        if (isEnabled && startTime != null && endTime != null) {
//            val durationInMinutes = ((endTime!!.hour * 60 + endTime!!.minute) -
//                    (startTime!!.hour * 60 + startTime!!.minute)).let {
//                if (it < 0) it + 24 * 60 else it // handle overnight wrap
//            }
//
//            val hours = durationInMinutes / 60
//            val minutes = durationInMinutes % 60
//
//            // Show toast
//            LaunchedEffect(startTime, endTime) {
//                Toast.makeText(
//                    context,
//                    "Duration: $hours hour(s) and $minutes minute(s)",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//    // TimePickers
//    if (showStartPicker) {
//        TimePickerDialog(
//            context,
//            { _, hour, minute -> startTime = LocalTime.of(hour, minute); showStartPicker = false },
//            LocalTime.now().hour,
//            LocalTime.now().minute,
//            true
//        ).show()
//    }
//
//    if (showEndPicker) {
//        TimePickerDialog(
//            context,
//            { _, hour, minute -> endTime = LocalTime.of(hour, minute); showEndPicker = false },
//            LocalTime.now().hour,
//            LocalTime.now().minute,
//            true
//        ).show()
//    }
//}



//@Composable
//fun TimeSelectionScreen() {
//    val context = LocalContext.current
//    var isSwitchOn by remember { mutableStateOf(false) }
//
//    var startHour by remember { mutableStateOf(0) }
//    var startMinute by remember { mutableStateOf(0) }
//    var endHour by remember { mutableStateOf(0) }
//    var endMinute by remember { mutableStateOf(0) }
//
//    val timeFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("Enable Time Selection", fontWeight = FontWeight.Bold)
//            Spacer(Modifier.weight(1f))
//            Switch(
//                checked = isSwitchOn,
//                onCheckedChange = { isSwitchOn = it }
//            )
//        }
//
//        Button(
//            onClick = {
//                val cal = Calendar.getInstance()
//                TimePickerDialog(
//                    context,
//                    { _, hour, minute ->
//                        startHour = hour
//                        startMinute = minute
//                    },
//                    cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE),
//                    true
//                ).show()
//            },
//            enabled = isSwitchOn
//        ) {
//            Text("Select Start Time: ${String.format("%02d:%02d", startHour, startMinute)}")
//        }
//
//        Button(
//            onClick = {
//                val cal = Calendar.getInstance()
//                TimePickerDialog(
//                    context,
//                    { _, hour, minute ->
//                        endHour = hour
//                        endMinute = minute
//                    },
//                    cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE),
//                    true
//                ).show()
//            },
//            enabled = isSwitchOn
//        ) {
//            Text("Select End Time: ${String.format("%02d:%02d", endHour, endMinute)}")
//        }
//
//        Button(
//            onClick = {
//                val startCal = Calendar.getInstance().apply {
//                    set(Calendar.HOUR_OF_DAY, startHour)
//                    set(Calendar.MINUTE, startMinute)
//                }
//
//                val endCal = Calendar.getInstance().apply {
//                    set(Calendar.HOUR_OF_DAY, endHour)
//                    set(Calendar.MINUTE, endMinute)
//                }
//
//                if (endCal.before(startCal)) {
//                    endCal.add(Calendar.DAY_OF_MONTH, 1)
//                }
//
//                val durationMillis = endCal.timeInMillis - startCal.timeInMillis
//                val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
//                val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60
//
//                Toast.makeText(
//                    context,
//                    "Duration: ${hours}h ${minutes}m",
//                    Toast.LENGTH_LONG
//                ).show()
//            },
//            enabled = isSwitchOn
//        ) {
//            Text("Calculate Duration")
//        }
//    }
//}



//@Composable
//fun TimeSelectionScreen() {
//    val context = LocalContext.current
//    var isSwitchOn by remember { mutableStateOf(false) }
//
//    var startHour by remember { mutableStateOf<Int?>(null) }
//    var startMinute by remember { mutableStateOf<Int?>(null) }
//    var endHour by remember { mutableStateOf<Int?>(null) }
//    var endMinute by remember { mutableStateOf<Int?>(null) }
//
//    val startTimeSelected = startHour != null && startMinute != null
//    val endTimeSelected = endHour != null && endMinute != null
//
//    // Auto-show toast when both times are selected
//    LaunchedEffect(startTimeSelected, endTimeSelected, isSwitchOn) {
//        if (isSwitchOn && startTimeSelected && endTimeSelected) {
//            val startCal = Calendar.getInstance().apply {
//                set(Calendar.HOUR_OF_DAY, startHour!!)
//                set(Calendar.MINUTE, startMinute!!)
//            }
//
//            val endCal = Calendar.getInstance().apply {
//                set(Calendar.HOUR_OF_DAY, endHour!!)
//                set(Calendar.MINUTE, endMinute!!)
//            }
//
//            if (endCal.before(startCal)) {
//                endCal.add(Calendar.DAY_OF_MONTH, 1)
//            }
//
//            val durationMillis = endCal.timeInMillis - startCal.timeInMillis
//            val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
//            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60
//
//            Toast.makeText(
//                context,
//                "Duration: ${hours}h ${minutes}m",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("Enable Time Selection", fontWeight = FontWeight.Bold)
//            Spacer(Modifier.weight(1f))
//            Switch(
//                checked = isSwitchOn,
//                onCheckedChange = { isSwitchOn = it }
//            )
//        }
//
//        Button(
//            onClick = {
//                val cal = Calendar.getInstance()
//                TimePickerDialog(
//                    context,
//                    { _, hour, minute ->
//                        startHour = hour
//                        startMinute = minute
//                    },
//                    cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE),
//                    true
//                ).show()
//            },
//            enabled = isSwitchOn
//        ) {
//            Text("Select Start Time: ${startHour?.let { String.format("%02d:%02d", it, startMinute) } ?: "--:--"}")
//        }
//
//        Button(
//            onClick = {
//                val cal = Calendar.getInstance()
//                TimePickerDialog(
//                    context,
//                    { _, hour, minute ->
//                        endHour = hour
//                        endMinute = minute
//                    },
//                    cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE),
//                    true
//                ).show()
//            },
//            enabled = isSwitchOn
//        ) {
//            Text("Select End Time: ${endHour?.let { String.format("%02d:%02d", it, endMinute) } ?: "--:--"}")
//        }
//    }
//}



@Composable
fun TimeSelectionWithAutoToast() {
    val context = LocalContext.current
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
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Enable Time Selection", fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isSwitchOn = it }
            )
        }

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
            enabled = isSwitchOn
        ) {
            Text("Select Start Time: ${startHour?.let { String.format("%02d:%02d", it, startMinute) } ?: "--:--"}")
        }

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
            enabled = isSwitchOn
        ) {
            Text("Select End Time: ${endHour?.let { String.format("%02d:%02d", it, endMinute) } ?: "--:--"}")
        }
    }
}
