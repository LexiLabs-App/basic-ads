package app.lexilabs.basic.ads.composable

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.getCurrentViewController
import app.lexilabs.basic.ads.toCGRectCValue
import cocoapods.Google_Mobile_Ads_SDK.GADBannerView
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
public actual fun BannerAd(
    adUnitId: String,
    adSize: AdSize,
    onLoad: () -> Unit
) {
    UIKitView(
        factory = {
            val viewController = getCurrentViewController() //UIApplication.sharedApplication.keyWindow?.rootViewController
            checkNotNull(viewController) { "Root ViewController is null" }

            val bannerView = GADBannerView(adSize.toCGRectCValue()).apply {
                adUnitID = adUnitId
                this.rootViewController = viewController
                loadRequest(GADRequest())
                if (viewController.viewLoaded) { onLoad() }
            }
            bannerView
        },
        modifier = Modifier.size(width = adSize.width.dp, height = adSize.height.dp)
    )
}

@OptIn(ExperimentalForeignApi::class)
@Composable
public actual fun BannerAd(
    ad: BannerAdHandler
) {
    UIKitView(
        factory = {
            ad.bannerView
        },
        modifier = Modifier.size(
            width = ad.adSize.width.dp,
            height = ad.adSize.height.dp
        )
    )
}
