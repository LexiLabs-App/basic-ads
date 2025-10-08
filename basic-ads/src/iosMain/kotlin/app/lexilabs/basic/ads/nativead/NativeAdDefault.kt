package app.lexilabs.basic.ads.nativead

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.uikit.LocalUIView
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import app.lexilabs.basic.ads.AdException
import cocoapods.Google_Mobile_Ads_SDK.GADAdChoicesView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController
import platform.UIKit.removeFromParentViewController
import platform.UIKit.willMoveToParentViewController

public actual open class NativeAdDefault actual constructor(
    actual override val nativeAdData: NativeAdData?
) : NativeAdTemplate(nativeAdData) {

    actual override operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate = this

    actual override fun copy(nativeAdData: NativeAdData?): NativeAdTemplate = NativeAdDefault(nativeAdData)

    @OptIn(ExperimentalForeignApi::class)
    private val nativeAdView: GADNativeAdView = GADNativeAdView.new() ?:
        throw AdException("NativeAdView null")

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalForeignApi::class)
    @Composable
    actual override fun Show(modifier: Modifier) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        val parentView = LocalUIView.current
        val nativeAdView = remember(parentView) {
            var p = parentView.superview
            while (p != null && p !is GADNativeAdView) {
                p = p.superview
            }
            p
        }
        DisposableEffect(nativeAdView, nativeAdData) {
            // By the time this effect runs, the child AndroidViews have been composed
            // and their update blocks have run, registering the views. Now it's safe
            // to associate the ad with the view.
            nativeAdView?.setNativeAd(nativeAdData!!.ios)
            onDispose {}
        }

        Box(modifier = Modifier.padding(8.dp).wrapContentHeight(Alignment.Top)) {
            Column(Modifier.align(Alignment.TopStart).wrapContentHeight(Alignment.Top)) {
                Media()
                Attribution(text = "ad")
                Row { nativeAdData!!.icon?.image?.let { Icon { it } } }
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

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Advertiser(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                // Assign the created UIView to the GADNativeAdView
                nativeAdView.advertiserView = uiView
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Body(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.bodyView = uiView
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun CallToAction(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.callToActionView = uiView
                // Make the view interactive for clicks
                nativeAdView.callToActionView?.setUserInteractionEnabled(true)
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun AdChoices(modifier: Modifier) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ViewInUIView(
            modifier = modifier,
            update = {
                nativeAdView.adChoicesView = GADAdChoicesView.new()
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Headline(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.headlineView = uiView
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Icon(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.iconView = uiView
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Media(
        modifier: Modifier,
        scaleType: ScaleType?
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ViewInUIView(
            modifier = modifier,
            update = { uiView ->
                nativeAdView.mediaView?.mediaContent = nativeAdData!!.ios.mediaContent
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Price(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.priceView = uiView
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun StarRating(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.starRatingView = uiView
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual override fun Store(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.storeView = uiView
            }
        )
    }

    @Composable
    actual override fun Attribution(text: String, modifier: Modifier) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
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
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        Box(
            modifier = modifier.background(backgroundColor, shape)
        ) {
            BasicText(color = { textColor }, text = text)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    private fun ComposeInUIView(
        content: @Composable () -> Unit,
        modifier: Modifier = Modifier,
        update: (UIView) -> Unit = {}
    ) {
        val parentController = LocalUIViewController.current

        // 1. Create and remember a UIViewController that will host the Compose content.
        val composeController = remember { ComposeUIViewController { content() } }

        // 2. Manage the lifecycle of the child view controller.
        DisposableEffect(parentController, composeController) {
            parentController.addChildViewController(composeController)
            composeController.didMoveToParentViewController(parentController)
            onDispose {
                composeController.willMoveToParentViewController(null)
                composeController.view.removeFromSuperview()
                composeController.removeFromParentViewController()
            }
        }

        // 3. Use UIKitView to get the underlying UIView and add it to the composition.
        UIKitView(
            factory = {
                // The composeController.view is the UIView that renders your content.
                composeController.view
            },
            modifier = modifier,
            update = {
                // Re-run the update block if the view needs to be reconfigured.
                update(it)
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    private fun ViewInUIView(
        modifier: Modifier = Modifier,
        update: (UIView) -> Unit = {}
    ) {
        val parentController = LocalUIViewController.current

        // 1. Create and remember a UIViewController that will host the Compose content.
        val composeController = remember { ComposeUIViewController { } }

        // 2. Manage the lifecycle of the child view controller.
        DisposableEffect(parentController, composeController) {
            parentController.addChildViewController(composeController)
            composeController.didMoveToParentViewController(parentController)
            onDispose {
                composeController.willMoveToParentViewController(null)
                composeController.view.removeFromSuperview()
                composeController.removeFromParentViewController()
            }
        }

        // 3. Use UIKitView to get the underlying UIView and add it to the composition.
        UIKitView(
            factory = {
                // The composeController.view is the UIView that renders your content.
                composeController.view
            },
            modifier = modifier,
            update = {
                // Re-run the update block if the view needs to be reconfigured.
                update(it)
            }
        )
    }
}