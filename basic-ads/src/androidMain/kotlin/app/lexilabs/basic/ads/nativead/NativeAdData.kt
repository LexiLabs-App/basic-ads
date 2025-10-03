package app.lexilabs.basic.ads.nativead

import android.graphics.drawable.Drawable
import android.net.Uri
import com.google.android.gms.ads.VideoController

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