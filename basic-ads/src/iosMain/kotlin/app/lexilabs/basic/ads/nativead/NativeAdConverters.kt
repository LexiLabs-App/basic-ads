package app.lexilabs.basic.ads.nativead

import swiftPMImport.app.lexilabs.basic.basic.ads.GADMediaContent
import swiftPMImport.app.lexilabs.basic.basic.ads.GADNativeAdImage
import swiftPMImport.app.lexilabs.basic.basic.ads.mainImage
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
public fun GADNativeAdImage.toCommon(): NativeAdData.AdIcon {
    return NativeAdData.AdIcon(
        image = this.image,
        scale = this.scale,
        imageUrl = this.imageURL
    )
}

@OptIn(ExperimentalForeignApi::class)
public fun GADMediaContent.toCommon(): NativeAdData.MediaContent {
    return NativeAdData.MediaContent(
        aspectRatio = this.aspectRatio,
        currentTime = this.currentTime,
        duration = this.duration,
        mainImage = this.mainImage,
        videoController = this.videoController
    )
}