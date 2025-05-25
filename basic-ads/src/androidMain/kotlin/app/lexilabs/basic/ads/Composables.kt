package app.lexilabs.basic.ads

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
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
            val adView = AdView(context)
            adView.apply {
                this.setAdSize(adSize.toAndroid())
                this.adUnitId = adUnitId
                this.loadAd(AdRequest.Builder().build())
                if (!this.isLoading) { onLoad() }
            }
        }
    )
}