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
        /** Indicates that the child-directed status is not specified. */
        public const val TAG_FOR_CHILD_DIRECTED_TREATMENT_UNSPECIFIED: Int = -1
        /** Indicates that the app is not child-directed. */
        public const val TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE: Int = 0
        /** Indicates that the app is child-directed. */
        public const val TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE: Int = 1
        /** Indicates that the user is under the age of consent. */
        public const val TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE: Int = 1
        /** Indicates that the user is not under the age of consent. */
        public const val TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE: Int = 0
        /** Indicates that the under-age-of-consent status is not specified. */
        public const val TAG_FOR_UNDER_AGE_OF_CONSENT_UNSPECIFIED: Int = -1
        /** Indicates that the maximum ad content rating is not specified. */
        public const val MAX_AD_CONTENT_RATING_UNSPECIFIED: String = ""
        /** Content suitable for all audiences. */
        public const val MAX_AD_CONTENT_RATING_G: String = "G"
        /** Content suitable for most audiences with parental guidance. */
        public const val MAX_AD_CONTENT_RATING_PG: String = "PG"
        /** Content suitable for teen and older audiences. */
        public const val MAX_AD_CONTENT_RATING_T: String = "T"
        /** Content suitable only for mature audiences. */
        public const val MAX_AD_CONTENT_RATING_MA: String = "MA"

    }
}