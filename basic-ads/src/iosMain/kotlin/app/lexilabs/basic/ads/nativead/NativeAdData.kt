package app.lexilabs.basic.ads.nativead

import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADVideoController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat
import platform.Foundation.NSURL
import platform.UIKit.UIImage

@OptIn(ExperimentalForeignApi::class)
public actual class NativeAdData(
    public val ios: GADNativeAd
) {
    public actual val adChoicesInfo: AdChoicesInfo? = null
    public actual val advertiser: String?
        get() = ios.advertiser
    public actual val body: String?
        get() = ios.body
    public actual val callToAction: String?
        get() = ios.callToAction
    public actual val headline: String?
        get() = ios.headline
    public actual val icon: Image?
        get() = ios.icon?.toCommon()
    public actual val mediaContent: MediaContent?
        get() = ios.mediaContent.toCommon()
    public actual val muteThisAdReasons: List<MuteThisAdReason>
        get() = ios.muteThisAdReasons?.map { it as MuteThisAdReason } ?: emptyList()
    public actual val placementId: Long?
        get() = ios.placementID
    public actual val price: String?
        get() = ios.price
    public actual val starRating: Double?
        get() = ios.starRating?.doubleValue
    public actual val store: String?
        get() = ios.store
    public actual class AdChoicesInfo (
        public val images: List<Image>,
        public val text: CharSequence
    )
    public actual class Image (
        public val image: UIImage?,
        public val scale: CGFloat,
        public val imageUrl: NSURL?
    )
    public actual class MediaContent(
        public val aspectRatio: Double,
        public val currentTime: Double,
        public val duration: Double,
        public val mainImage: UIImage?,
        public val videoController: GADVideoController,
    )
    public actual class MuteThisAdReason(
        public val description: String
    )

}