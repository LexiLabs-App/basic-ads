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
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake

@OptIn(ExperimentalForeignApi::class)
public actual class AdSize actual constructor(public actual val width: Int, public actual val height: Int) {

    public actual companion object {
        public actual val FULL_WIDTH: Int get() = -1 // Value from Android implementation
        public actual val AUTO_HEIGHT: Int get() = -2 // Value from Android implementation
        public actual val BANNER: AdSize get() = GADAdSizeBanner.toCommon()
        public actual val FULL_BANNER: AdSize get() = GADAdSizeFullBanner.toCommon()
        public actual val LARGE_BANNER: AdSize get() = GADAdSizeLargeBanner.toCommon()
        public actual val LEADERBOARD: AdSize get() = GADAdSizeLeaderboard.toCommon()
        public actual val MEDIUM_RECTANGLE: AdSize get() = GADAdSizeMediumRectangle.toCommon()
        public actual val WIDE_SKYSCRAPER: AdSize get() = GADAdSizeSkyscraper.toCommon()
        public actual val FLUID: AdSize get() = GADAdSizeFluid.toCommon()
        public actual val INVALID: AdSize get() = GADAdSizeInvalid.toCommon()
    }
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

@OptIn(ExperimentalForeignApi::class)
public fun GADAdSize.toCommon(): AdSize =
    AdSize(
        width = this.size.width.toInt(),
        height = this.size.height.toInt()
    )

@OptIn(ExperimentalForeignApi::class)
public fun GADAdSize.toCGRectCValue(): CValue<CGRect> {
    return CGRectMake(
        x = 0.0,
        y = 0.0,
        width = this.size.width,
        height = this.size.height
    )
}

@OptIn(ExperimentalForeignApi::class)
public fun AdSize.toCGRectCValue(): CValue<CGRect> {
    return CGRectMake(
        x = 0.0,
        y = 0.0,
        width = this.width.toDouble(),
        height = this.height.toDouble()
    )
}