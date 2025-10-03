package app.lexilabs.basic.ads.nativead

import cocoapods.Google_Mobile_Ads_SDK.GADVideoController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat
import platform.Foundation.NSURL
import platform.UIKit.UIImage

public actual class NativeAdData (
    public actual val adChoicesInfo: AdChoicesInfo?,
    public actual val advertiser: String?,
    public actual val body: String?,
    public actual val callToAction: String?,
    public actual val headline: String?,
    public actual val icon: Image?,
    public actual val mediaContent: MediaContent?,
    public actual val muteThisAdReasons: List<MuteThisAdReason>,
    public actual val placementId: Long?,
    public actual val price: String?,
    public actual val starRating: Double?,
    public actual val store: String?
) {
    public actual class AdChoicesInfo (
        public val images: List<Image>,
        public val text: CharSequence
    )
    public actual class Image (
        public val image: UIImage?,
        public val scale: CGFloat,
        public val imageUrl: NSURL?
    )
    public actual class MediaContent @OptIn(ExperimentalForeignApi::class) constructor(
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