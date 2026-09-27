package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.CustomTargeting
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param adSize Your AdMob [AdSize]
 * @param onLoad Lambda expression that executes after the Google AdRequest has fully loaded
 * @param customTargeting Optional [CustomTargeting] parameters for ad targeting
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public expect fun BannerAd(
    adUnitId: String = AdUnitId.BANNER_DEFAULT,
    adSize: AdSize = AdSize.FULL_BANNER,
    onLoad: () -> Unit = {},
    customTargeting: CustomTargeting? = null
)

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param ad The [BannerAdHandler] built within your [Composable] using [rememberBannerAd]
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public expect fun BannerAd(
    ad: BannerAdHandler
)