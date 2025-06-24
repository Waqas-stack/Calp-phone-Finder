package com.o9tech.clapphonefinder.Screen.Active_deactive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.o9tech.clapphonefinder.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceClosedScreen(navController: NavHostController) {

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
                title = {
                    Text(
                        text = "",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { Icon(
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

                Column(
                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.feature_deactivated),
//                        "Feature deactivated",
                        color = Color.Black, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
//                    Text(
//                        "please reactive the function to continue \nusing it", color = Color.Black, fontSize = 16.sp,
//                    )
                    Text(
//                        text = "please reactive the function to continue \nusing it",
                        text = stringResource(id = R.string.function_activated_tap_to_close),
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center, // ✅ Center each line of the text
                        modifier = Modifier.fillMaxWidth() // ✅ Needed for proper textAlign
                    )
                }



//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Service Closed", color = Color.Gray, fontSize = 20.sp)
//                }
            }
        }
    )
}