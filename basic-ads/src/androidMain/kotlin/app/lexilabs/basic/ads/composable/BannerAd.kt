package app.lexilabs.basic.ads.composable

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.toAndroid
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

@OptIn(DependsOnGoogleMobileAds::class)
@RequiresPermission("android.permission.INTERNET")
@Composable
public actual fun BannerAd(
    adUnitId: String,
    adSize: AdSize,
    onLoad: () -> Unit
) {
    AndroidView(
        factory = { context ->
            val bannerView = AdView(context)
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
 * @param ad Your pre-loaded [rememberBannerAd]
 * @see AdUnitId.autoSelect
 */
@OptIn(DependsOnGoogleMobileAds::class)
@RequiresPermission("android.permission.INTERNET")
@Composable
public actual fun BannerAd(
    ad: BannerAdHandler
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    // Manage the AdView's lifecycle with DisposableEffect.
    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> ad.bannerView?.resume()
                Lifecycle.Event.ON_PAUSE -> ad.bannerView?.pause()
                Lifecycle.Event.ON_DESTROY -> ad.bannerView?.destroy()
                else -> { /* Do nothing for other events */ }
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            // Create a FrameLayout to hold the AdView. This is important to ensure
            // the AdView always has a fresh parent when it's added.
            FrameLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // If the adView already has a parent, remove it. This is a defensive check
                // although `remember` should prevent it from being re-created if it's the same instance.
                (ad.bannerView?.parent as? ViewGroup)?.removeView(ad.bannerView)
                addView(ad.bannerView) // Add the retained AdView to the new FrameLayout.
            }
        },
        update = { frameLayout ->
            // Re-adding the adView to the frameLayout on update.
            // Ensure the AdView is not already a child of this FrameLayout.
            if (ad.bannerView?.parent != frameLayout) {
                (ad.bannerView?.parent as? ViewGroup)?.removeView(ad.bannerView)
                frameLayout.addView(ad.bannerView)
            }
        }
    )
}