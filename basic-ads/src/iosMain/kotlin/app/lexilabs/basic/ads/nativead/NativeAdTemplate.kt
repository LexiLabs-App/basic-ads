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
import androidx.compose.ui.viewinterop.UIKitView
import app.lexilabs.basic.ads.AdException
import app.lexilabs.basic.ads.ExperimentalBasicAdsFeature
import cocoapods.Google_Mobile_Ads_SDK.GADAdChoicesView
import cocoapods.Google_Mobile_Ads_SDK.GADMediaContent
import cocoapods.Google_Mobile_Ads_SDK.GADMediaView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIButton
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIImageView
import platform.UIKit.UILabel

@ExperimentalBasicAdsFeature
public actual abstract class NativeAdTemplate public actual constructor(
    public actual override val nativeAdData: NativeAdData?
): NativeAdScope {

    public actual operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate = this

    public actual abstract fun copy(nativeAdData: NativeAdData?): NativeAdTemplate

    @OptIn(ExperimentalForeignApi::class)
    internal val nativeAdView: GADNativeAdView = GADNativeAdView.new() ?: throw AdException("GADNativeAdView is null")

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
        UIKitView(
            factory = {
                GADAdChoicesView.new() ?: throw AdException("GADAdChoicesView is null")
            },
            update = { adChoices ->
                nativeAdView.adChoicesView = adChoices
            },
            modifier = modifier
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    public actual fun SupervisorScope.Advertiser(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        UIKitView(
            factory = {
                UILabel.new() ?: throw AdException("UILabel is null")
            },
            update = { advertiser ->
                if (nativeAdData?.advertiser.isNullOrBlank()) {
                    advertiser.hidden = true
                } else {
                    advertiser.text = nativeAdData?.advertiser
                }
                nativeAdView.advertiserView = advertiser
            },
            modifier = modifier
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
        UIKitView(
            factory = {
                UILabel.new() ?: throw AdException("UILabel is null")
            },
            update = { body ->
                if (nativeAdData?.body.isNullOrBlank()){
                    body.hidden = true
                } else {
                    body.text = nativeAdData?.body
                }
                nativeAdView.bodyView = body
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.CallToAction(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        UIKitView(
            factory = {
                UIButton.new() ?: throw AdException("UIButton is null")
            },
            update = { cta ->
                if (nativeAdData?.callToAction.isNullOrBlank()) {
                    cta.hidden = true
                } else {
                    cta.setTitle(
                        title = nativeAdData?.callToAction,
                        forState = UIControlStateNormal
                    )
                }
                cta.setUserInteractionEnabled(false)
                nativeAdView.callToActionView = cta
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.Headline(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        UIKitView(
            factory = {
                UILabel.new() ?: throw AdException("UILabel is null")
            },
            update = { headline ->
                if (nativeAdData?.headline.isNullOrBlank()) {
                    headline.hidden = true
                } else {
                    headline.text = nativeAdData?.headline
                }
                nativeAdView.headlineView = headline
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.AdIcon(
        modifier: Modifier,
        adIcon: NativeAdData.AdIcon
    ) {
        UIKitView(
            factory = {
                UIImageView.new() ?: throw AdException("UIImageView is null")
            },
            update = { icon ->
                if (nativeAdData?.headline.isNullOrEmpty()) {
                    icon.hidden = true
                } else {
                    icon.image = adIcon.image
                }
                nativeAdView.iconView = icon
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.Media(
        modifier: Modifier,
        scaleType: ScaleType?
    ) {
        UIKitView(
            factory = {
                GADMediaView.new() ?: throw AdException("GADMediaView is null")
            },
            update = { media ->
                if (nativeAdData?.ios?.mediaContent != null) {
                    val content: GADMediaContent = nativeAdData?.ios?.mediaContent ?: throw AdException("GADMediaContent is null")
                    media.mediaContent = content
                } else {
                    media.hidden = true
                }
                nativeAdView.mediaView = media
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.Price(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        UIKitView(
            factory = {
                UILabel.new() ?: throw AdException("UILabel is null")
            },
            update = { price ->
                if (nativeAdData?.price.isNullOrBlank()) {
                    price.hidden = true
                } else {
                    price.text = nativeAdData?.price
                }
                nativeAdView.priceView = price
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.StarRating(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        UIKitView(
            factory = {
                UILabel.new() ?: throw AdException("UILabel is null")
            },
            update = { starRating ->
                if (nativeAdData?.ios?.starRating != null) {
                    starRating.text = nativeAdData?.ios?.starRating.toString()
                } else {
                    starRating.hidden = true
                }
                nativeAdView.starRatingView = starRating
            },
            modifier = modifier
        )
    }

    @Composable
    public actual fun SupervisorScope.Store(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        UIKitView(
            factory = {
                UILabel.new() ?: throw AdException("UILabel is null")
            },
            update = { store ->
                if (nativeAdData?.store.isNullOrBlank()) {
                    store.hidden = true
                } else {
                    store.text = nativeAdData?.store
                }
                nativeAdView.storeView = store
            },
            modifier = modifier
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