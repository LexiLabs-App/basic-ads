package app.lexilabs.basic.ads.nativead

import android.view.View
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.nativead.AdChoicesView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView

public actual abstract class NativeAdTemplate public actual constructor(
    public actual override val nativeAdData: NativeAdData?
): NativeAdScope {

    public actual operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate = this

    public actual abstract fun copy(nativeAdData: NativeAdData?): NativeAdTemplate
    @Composable
    public actual abstract fun Show(modifier: Modifier)

    public actual interface SupervisorScope

    internal actual object SupervisorScopeInstance: SupervisorScope

    @Composable
    public actual fun Supervisor(
        modifier: Modifier,
        content: @Composable SupervisorScope.() -> Unit
    ) {
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

        SupervisorScopeInstance.content()
    }

    @Composable
    public actual fun SupervisorScope.AdChoices(
        modifier: Modifier
    ) {
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
    public actual fun SupervisorScope.Advertiser(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        AndroidView(
            factory = { context ->
                ComposeView(context).apply {
                    id = View.generateViewId()
                    nativeAdView.advertiserView = this
                }
            },
            modifier = modifier,
        )
    }

    @Composable
    public actual fun SupervisorScope.Attribution(
        text: String,
        modifier: Modifier
    ) {
        Box(
            modifier = modifier.background(Color.Yellow).clip(RectangleShape)
        ) {
            BasicText(color = { Color.White }, text = text)
        }
    }

    @Composable
    public actual fun SupervisorScope.Body(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.bodyView = localComposeView
                localComposeView
            },
            modifier = modifier,
        )
    }

    @Composable
    public actual fun SupervisorScope.CallToAction(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.callToActionView = localComposeView
                localComposeView
            },
            modifier = modifier,
        )
    }

    @Composable
    public actual fun SupervisorScope.Headline(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.headlineView = localComposeView
                localComposeView
            },
            modifier = modifier,
        )
    }

    @Composable
    public actual fun SupervisorScope.Icon(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        nativeAdData?.android?.icon?.drawable?.let { image ->
            AndroidView(
                factory = {
                    nativeAdView.iconView = localComposeView
                    localComposeView.apply {
                        setContent {
                            Image(bitmap = image.toBitmap().asImageBitmap(), "Icon")
                        }
                    }
                },
                modifier = modifier,
            )
        }
    }

    @Composable
    public actual fun SupervisorScope.Media(
        modifier: Modifier,
        scaleType: ScaleType?
    ) {
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
    public actual fun SupervisorScope.Price(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.priceView = localComposeView
                localComposeView
            },
            modifier = modifier,
        )
    }

    @Composable
    public actual fun SupervisorScope.StarRating(
        modifier: Modifier,
        stars: @Composable (Double) -> Unit
    ) {
        nativeAdData?.starRating?.let{ stars(it) }
    }

    @Composable
    public actual fun SupervisorScope.Store(
        modifier: Modifier
    ) {
        val nativeAdView = LocalNativeAdView.current ?: throw IllegalStateException("NativeAdView null")
        val localContext = LocalContext.current
        val localComposeView = remember { ComposeView(localContext).apply { id = View.generateViewId() } }
        AndroidView(
            factory = {
                nativeAdView.storeView = localComposeView
                localComposeView
            },
            modifier = modifier,
        )
    }

    @Composable
    public actual fun SupervisorScope.NativeAdButton(
        text: String,
        modifier: Modifier,
        textColor: Color,
        backgroundColor: Color,
        shape: Shape
    ) {
        Box(
            modifier = modifier.background(backgroundColor, shape)
        ) {
            BasicText(color = { textColor }, text = text)
        }
    }
}