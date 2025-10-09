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

/**
 * A default implementation of [NativeAdTemplate] for iOS.
 *
 * @param nativeAdData The data for the native ad.
 */
public actual class NativeAdDefault actual constructor(
    actual override val nativeAdData: NativeAdData?
) : NativeAdTemplate(nativeAdData) {

    /**
     * Returns this instance of [NativeAdDefault].
     *
     * @param nativeAdData The data for the native ad.
     * @return This instance of [NativeAdDefault].
     */
    actual override operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate = this

    /**
     * Creates a new instance of [NativeAdDefault] with the given [nativeAdData].
     *
     * @param nativeAdData The data for the native ad.
     * @return A new instance of [NativeAdDefault].
     */
    actual override fun copy(nativeAdData: NativeAdData?): NativeAdTemplate = NativeAdDefault(nativeAdData)

    @OptIn(ExperimentalForeignApi::class)
    private val nativeAdView: GADNativeAdView = GADNativeAdView.new() ?:
        throw AdException("NativeAdView null")

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

    /**
     * A supervisor for the native ad.
     *
     * @param modifier The modifier to be applied to the supervisor.
     * @param content The content of the supervisor.
     */
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalForeignApi::class)
    @Composable
    public actual fun Supervisor(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
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
            nativeAdView?.setNativeAd(nativeAdData.ios)
            onDispose {}
        }
        content()
    }

    /**
     * The advertiser view for the native ad.
     *
     * @param modifier The modifier to be applied to the advertiser view.
     * @param content The content of the advertiser view.
     */
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

    /**
     * The body view for the native ad.
     *
     * @param modifier The modifier to be applied to the body view.
     * @param content The content of the body view.
     */
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

    /**
     * The call to action view for the native ad.
     *
     * @param modifier The modifier to be applied to the call to action view.
     * @param content The content of the call to action view.
     */
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

    /**
     * The ad choices view for the native ad.
     *
     * @param modifier The modifier to be applied to the ad choices view.
     */
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

    /**
     * The headline view for the native ad.
     *
     * @param modifier The modifier to be applied to the headline view.
     * @param content The content of the headline view.
     */
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

    /**
     * The icon view for the native ad.
     *
     * @param modifier The modifier to be applied to the icon view.
     * @param content The content of the icon view.
     */
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

    /**
     * The media view for the native ad.
     *
     * @param modifier The modifier to be applied to the media view.
     * @param scaleType The scale type for the media view.
     */
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
                nativeAdView.mediaView?.mediaContent = nativeAdData.ios.mediaContent
                scaleType?.let { nativeAdView.mediaView?.contentMode = it.toIos() }
            }
        )
    }

    /**
     * The price view for the native ad.
     *
     * @param modifier The modifier to be applied to the price view.
     * @param content The content of the price view.
     */
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

    /**
     * The star rating view for the native ad.
     *
     * @param modifier The modifier to be applied to the star rating view.
     * @param content The content of the star rating view.
     */
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

    /**
     * The store view for the native ad.
     *
     * @param modifier The modifier to be applied to the store view.
     * @param content The content of the store view.
     */
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

    /**
     * The attribution for the native ad.
     *
     * @param text The text to be displayed in the attribution.
     * @param modifier The modifier to be applied to the attribution.
     */
    @Composable
    actual override fun Attribution(text: String, modifier: Modifier) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        Box(
            modifier = modifier.background(Color.Yellow).clip(RectangleShape)
        ) {
            BasicText(color = { Color.White }, text = text)
        }
    }

    /**
     * A button for the native ad.
     *
     * @param text The text to be displayed on the button.
     * @param modifier The modifier to be applied to the button.
     * @param textColor The color of the text.
     * @param backgroundColor The color of the background.
     * @param shape The shape of the button.
     */
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

    /**
     * A composable that embeds a Compose view in a UIView.
     *
     * @param content The content to be embedded.
     * @param modifier The modifier to be applied to the view.
     * @param update A callback to be invoked when the view is updated.
     */
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

    /**
     * A composable that embeds a UIView in a Compose view.
     *
     * @param modifier The modifier to be applied to the view.
     * @param update A callback to be invoked when the view is updated.
     */
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
