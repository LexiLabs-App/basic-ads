package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.RewardedInterstitialAd

@DependsOnGoogleMobileAds
@Composable
public fun rememberRewardedInterstitialAd(activity: Any?): MutableState<RewardedInterstitialAd> =
    remember(activity) { mutableStateOf(RewardedInterstitialAd(activity)) }