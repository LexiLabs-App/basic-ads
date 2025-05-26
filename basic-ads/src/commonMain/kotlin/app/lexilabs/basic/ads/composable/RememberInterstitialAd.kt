package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAd

@DependsOnGoogleMobileAds
@Composable
public fun rememberInterstitialAd(activity: Any?): MutableState<InterstitialAd> =
    remember(activity) { mutableStateOf(InterstitialAd(activity)) }