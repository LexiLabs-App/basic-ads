package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.RewardedAdHandler

/**
 * Remembers a [RewardedAdHandler], which is used to load and show rewarded ads.
 *
 * This function will automatically attempt to load an ad when the [RewardedAdHandler.state]
 * is [AdState.NONE] or [AdState.DISMISSED].
 *
 * @param activity The activity to use for loading and showing the ad. This should be an Android `Activity`.
 * @param adUnitId The ad unit ID to use for loading the ad. Defaults to [AdUnitId.REWARDED_DEFAULT].
 * @param onLoad A callback that will be invoked when the ad has successfully loaded.
 * @param onFailure A callback that will be invoked if the ad fails to load, providing an [Exception] with details of the failure.
 * @return A [MutableState] holding the [RewardedAdHandler]. You can observe this state to react to changes in the ad's lifecycle.
 */
@DependsOnGoogleMobileAds
@Composable
public fun rememberRewardedAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_DEFAULT,
    userId: String? = null,
    customData: String? = null,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<RewardedAdHandler> {
    val ad = remember(activity) { mutableStateOf(RewardedAdHandler(activity)) }
    when(ad.value.state){
        AdState.DISMISSED,
        AdState.NONE -> {
            ad.value.load(
                adUnitId = adUnitId,
                userId = userId,
                customData = customData,
                onLoad = onLoad,
                onFailure = onFailure
            )
        }
        else -> { /** DO NOTHING **/ }
    }
    return ad
}
