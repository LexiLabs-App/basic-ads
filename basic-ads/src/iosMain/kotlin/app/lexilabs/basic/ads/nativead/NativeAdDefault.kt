package app.lexilabs.basic.ads.nativead

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.lexilabs.basic.ads.ExperimentalBasicAdsFeature
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * A default implementation of [NativeAdTemplate] for iOS.
 *
 * @param nativeAdData The data for the native ad.
 */
@ExperimentalBasicAdsFeature
public actual class NativeAdDefault actual constructor(
    actual override val nativeAdData: NativeAdData?
) : NativeAdTemplate(nativeAdData) {

    /**
     * Displays the native ad.
     *
     * @param modifier The modifier to be applied to the ad.
     */
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalForeignApi::class)
    @Composable
    actual override fun Show(modifier: Modifier) {
        Supervisor(modifier) {
            Box(modifier = Modifier.padding(8.dp).wrapContentHeight(Alignment.Top)) {
                Column(Modifier.align(Alignment.TopStart).wrapContentHeight(Alignment.Top)) {
                    Media()
                    Attribution(text = "ad")
                    Row { nativeAdData!!.icon?.image?.let { Icon { it } } }
                    Column {
                        nativeAdData!!.headline?.let { Headline { BasicText(it) } }
                        nativeAdData.starRating?.let { BasicText("Rated $it") }
                    }

                    nativeAdData!!.body?.let { Body { BasicText(it) } }

                    Row(Modifier.align(Alignment.End).padding(5.dp)) {
                        nativeAdData.price?.let { BasicText(it) }
                        nativeAdData.store?.let { BasicText(it) }
                        nativeAdData.callToAction?.let {
                            NativeAdButton(it)
                        }
                    }
                }
            }
        }
    }
}
