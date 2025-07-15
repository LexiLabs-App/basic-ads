package app.lexilabs.basic.ads

/**
 * Parameters sent when updating user consent information.
 *
 * This class encapsulates parameters used when requesting an update to the user's consent status.
 *
 * @see getConsentDebugSettings
 * @see getIsTagForUnderAgeOfConsent
 * @see Builder
 */
@Suppress("unused")
@DependsOnGoogleUserMessagingPlatform
public expect class ConsentRequestParameters {

    /**
     * Gets the [ConsentDebugSettings].
     *
     * @see [ConsentDebugSettings]
     */
    public fun getConsentDebugSettings(): ConsentDebugSettings?

    /**
     * Gets the TFUA value sets by [Builder.setTagForUnderAgeOfConsent].
     */
    public fun getIsTagForUnderAgeOfConsent(): Boolean

    /**
     * Builder of [ConsentRequestParameters].
     *
     * @see setConsentDebugSettings
     * @see setAdMobAppId
     * @see setTagForUnderAgeOfConsent
     * @see build
     */
    public class Builder() {

        /**
         * Sets the [ConsentDebugSettings].
         *
         * If `null`, no debug setting will be used.
         * @param debugSettings Debug Settings to be set for [Consent.requestConsentInfoUpdate]
         * @return [Builder]
         * @see ConsentDebugSettings
         */
        public fun setConsentDebugSettings(debugSettings: ConsentDebugSettings): Builder

        /**
         * (iOS only) Sets the AdMobAppId for [Consent.requestConsentInfoUpdate]
         * @param adMobAppId Your adMobAppId [String]
         * @return [Builder]
         */
        public fun setAdMobAppId(adMobAppId: String): Builder

        /**
         * Sets whether the user is tagged for under age of consent.
         *
         * Default value is `false`.
         *
         * @param underAgeOfConsent is `true` if the user is under the age of consent.
         * @return [Builder]
         */
        public fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean): Builder

        /**
         * Builds the [ConsentRequestParameters]
         * @return [ConsentRequestParameters]
         */
        public fun build(): ConsentRequestParameters
    }
}