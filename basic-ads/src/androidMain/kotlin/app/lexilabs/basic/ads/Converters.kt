package app.lexilabs.basic.ads

/**
 * Converts a common [AdSize] to a [com.google.android.gms.ads.AdSize].
 */
public fun AdSize.toAndroid(): com.google.android.gms.ads.AdSize =
    com.google.android.gms.ads.AdSize(this.width, this.height)

/**
 * Converts a [com.google.android.gms.ads.AdSize] to a common [AdSize].
 */
public fun com.google.android.gms.ads.AdSize.toCommon(): AdSize =
    AdSize(width = this.width, height = this.height)

/**
 * Converts a common [RequestConfiguration] to a [com.google.android.gms.ads.RequestConfiguration].
 */
@DependsOnGoogleMobileAds
public fun RequestConfiguration.toAndroid(): com.google.android.gms.ads.RequestConfiguration =
    com.google.android.gms.ads.RequestConfiguration.Builder()
        .setMaxAdContentRating(maxAdContentRating)
        .setPublisherPrivacyPersonalizationState(publisherPrivacyPersonalizationState.toAndroid())
        .setTagForChildDirectedTreatment(tagForChildDirectedTreatment)
        .setTagForUnderAgeOfConsent(tagForUnderAgeOfConsent)
        .setTestDeviceIds(testDeviceIds)
        .build()

/**
 * Converts a [com.google.android.gms.ads.RequestConfiguration] to a common [RequestConfiguration].
 */
@DependsOnGoogleMobileAds
public fun com.google.android.gms.ads.RequestConfiguration.toCommon(): RequestConfiguration =
    RequestConfiguration(
        maxAdContentRating = maxAdContentRating,
        publisherPrivacyPersonalizationState = publisherPrivacyPersonalizationState.toCommon(),
        tagForChildDirectedTreatment = tagForChildDirectedTreatment,
        tagForUnderAgeOfConsent = tagForUnderAgeOfConsent,
        testDeviceIds = testDeviceIds
    )

/**
 * Converts a common [RequestConfiguration.PublisherPrivacyPersonalizationState] to a [com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState].
 */
@DependsOnGoogleMobileAds
public fun RequestConfiguration.PublisherPrivacyPersonalizationState.toAndroid(
): com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState =
    com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState.valueOf(this.name)

/**
 * Converts a [com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState] to a common [RequestConfiguration.PublisherPrivacyPersonalizationState].
 */
@DependsOnGoogleMobileAds
public fun com.google.android.gms.ads.RequestConfiguration.PublisherPrivacyPersonalizationState.toCommon(
): RequestConfiguration.PublisherPrivacyPersonalizationState =
    RequestConfiguration.PublisherPrivacyPersonalizationState.fromInt(this.ordinal)