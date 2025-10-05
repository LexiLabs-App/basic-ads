package app.lexilabs.basic.ads.nativead

import android.graphics.drawable.Drawable
import android.net.Uri
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.nativead.NativeAd

public actual class NativeAdData (
    public val android: NativeAd
) {
    public actual val adChoicesInfo: AdChoicesInfo?
        get() = android.adChoicesInfo?.toCommon()
    public actual val advertiser: String?
        get() = android.advertiser
    public actual val body: String?
        get() = android.body
    public actual val callToAction: String?
        get() = android.callToAction
    public actual val headline: String?
        get() = android.headline
    public actual val icon: Image?
        get() = android.icon?.toCommon()
    public actual val mediaContent: MediaContent?
        get() = android.mediaContent?.toCommon()
    public actual val muteThisAdReasons: List<MuteThisAdReason>
        get() = android.muteThisAdReasons.map { it.toCommon() }
    public actual val placementId: Long?
        get() = android.placementId
    public actual val price: String?
        get() = android.price
    public actual val starRating: Double?
        get() = android.starRating
    public actual val store: String?
        get() = android.store
    public actual class AdChoicesInfo (
        public val images: List<Image>,
        public val text: CharSequence
    )
    public actual class Image (
        public val drawable: Drawable?,
        public val scale: Double,
        public val uri: Uri?
    )
    public actual class MediaContent(
        public val aspectRatio: Float,
        public val currentTime: Float,
        public val duration: Float,
        public val mainImage: Drawable?,
        public val videoController: VideoController,
    )
    public actual class MuteThisAdReason(
        public val description: String
    )
}