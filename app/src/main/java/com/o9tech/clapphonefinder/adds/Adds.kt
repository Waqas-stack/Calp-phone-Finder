package com.o9tech.clapphonefinder.adds

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun BannersAds(modifier: Modifier, adSize: AdSize = AdSize.BANNER) {

    AndroidView(modifier = modifier.fillMaxWidth(), factory = {
        AdView(it).apply {
            setAdSize(adSize)
            adUnitId = AppConstant.BANNER_AD_UNIT_ID
            loadAd(AdRequest.Builder().build())
        }
    }
    )
}