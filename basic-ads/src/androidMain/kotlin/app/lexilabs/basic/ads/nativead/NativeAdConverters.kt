package app.lexilabs.basic.ads.nativead

import com.google.android.gms.ads.MediaContent
import com.google.android.gms.ads.MuteThisAdReason
import com.google.android.gms.ads.nativead.NativeAd

public fun NativeAd.AdChoicesInfo.toCommon(): NativeAdData.AdChoicesInfo {
    return NativeAdData.AdChoicesInfo(
        adIcons = this.images.map { it.toCommon() },
        text = this.text
    )
}

public fun NativeAd.Image.toCommon(): NativeAdData.AdIcon {
    return NativeAdData.AdIcon(
        drawable = this.drawable,
        scale = this.scale,
        uri = this.uri
    )
}

public fun MediaContent.toCommon(): NativeAdData.MediaContent {
    return NativeAdData.MediaContent(
        aspectRatio = this.aspectRatio,
        currentTime = this.currentTime,
        duration = this.duration,
        mainImage = this.mainImage,
        videoController = this.videoController,
    )
}

public fun MuteThisAdReason.toCommon(): NativeAdData.MuteThisAdReason {
    return NativeAdData.MuteThisAdReason(
        description = this.description
    )
}