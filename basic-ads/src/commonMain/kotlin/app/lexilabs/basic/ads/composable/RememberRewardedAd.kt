package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.RewardedAd

@DependsOnGoogleMobileAds
@Composable
public fun rememberRewardedAd(activity: Any?): MutableState<RewardedAd> =
    remember(activity) { mutableStateOf(RewardedAd(activity)) }