package com.o9tech.clapphonefinder.model

data class SoundData(
    val soundName: String,
    val soundIcon: Int,
    val soundUri: String,
    val isSelected: Boolean = false
)
