package app.lexilabs.basic.ads.nativead

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
import app.lexilabs.basic.logging.Log

public class NativeAdDefault public constructor(
    override val nativeAdData: NativeAdData? = null
): NativeAdTemplate(nativeAdData) {

    public override fun copy(nativeAdData: NativeAdData?): NativeAdTemplate = NativeAdDefault(nativeAdData)
    
    @Composable
    public override fun Show(
        modifier: Modifier
    ) {
        Log.i("NativeAdDefault", "Starting Show")
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        Supervisor(modifier) {
            Box(modifier = Modifier.padding(8.dp).wrapContentHeight(Alignment.Top)) {
                Column(Modifier.align(Alignment.TopStart).wrapContentHeight(Alignment.Top)) {
                    Media()
                    Attribution(text = "ad")
                    Row { nativeAdData.icon?.let { AdIcon(adIcon = it) } }
                    Column {
                        nativeAdData.headline?.let { Headline { BasicText(it) } }
                        nativeAdData.starRating?.let { BasicText("Rated $it") }
                    }

                    nativeAdData.body?.let { Body { BasicText(it) } }

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