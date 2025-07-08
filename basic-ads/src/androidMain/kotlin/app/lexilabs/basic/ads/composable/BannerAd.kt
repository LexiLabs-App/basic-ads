package app.lexilabs.basic.ads.composable

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.BannerView
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.toAndroid
import com.google.android.gms.ads.AdRequest

@RequiresPermission("android.permission.INTERNET")
@Composable
public actual fun BannerAd(
    adUnitId: String,
    adSize: AdSize,
    onLoad: () -> Unit
) {
    AndroidView(
        factory = { context ->
            val bannerView = BannerView(context)
            bannerView.apply {
                this.setAdSize(adSize.toAndroid())
                this.adUnitId = adUnitId
                this.loadAd(AdRequest.Builder().build())
                if (!this.isLoading) { onLoad() }
            }
        }
    )
}

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param loadedAd Your pre-loaded [rememberBannerAd]
 * @see AdUnitId.autoSelect
 */
@RequiresPermission("android.permission.INTERNET")
@Composable
public actual fun BannerAd(
    loadedAd: BannerAdHandler,
) {
    AndroidView(
        factory = { loadedAd.bannerView }
    )
}