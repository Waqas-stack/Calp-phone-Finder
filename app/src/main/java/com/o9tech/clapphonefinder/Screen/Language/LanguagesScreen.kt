package com.o9tech.clapphonefinder.Screen.Language

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.o9tech.clapphonefinder.Locale.LocaleManager
import com.o9tech.clapphonefinder.MainActivity
import com.o9tech.clapphonefinder.R
import com.o9tech.clapphonefinder.model.Languagesis
import com.o9tech.clapphonefinder.ui.theme.mode_setting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanuageScreen(navController: NavHostController) {
//    val languages = listOf(
//        "English", "Urdu", "Arabic", "French", "German", "Spanish",
//        "Russian", "Hindi", "Bengali", "Turkish", "Italian", "Japanese",
//        "Chinese", "Portuguese", "Persian", "Korean", "Greek", "Polish",
//        "Dutch", "Swedish", "Thai", "Indonesian"
//    )

    val context = LocalContext.current
    val savedLangCode = LocaleManager.getSavedLanguage(context)
//    val savedLanguage = languageList.find { it.languageCode == savedLangCode }?.languageName ?: "English"
    val languageList = listOf(
        Languagesis("English", "en"),
        Languagesis("Urdu", "ur"),
        Languagesis("Arabic", "ar"),
        Languagesis("French", "fr"),
        Languagesis("German", "de"),
        Languagesis("Spanish", "es"),
        Languagesis("Russian", "ru"),
        Languagesis("Hindi", "hi"),
        Languagesis("Bengali", "bn"),
        Languagesis("Turkish", "tr"),
        Languagesis("Italian", "it"),
        Languagesis("Japanese", "ja"),
        Languagesis("Chinese", "zh"),
        Languagesis("Portuguese", "pt"),
        Languagesis("Persian", "peo"),
        Languagesis("Korean", "ko"),
        Languagesis("Greek", "el"),
        Languagesis("Polish", "pl"),
        Languagesis("Dutch", "nl"),
        Languagesis("Swedish", "sv"),
        Languagesis("Thai", "th"),
        Languagesis("Indonesian", "id")
    )


//    val languageLocaleMap = mapOf(
//        "Urdu" to "ur",
//        "Arabic" to "ar",
//        "French" to "fr",
//        "German" to "de",
//        "Spanish" to "es",
//        "Russian" to "ru",
//        "Hindi" to "hi",
//        "Bengali" to "bn",
//        "Turkish" to "tr",
//        "Japanese" to "ja",
//        "Chinese" to "zh",
//        "Portuguese" to "pt",
//        "Persian" to "fa",
//        "Korean" to "ko",
//        "Greek" to "el",
//        "Polish" to "pl",
//        "Swedish" to "sv",
//        "Thai" to "th",
//    )
//    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedLanguage by remember { mutableStateOf(savedLangCode) }

//    val context= LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
                title = {
                    Text(
                        text = "Language",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
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
                    .padding(paddingValues)
            ) {


                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(languageList.size) { index ->
                        val language = languageList[index]
                        val isSelected = selectedLanguage == language.languageCode

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    selectedLanguage = language.languageCode
                                    LocaleManager.setLocale(context, language.languageCode)

                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFF4CAF50) else Color(0xFFF2F2F2)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = language.languageName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color.Black
                                )

                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        selectedLanguage = language.languageCode
                                        LocaleManager.setLocale(context, language.languageCode)

                                        val intent = Intent(context, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        context.startActivity(intent)
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color.Gray
                                    ),
                                    modifier = Modifier.scale(0.85f)
                                )
                            }
                        }
                    }
                }

//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    modifier = Modifier
//                        .fillMaxSize().background(Color.White)
//                        .padding(18.dp),
//                    verticalArrangement = Arrangement.spacedBy(18.dp),
//                    horizontalArrangement = Arrangement.spacedBy(18.dp)
//                ) {
//                    items(languages.size) { index ->
//                        val language = languages[index]
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(60.dp).clickable {
//                                    selectedLanguage = language
////                                    val localeCode = languageLocaleMap[language] ?: "en"
//                                    val newContext = LocaleManager.setLocale(context, localeCode)
//
//                                    // Restart app to apply language
//                                    val intent = Intent(context, MainActivity::class.java)
//                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    context.startActivity(intent)
//                                },
////                                .clickable {
////                                    selectedLanguage = language
////                                },
//                            shape = RoundedCornerShape(12.dp),
//                            colors = CardDefaults.cardColors(
//                                containerColor = if (selectedLanguage == language) Color(0xFF4CAF50) else mode_setting
//                            )
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(horizontal = 12.dp),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Text(
//                                    text = language,
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Medium,
//                                    color = if (selectedLanguage == language) Color.White else Color.Black
//                                )
//
//                                RadioButton(
//                                    selected = selectedLanguage == language,
//                                    onClick = { selectedLanguage = language },
//                                    colors = RadioButtonDefaults.colors(
//                                        selectedColor = Color.White,
//                                        unselectedColor = Color.Gray
//                                    ),
//                                    modifier = Modifier.scale(0.85f)
//                                )
//                            }
//                        }
//
//                    }
//                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageScreen() {
    val navController = rememberNavController()
    LanuageScreen(navController = navController)
}


