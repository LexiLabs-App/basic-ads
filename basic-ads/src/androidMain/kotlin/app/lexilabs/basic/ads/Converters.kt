package app.lexilabs.basic.ads

public fun AdSize.toAndroid(): com.google.android.gms.ads.AdSize =
    com.google.android.gms.ads.AdSize(this.width, this.height)

public fun com.google.android.gms.ads.AdSize.toCommon(): AdSize =
    AdSize(width = this.width, height = this.height)

@DependsOnGoogleMobileAds
public fun RequestConfiguration.toAndroid(): com.google.android.gms.ads.RequestConfiguration =
    com.google.android.gms.ads.RequestConfiguration.Builder()
        .setMaxAdContentRating(maxAdContentRating)
        .setPublisherPrivacyPersonalizationState(publisherPrivacyPersonalizationState.toAndroid())
        .setTagForChildDirectedTreatment(tagForChildDirectedTreatment)
        .setTagForUnderAgeOfConsent(tagForUnderAgeOfConsent)
        .setTestDeviceIds(testDeviceIds)
        .build()

@DependsOnGoogleMobileAds
public fun com.google.android.gms.ads.RequestConfiguration.toCommon(): RequestConfiguration =
    RequestConfiguration(
        maxAdContentRating = maxAdContentRating,
        publisherPrivacyPersonalizationState = publisherPrivacyPersonalizationState.toCommon(),
        tagForChildDirectedTreatment = tagForChildDirectedTreatment,
        tagForUnderAgeOfConsent = tagForUnderAgeOfConsent,
        testDeviceIds = testDeviceIds
    )

@DependsOnGoogleMobileAds
public fun RequestConfiguration.PublisherPrivacyPersonalizationState.toAndroid(
): com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState =
    com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState.valueOf(this.name)

@DependsOnGoogleMobileAds
public fun com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState.toCommon(
): RequestConfiguration.PublisherPrivacyPersonalizationState =
    RequestConfiguration.PublisherPrivacyPersonalizationState.fromInt(this.ordinal)