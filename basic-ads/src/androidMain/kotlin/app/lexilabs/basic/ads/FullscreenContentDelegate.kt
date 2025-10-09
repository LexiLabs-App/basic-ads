package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback

/**
 * A [FullScreenContentCallback] for handling full-screen ad content events.
 *
 * @param onDismissed A callback invoked when the ad is dismissed.
 * @param onShown A callback invoked when the ad is shown.
 * @param onImpression A callback invoked when an impression is recorded for the ad.
 * @param onClick A callback invoked when the ad is clicked.
 * @param onFailure A callback invoked when the ad fails to show.
 */
public class FullscreenContentDelegate(
    private val onDismissed: () -> Unit,
    private val onShown: () -> Unit,
    private val onImpression: () -> Unit,
    private val onClick: () -> Unit,
    private val onFailure: (Exception) -> Unit
): FullScreenContentCallback() {

    private val tag = "FullscreenContentDelegate"

    override fun onAdClicked() {
        super.onAdClicked()
        // Called when a click is recorded for an ad.
        Log.d(tag, "Ad was clicked.")
        onClick()
    }

    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        // Called when ad is dismissed.
        Log.d(tag, "Ad dismissed fullscreen content.")
        onDismissed()
    }

    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
        super.onAdFailedToShowFullScreenContent(p0)
        // Called when ad fails to show.
        Log.e(tag, "Ad failed to show fullscreen content. ${p0.message}")
        onFailure(AdException(p0.message))
    }

    override fun onAdImpression() {
        super.onAdImpression()
        // Called when an impression is recorded for an ad.
        Log.d(tag, "Ad recorded an impression.")
        onImpression()
    }

    override fun onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent()
        // Called when ad is shown.
        Log.d(tag, "Ad showed fullscreen content.")
        onShown()
    }
}