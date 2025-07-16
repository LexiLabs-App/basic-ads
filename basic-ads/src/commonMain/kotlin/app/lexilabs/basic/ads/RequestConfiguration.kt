package app.lexilabs.basic.ads

/**
 * Sets the initial configuration of your AdMob instance for Android and iOS
 * @param maxAdContentRating The maximum content rating of the ads you wish to display
 * @param publisherPrivacyPersonalizationState The privacy level of the ads
 * @param tagForChildDirectedTreatment Should ads be appropriate for children
 * @param tagForUnderAgeOfConsent Should ads be limited to content for children under the age of consent
 * @param testDeviceIds A [List] of id [String] for testing devices
 * @see BasicAds.initialize
 * @see BasicAds.configuration
 * @see DependsOnGoogleMobileAds
 */
@DependsOnGoogleMobileAds
public data class RequestConfiguration(
    val maxAdContentRating: String?,
    val publisherPrivacyPersonalizationState: PublisherPrivacyPersonalizationState,
    val tagForChildDirectedTreatment: Int,
    val tagForUnderAgeOfConsent: Int,
    val testDeviceIds: List<String?>?
){
    /**
     * Enum for the state of publisher privacy personalization.
     * @property DEFAULT The default state, which may be determined by other settings.
     * @property ENABLED Publisher has opted to enable personalized advertising.
     * @property DISABLED Publisher has opted to disable personalized advertising.
     */
    public enum class PublisherPrivacyPersonalizationState(private val value: Int) {
        DEFAULT(0),
        ENABLED(1),
        DISABLED(2);

        public companion object {
            public fun fromInt(value: Int): PublisherPrivacyPersonalizationState =
                PublisherPrivacyPersonalizationState.entries.find { it.value == value } ?: DEFAULT
        }
    }

    public companion object {
        public const val TAG_FOR_CHILD_DIRECTED_TREATMENT_UNSPECIFIED: Int = -1
        public const val TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE: Int = 0
        public const val TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE: Int = 1
        public const val TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE: Int = 1
        public const val TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE: Int = 0
        public const val TAG_FOR_UNDER_AGE_OF_CONSENT_UNSPECIFIED: Int = -1
        public const val MAX_AD_CONTENT_RATING_UNSPECIFIED: String = ""
        public const val MAX_AD_CONTENT_RATING_G: String = "G"
        public const val MAX_AD_CONTENT_RATING_PG: String = "PG"
        public const val MAX_AD_CONTENT_RATING_T: String = "T"
        public const val MAX_AD_CONTENT_RATING_MA: String = "MA"

    }
}