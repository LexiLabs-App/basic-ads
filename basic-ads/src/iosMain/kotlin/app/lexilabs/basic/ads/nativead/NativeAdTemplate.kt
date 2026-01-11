@file:OptIn(ExperimentalForeignApi::class)
package app.lexilabs.basic.ads.nativead

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.uikit.LocalUIView
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import app.lexilabs.basic.ads.AdException
import app.lexilabs.basic.ads.ExperimentalBasicAdsFeature
import cocoapods.Google_Mobile_Ads_SDK.GADAdChoicesView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController
import platform.UIKit.removeFromParentViewController
import platform.UIKit.willMoveToParentViewController

@ExperimentalBasicAdsFeature
public actual abstract class NativeAdTemplate public actual constructor(
    public actual override val nativeAdData: NativeAdData?
): NativeAdScope {

    public actual operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate = this

    public actual abstract fun copy(nativeAdData: NativeAdData?): NativeAdTemplate

    @OptIn(ExperimentalForeignApi::class)
    private val nativeAdView: GADNativeAdView = GADNativeAdView.new() ?:
        throw AdException("NativeAdView null")

    @Composable
    public actual abstract fun Show(modifier: Modifier)

    public actual interface SupervisorScope

    internal actual object SupervisorScopeInstance: SupervisorScope

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalForeignApi::class)
    @Composable
    public actual fun Supervisor(
        modifier: Modifier,
        content: @Composable SupervisorScope.() -> Unit
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
            nativeAdView?.setNativeAd(nativeAdData!!.ios)
            onDispose {}
        }
        SupervisorScopeInstance.content()
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    public actual fun SupervisorScope.AdChoices(
        modifier: Modifier
    ) {
        ViewInUIView(
            modifier = modifier,
            update = {
                nativeAdView.adChoicesView = GADAdChoicesView.new()
            }
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    public actual fun SupervisorScope.Advertiser(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                // Assign the created UIView to the GADNativeAdView
                nativeAdView.advertiserView = uiView
            }
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

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    public actual fun SupervisorScope.Body(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.bodyView = uiView
            }
        )
    }

    @Composable
    public actual fun SupervisorScope.CallToAction(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
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

    @Composable
    public actual fun SupervisorScope.Headline(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.headlineView = uiView
            }
        )
    }

    @Composable
    public actual fun SupervisorScope.AdIcon(
        modifier: Modifier,
        adIcon: NativeAdData.AdIcon
    ) {
        adIcon.image?.let {
            ComposeInUIView(
                content = { it },
                modifier = modifier,
                update = { uiView ->
                    nativeAdView.iconView = uiView
                }
            )
        }
    }

    @Composable
    public actual fun SupervisorScope.Media(
        modifier: Modifier,
        scaleType: ScaleType?
    ) {
        ViewInUIView(
            modifier = modifier,
            update = { _ ->
                nativeAdView.mediaView?.mediaContent = nativeAdData?.ios?.mediaContent
                scaleType?.let { nativeAdView.mediaView?.contentMode = it.toIos() }
            }
        )
    }

    @Composable
    public actual fun SupervisorScope.Price(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.priceView = uiView
            }
        )
    }

    @Composable
    public actual fun SupervisorScope.StarRating(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.starRatingView = uiView
            }
        )
    }

    @Composable
    public actual fun SupervisorScope.Store(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        ComposeInUIView(
            content = content,
            modifier = modifier,
            update = { uiView ->
                nativeAdView.storeView = uiView
            }
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