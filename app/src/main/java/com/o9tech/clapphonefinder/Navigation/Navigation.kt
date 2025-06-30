package com.o9tech.clapphonefinder.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.o9tech.clapphonefinder.Screen.Active_deactive.ActiveDeActive
import com.o9tech.clapphonefinder.Screen.Active_deactive.ServiceClosedScreen
import com.o9tech.clapphonefinder.Screen.Active_deactive.SuccessScreen
import com.o9tech.clapphonefinder.Screen.ClapDectionScreen.ClapDetectionScreen
import com.o9tech.clapphonefinder.Screen.Home.HomeScreen
import com.o9tech.clapphonefinder.Screen.HowToUse.HowToUseScreen
import com.o9tech.clapphonefinder.Screen.Language.LanuageScreen
import com.o9tech.clapphonefinder.Screen.MoreSettings.MoreSettingsScreen
import com.o9tech.clapphonefinder.Screen.Sounds.SoundScreen
import com.o9tech.clapphonefinder.ViewModel.MainViewModel

@Composable
fun Navigation(){
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination ="HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController = navController,mainViewModel)
        }
        composable("ClapDetectionScreen") {
            ClapDetectionScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable("MoreSettingsScreen") {
            MoreSettingsScreen(navController = navController,mainViewModel)
        }
        composable("LanuageScreen") {
            LanuageScreen(navController = navController,)
        }
        composable("ActiveDeActive") {
            ActiveDeActive(navController = navController,)
        }
        composable("SoundScreen") {
            SoundScreen(navController = navController,)
        }
        composable("HowToUseScreen") {
            HowToUseScreen(navController = navController,)
        }
        composable("SuccessScreen") {
            SuccessScreen(navController = navController,)
        }
        composable("ServiceClosedScreen") {
            ServiceClosedScreen(navController = navController,)
        }
    }

}