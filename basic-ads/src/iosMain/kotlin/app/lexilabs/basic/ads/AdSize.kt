package app.lexilabs.basic.ads

import cocoapods.Google_Mobile_Ads_SDK.GADAdSize
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeBanner
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeFluid
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeFullBanner
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeInvalid
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeLargeBanner
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeLeaderboard
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeMediumRectangle
import cocoapods.Google_Mobile_Ads_SDK.GADAdSizeSkyscraper
import cocoapods.Google_Mobile_Ads_SDK.GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth
import cocoapods.Google_Mobile_Ads_SDK.GADCurrentOrientationInlineAdaptiveBannerAdSizeWithWidth
import cocoapods.Google_Mobile_Ads_SDK.GADInlineAdaptiveBannerAdSizeWithWidthAndMaxHeight
import cocoapods.Google_Mobile_Ads_SDK.GADLandscapeAnchoredAdaptiveBannerAdSizeWithWidth
import cocoapods.Google_Mobile_Ads_SDK.GADLandscapeInlineAdaptiveBannerAdSizeWithWidth
import cocoapods.Google_Mobile_Ads_SDK.GADPortraitAnchoredAdaptiveBannerAdSizeWithWidth
import cocoapods.Google_Mobile_Ads_SDK.GADPortraitInlineAdaptiveBannerAdSizeWithWidth
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIScreen

/**
 * The iOS implementation of [AdSize].
 */
@OptIn(ExperimentalForeignApi::class)
public actual class AdSize actual constructor(public actual val width: Int, public actual val height: Int) {

    public actual companion object {
        public actual val FULL_WIDTH: Int get() = UIScreen.mainScreen.bounds.width()
        public actual val AUTO_HEIGHT: Int get() = GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(this.FULL_WIDTH.toDouble()).toAdSize().height
        public actual val BANNER: AdSize get() = GADAdSizeBanner.toCommon()
        public actual val FULL_BANNER: AdSize get() = GADAdSizeFullBanner.toCommon()
        public actual val LARGE_BANNER: AdSize get() = GADAdSizeLargeBanner.toCommon()
        public actual val LEADERBOARD: AdSize get() = GADAdSizeLeaderboard.toCommon()
        public actual val MEDIUM_RECTANGLE: AdSize get() = GADAdSizeMediumRectangle.toCommon()
        public actual val WIDE_SKYSCRAPER: AdSize get() = GADAdSizeSkyscraper.toCommon()
        public actual val FLUID: AdSize get() = GADAdSizeFluid.toCommon()
        public actual val INVALID: AdSize get() = GADAdSizeInvalid.toCommon()

        public actual fun autoSelect(androidAdSize: AdSize, iosAdSize: AdSize): AdSize = iosAdSize

        public actual fun getCurrentOrientationAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize =
            GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(width.toDouble()).toAdSize()

        public actual fun getPortraitAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize =
            GADPortraitAnchoredAdaptiveBannerAdSizeWithWidth(width.toDouble()).toAdSize()

        public actual fun getLandscapeAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize =
            GADLandscapeAnchoredAdaptiveBannerAdSizeWithWidth(width.toDouble()).toAdSize()

        public actual fun getCurrentOrientationInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize =
            GADCurrentOrientationInlineAdaptiveBannerAdSizeWithWidth(width.toDouble()).toAdSize()

        public actual fun getPortraitInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize =
            GADPortraitInlineAdaptiveBannerAdSizeWithWidth(width.toDouble()).toAdSize()

        public actual fun getLandscapeInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize =
            GADLandscapeInlineAdaptiveBannerAdSizeWithWidth(width.toDouble()).toAdSize()

        public actual fun getInlineAdaptiveBannerAdSize(width: Int, maxHeight: Int): AdSize =
            GADInlineAdaptiveBannerAdSizeWithWidthAndMaxHeight(width.toDouble(), maxHeight.toDouble()).toAdSize()
    }

    /**
     * Converts this common [AdSize] to an iOS-specific [GADAdSize].
     */
    public fun toIos(): GADAdSize {
        return when(this) {
            BANNER -> GADAdSizeBanner
            FULL_BANNER -> GADAdSizeFullBanner
            LARGE_BANNER -> GADAdSizeLargeBanner
            LEADERBOARD -> GADAdSizeLeaderboard
            MEDIUM_RECTANGLE -> GADAdSizeMediumRectangle
            WIDE_SKYSCRAPER -> GADAdSizeSkyscraper
            FLUID -> GADAdSizeFluid
            INVALID -> GADAdSizeInvalid
            else -> GADAdSizeFluid
        }
    }
}

/**
 * Converts an iOS-specific [GADAdSize] to a common [AdSize].
 */
@OptIn(ExperimentalForeignApi::class)
public fun GADAdSize.toCommon(): AdSize =
    AdSize(
        width = this.size.width.toInt(),
        height = this.size.height.toInt()
    )

/**
 * Converts an iOS-specific [GADAdSize] to a [CValue] of [CGRect].
 */
@OptIn(ExperimentalForeignApi::class)
public fun GADAdSize.toCGRectCValue(): CValue<CGRect> {
    return CGRectMake(
        x = 0.0,
        y = 0.0,
        width = this.size.width,
        height = this.size.height
    )
}

/**
 * Converts a common [AdSize] to a [CValue] of [CGRect].
 */
@OptIn(ExperimentalForeignApi::class)
public fun AdSize.toCGRectCValue(): CValue<CGRect> {
    return CGRectMake(
        x = 0.0,
        y = 0.0,
        width = this.width.toDouble(),
        height = this.height.toDouble()
    )
}

/**
 * Returns the width of the [CGRect].
 */
@OptIn(ExperimentalForeignApi::class)
public fun CValue<CGRect>.width(): Int = this.useContents { return size.width.toInt() }

/**
 * Returns the height of the [CGRect].
 */
@OptIn(ExperimentalForeignApi::class)
public fun CValue<CGRect>.height(): Int = this.useContents { return size.height.toInt() }

/**
 * Converts a [CValue] of [CGRect] to a common [AdSize].
 */
@OptIn(ExperimentalForeignApi::class)
public fun CValue<CGRect>.toAdSize(): AdSize {
    this.useContents {
        return AdSize(
            width = size.width.toInt(),
            height = size.height.toInt()
        )
    }
}

/**
 * Converts a [CValue] of [GADAdSize] to a common [AdSize].
 */
@OptIn(ExperimentalForeignApi::class)
public fun CValue<GADAdSize>.toAdSize(): AdSize {
    this.useContents {
        return AdSize(
            width = size.width.toInt(),
            height = size.width.toInt()
        )
    }
}