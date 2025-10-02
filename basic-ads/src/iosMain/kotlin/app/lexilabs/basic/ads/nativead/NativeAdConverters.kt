package app.lexilabs.basic.ads.nativead

import cocoapods.Google_Mobile_Ads_SDK.GADMediaContent
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdImage
import cocoapods.Google_Mobile_Ads_SDK.mainImage
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
public fun GADNativeAd.toCommon(): NativeAdData {
    return NativeAdData(
        adChoicesInfo = null,
        advertiser = this.advertiser,
        body = this.body,
        callToAction = this.callToAction,
        headline = this.headline,
        icon = this.icon?.toCommon(),
        mediaContent = this.mediaContent.toCommon(),
        // The weird cast below is probably the issue if you're troubleshooting on iOS
        muteThisAdReasons = this.muteThisAdReasons?.map{ it as NativeAdData.MuteThisAdReason } ?: emptyList(),
        placementId = null,
        price = this.price,
        starRating = this.starRating?.doubleValue,
        store = this.store
    )
}

@OptIn(ExperimentalForeignApi::class)
public fun GADNativeAdImage.toCommon(): NativeAdData.Image {
    return NativeAdData.Image(
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