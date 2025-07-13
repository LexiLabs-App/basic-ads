package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError

public class BannerAdListener(
    public val onLoad: () -> Unit,
    public val onFailure: (Exception) -> Unit,
    public val onDismissed: () -> Unit,
    public val onShown: () -> Unit,
    public val onImpression: () -> Unit,
    public val onClick: () -> Unit
): AdListener() {

    private val tag = "BannerAdListener"

    override fun onAdClicked() {
        super.onAdClicked()
        Log.d(tag, "onClick event")
        onClick()
    }

    override fun onAdClosed() {
        super.onAdClosed()
        Log.d(tag, "onDismissed event")
        onDismissed()
        // Code to be executed when the user is about to return
        // to the app after tapping on an ad.
    }

    override fun onAdFailedToLoad(adError: LoadAdError) {
        super.onAdFailedToLoad(adError)
        Log.e(tag, "onFailure: ${adError.message}")
        onFailure(AdException(adError.message))
    }

    override fun onAdImpression() {
        super.onAdImpression()
        Log.d(tag, "onImpression event")
        onImpression()
        // Code to be executed when an impression is recorded
        // for an ad.
    }

    override fun onAdLoaded() {
        super.onAdLoaded()
        Log.d(tag, "onLoad event")
        onLoad()
    }

    override fun onAdOpened() {
        super.onAdOpened()
        Log.d(tag, "onShown event")
        onShown()
        // Code to be executed when an ad opens an overlay that
        // covers the screen.
    }
}