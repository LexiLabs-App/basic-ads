/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.lexilabs.basic.ads.nativead

import android.view.View
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.nativead.AdChoicesView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView

public actual open class NativeAdDefault actual constructor(
    actual override val nativeAdData: NativeAdData?,
) : NativeAdTemplate(nativeAdData) {

    actual override operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate = this

    actual override fun copy(nativeAdData: NativeAdData?): NativeAdTemplate = NativeAdDefault(nativeAdData)

    @Composable
    actual override fun Show(
        modifier: Modifier
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val parentView = LocalView.current
        val nativeAdView = remember(parentView) {
            var p = parentView.parent
            while (p != null && p !is NativeAdView) {
                p = p.parent
            }
            p
        }

        DisposableEffect(nativeAdView, nativeAdData) {
            // By the time this effect runs, the child AndroidViews have been composed
            // and their update blocks have run, registering the views. Now it's safe
            // to associate the ad with the view.
            nativeAdView?.setNativeAd(nativeAdData!!.android)
            onDispose {}
        }

        Box(modifier = Modifier.padding(8.dp).wrapContentHeight(Alignment.Top)) {
            Column(Modifier.align(Alignment.TopStart).wrapContentHeight(Alignment.Top)) {
                Media()
                Attribution(text = "ad")
                Row {
                    nativeAdData!!.icon?.drawable?.let {
                        Icon { Image(bitmap = it.toBitmap().asImageBitmap(), "Icon") }
                    }
                }
                Column {
                    nativeAdData!!.headline?.let { Headline { BasicText(it) } }
                    nativeAdData!!.starRating?.let { BasicText("Rated $it") }
                }

                nativeAdData!!.body?.let { Body { BasicText(it) } }

                Row(Modifier.align(Alignment.End).padding(5.dp)) {
                    nativeAdData!!.price?.let { BasicText(it)}
                    nativeAdData!!.store?.let { BasicText(it)}
                    nativeAdData!!.callToAction?.let {
                        NativeAdButton(it)
                    }
                }
            }
        }
    }

    @Composable
    actual override fun Advertiser(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        AndroidView(
            factory = { context ->
                ComposeView(context).apply {
                    id = View.generateViewId()
                    setContent(content)
                    nativeAdView.advertiserView = this
                }
            },
            modifier = modifier,
            update = { view -> view.setContent(content) },
        )
    }

    @Composable
    actual override fun Body(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.bodyView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun CallToAction(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.callToActionView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun AdChoices(modifier: Modifier) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        AndroidView(
            factory = {
                AdChoicesView(localContext).apply {
                    minimumWidth = 15
                    minimumHeight = 15
                }
            },
            update = { view -> nativeAdView.adChoicesView = view },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun Headline(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.headlineView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun Icon(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.iconView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun Media(
        modifier: Modifier,
        scaleType: ScaleType?
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        AndroidView(
            factory = { MediaView(localContext) },
            update = { view ->
                nativeAdView.mediaView = view
                scaleType?.let { type -> view.setImageScaleType(ImageView.ScaleType.valueOf(type.name)) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun Price(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.priceView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun StarRating(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.starRatingView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun Store(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.storeView = localComposeView
                localComposeView.apply { setContent(content) }
            },
            modifier = modifier,
        )
    }

    @Composable
    actual override fun Attribution(text: String, modifier: Modifier) {
        Box(
            modifier = modifier.background(Color.Yellow).clip(RectangleShape)
        ) {
            BasicText(color = { Color.White }, text = text)
        }
    }

    @Composable
    public fun NativeAdButton(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = Color.White,
        backgroundColor: Color = Color.Black,
        shape: Shape = RectangleShape
    ) {
        Box(
            modifier = modifier.background(backgroundColor, shape)
        ) {
            BasicText(color = { textColor }, text = text)
        }
    }
}