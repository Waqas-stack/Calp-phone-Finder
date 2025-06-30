package com.o9tech.clapphonefinder.Screen.Home

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.o9tech.clapphonefinder.Foreground.DetectionManager
import com.o9tech.clapphonefinder.ui.theme.settingsclr
import com.o9tech.clapphonefinder.R
import com.o9tech.clapphonefinder.ViewModel.MainViewModel
import com.o9tech.clapphonefinder.ui.theme.black
import com.o9tech.clapphonefinder.ui.theme.green
import com.o9tech.clapphonefinder.ui.theme.mode_setting
import com.o9tech.clapphonefinder.ui.theme.mode_switch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity


    var flashmode by remember { mutableStateOf(false) }
    var vibrationmode by remember { mutableStateOf(false) }
    val isDetectionActive by mainViewModel.isDetectionActive.collectAsState()

    val interactionSource = remember { MutableInteractionSource() }

    var loadInterstitialAd by remember { mutableStateOf(true) }

    var interstitialAd: InterstitialAd? by remember { mutableStateOf(null) }
    var shouldShowAd by remember { mutableStateOf(false) }




    LaunchedEffect(loadInterstitialAd) {
        if (loadInterstitialAd) {
            InterstitialAd.load(
                context,
                "ca-app-pub-3940256099942544/1033173712",
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        println("Ad failed to load: ${error.message}")
                    }

                    override fun onAdLoaded(loadedAd: InterstitialAd) {
                        println("Ad Loaded Successfully")
                        interstitialAd = loadedAd
                    }
                }
            )
        }
    }


    if (shouldShowAd) {

        loadAd(context) { ad ->
            interstitialAd = ad
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    shouldShowAd = false
//                    safeNavController.popBackStack() // Navigate back
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                    shouldShowAd = false
                }
            }
            activity?.let { ad.show(it) }
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)

                    ) {


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { /* TODO: Handle click */ },
                                shape = RoundedCornerShape(50), // Fully rounded
                                colors = ButtonDefaults.buttonColors(containerColor = settingsclr),
                                contentPadding = PaddingValues(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                ), // Small size
                                modifier = Modifier.height(29.dp) // Adjust height as needed
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.vip),
//                                    imageVector = Icons.Default.Favorite, // Replace with your icon
                                    contentDescription = "Like",
                                    tint = colorResource(R.color.white),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "PRO",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                        }





                        IconButton(onClick = {
                            navController.navigate("MoreSettingsScreen")
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.settings),
                                contentDescription = "Another Action",
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF6FDE8))
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFF6FDE8),
                                    Color(0xFF4CAF50)
                                )
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(180.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                if (isDetectionActive) {
                                    shouldShowAd = true
                                    mainViewModel.stopDetection()
                                    navController.navigate("ServiceClosedScreen")
                                } else {
                                    mainViewModel.startDetection("YES")
                                    navController.navigate("SuccessScreen")

                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(green)
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(125.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.on),
                                contentDescription = "Center Icon",
                                tint = green,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }

                    Text(
                        text = if (isDetectionActive) stringResource(R.string.clap_to_find_desc) else stringResource(
                            R.string.click_to_activate
                        ),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp, color =
                            Color.Black, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Card(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = mode_setting),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate("HowToUseScreen")
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center

                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.questionmark),
                                                contentDescription = "App Icon",
                                                modifier = Modifier
                                                    .size(64.dp),
                                                tint = Color.Unspecified
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
//                                                text = "How to use",
                                                text = stringResource(id = R.string.how_to_use),
                                                fontSize = 16.sp,
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = mode_setting),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate("SoundScreen")
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.airhorn),
                                                contentDescription = "App Icon",
                                                modifier = Modifier.size(64.dp),
                                                tint = Color.Unspecified
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
//                                            Text(text = stringResource(id = R.string.sound))
                                            Text(
//                                                text = "Sound",
                                                text = stringResource(id = R.string.sound),
                                                fontSize = 16.sp,
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    }
                                }

                            }

                            Spacer(modifier = Modifier.height(16.dp))


                            Card(
                                shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = mode_setting),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.flash_on),
                                        contentDescription = "flash_on",
                                        tint = black,
                                        modifier = Modifier
                                            .size(28.dp)
                                    )
                                    Text(
//                                        text = "Flash Mode",
                                        text = stringResource(id = R.string.flash_mode),
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(
                                        modifier = Modifier.scale(0.8f),
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            uncheckedThumbColor = Color.Gray,
                                            checkedTrackColor = green,
                                            uncheckedTrackColor = mode_switch,
                                            uncheckedBorderColor = Color.Unspecified
                                        ),

                                        checked = flashmode, // Replace with your state variable
                                        onCheckedChange = {
                                            flashmode = it
                                            DetectionManager.flashEnabled = it

                                        },
//                                        modifier = Modifier.padding(16.dp)
                                    )

                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                shape = RoundedCornerShape(16.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = mode_setting),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vibration),
                                        contentDescription = "vibration_on",
                                        tint = black,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .graphicsLayer(scaleX = -1f)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
//                                        text = "Vibration Mode",
                                        text = stringResource(id = R.string.vibration_mode),
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(
                                        modifier = Modifier.scale(0.8f),

                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            uncheckedThumbColor = Color.Gray,
                                            checkedTrackColor = green,
                                            uncheckedTrackColor = mode_switch,
                                            uncheckedBorderColor = Color.Unspecified

                                        ),
                                        checked = vibrationmode, // Replace with your state variable
                                        onCheckedChange = {
                                            vibrationmode = it
                                            DetectionManager.vibrationEnabled = it

                                        },
//                                        modifier = Modifier.padding(16.dp)
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
                                        navController.navigate("MoreSettingsScreen")
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.menu),
                                        contentDescription = "flash_on",
                                        tint = black,
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
//                                        text = "More Settings",
                                        text = stringResource(id = R.string.more_settings),
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        painter = painterResource(id = R.drawable.keyboard_arrow_right),
                                        contentDescription = "More_Settings",
                                        tint = black,
                                        modifier = Modifier
                                            .size(34.dp)
                                    )

                                }
                            }


                        }
                    }


                }
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val navController = rememberNavController()
//    HomeScreen(navController, mainViewModel)
//}


fun loadAd(context: Context, onAdLoaded: (InterstitialAd) -> Unit) {
    InterstitialAd.load(
        context,
        "ca-app-pub-3940256099942544/1033173712", // test ad unit
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                onAdLoaded(ad)
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                println("Ad failed: ${error.message}")
            }
        }
    )
}
