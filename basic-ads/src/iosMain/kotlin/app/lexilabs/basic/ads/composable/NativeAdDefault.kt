package app.lexilabs.basic.ads.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.lexilabs.basic.ads.nativead.NativeAdData
import platform.UIKit.UIImageView

@Composable
public actual fun NativeAdDefault(activity: Any?, nativeAd: NativeAdData) {
    /** Display a native ad with a user defined template. */
    Box(modifier = Modifier.padding(8.dp).wrapContentHeight(Alignment.Top)) {
        // Inside the NativeAdView composable, display the native ad assets.
        Column(Modifier.align(Alignment.TopStart).wrapContentHeight(Alignment.Top)) {
            // Display the ad attribution.
            BasicText(text = "ad")
            Row {
                // If available, display the icon asset.
                nativeAd.icon?.let { icon ->
                    icon.image?.let {
                        UIImageView().image = it
                    }
                }
            }
            Column {
                // If available, display the headline asset.
                nativeAd.headline?.let {
                    BasicText(text = it)

                }
                // If available, display the star rating asset.
                nativeAd.starRating?.let {
                    BasicText(text = "Rated $it")
                }
            }

            // If available, display the body asset.
            nativeAd.body?.let { BasicText(text = it) }

            Row(Modifier.align(Alignment.End).padding(5.dp)) {
                // If available, display the price asset.
                nativeAd.price?.let {
                    BasicText(text = it)
                }
                // If available, display the store asset.
                nativeAd.store?.let {
                    BasicText(text = it)
                }
                // If available, display the call to action asset.
                // Note: The Jetpack Compose button implements a click handler which overrides the native
                // ad click handler, causing issues. Use the NativeAdButton which does not implement a
                // click handler. To handle native ad clicks, use the NativeAd AdListener onAdClicked
                // callback.
                nativeAd.callToAction?.let { callToAction ->
                    Box(
                        Modifier
                            .padding(5.dp)
                    ) {
                        BasicText(text = callToAction)
                    }
                }
            }
        }
    }
}