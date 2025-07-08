package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param adSize Your AdMob [AdSize]
 * @param onLoad Lambda expression that executes after the Google AdRequest has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public expect fun BannerAd(
    adUnitId: String = AdUnitId.BANNER_DEFAULT,
    adSize: AdSize = AdSize.FULL_BANNER,
    onLoad: () -> Unit = {}
)

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param loadedAd Your pre-loaded [rememberBannerAd]
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public expect fun BannerAd(
    loadedAd: BannerAdHandler
)