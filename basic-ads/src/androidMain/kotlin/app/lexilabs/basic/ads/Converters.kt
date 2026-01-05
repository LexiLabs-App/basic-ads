package app.lexilabs.basic.ads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

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

/**
 * Recursively unwraps this [Context] to find the underlying [Activity].
 *
 * In Android, a [Context] is frequently wrapped in multiple layers of [ContextWrapper]s
 * (e.g., for Themes, Hilt/Dagger, or Jetpack Compose). Attempting to cast a
 * wrapped context directly to [Activity] can cause a [ClassCastException].
 *
 * This function traverses the `baseContext` chain until it finds the actual [Activity]
 * or reaches the end of the chain.
 *
 * @return The underlying [Activity] if found, or `null` if the context is not attached to an activity.
 */
public fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}