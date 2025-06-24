package com.o9tech.clapphonefinder

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.o9tech.clapphonefinder.Locale.LocaleManager
import com.o9tech.clapphonefinder.Navigation.Navigation
import com.o9tech.clapphonefinder.Screen.ClapDectionScreen.ClapDetectionScreen
import com.o9tech.clapphonefinder.Services.ClapDetector
import com.o9tech.clapphonefinder.ui.theme.ClapPhoneFinderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(base!!, LocaleManager.getSavedLanguage(base)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val clapDetector = ClapDetector()

        setContent {
            ClapPhoneFinderTheme {

//                var clapDetected by remember { mutableStateOf(false) }
//                LaunchedEffect(Unit) {
//                    clapDetector.startListening {
//                        clapDetected = true
//                        delay(1000)
//                        clapDetected = false
//                    }
//                }
//
//                ClapDetectionScreen(clapDetected)
//            }

                Navigation()
            }
        }}
    }


