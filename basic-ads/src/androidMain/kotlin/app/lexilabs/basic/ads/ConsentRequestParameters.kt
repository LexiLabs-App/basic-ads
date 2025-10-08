package app.lexilabs.basic.ads

import com.google.android.ump.ConsentRequestParameters as AndroidConsentRequestParameters

/**
 * Parameters for a consent request.
 */
public actual class ConsentRequestParameters internal constructor(
    internal val android: AndroidConsentRequestParameters = AndroidConsentRequestParameters.Builder().build()
) {
    /**
     * Gets the consent debug settings.
     *
     * @return The consent debug settings.
     */
    public actual fun getConsentDebugSettings(): ConsentDebugSettings? =
        android.consentDebugSettings?.let { ConsentDebugSettings(it) }

    /**
     * Checks if the user is under the age of consent.
     *
     * @return `true` if the user is under the age of consent, `false` otherwise.
     */
    public actual fun getIsTagForUnderAgeOfConsent(): Boolean = android.isTagForUnderAgeOfConsent

    /**
     * A builder for [ConsentRequestParameters].
     */
    public actual class Builder actual constructor() {
        @Suppress("MemberVisibilityCanBePrivate")
        internal val builder = AndroidConsentRequestParameters.Builder()

        /**
         * Sets the consent debug settings.
         *
         * @param debugSettings The consent debug settings.
         * @return This builder.
         */
        public actual fun setConsentDebugSettings(debugSettings: ConsentDebugSettings): Builder{
            builder.setConsentDebugSettings(debugSettings.android)
            return this
        }
        /**
         * Sets the AdMob app ID.
         *
         * @param adMobAppId The AdMob app ID.
         * @return This builder.
         */
        public actual fun setAdMobAppId(adMobAppId: String): Builder {
            builder.setAdMobAppId(adMobAppId)
            return this
        }
        /**
         * Sets whether the user is under the age of consent.
         *
         * @param underAgeOfConsent Whether the user is under the age of consent.
         * @return This builder.
         */
        public actual fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean): Builder {
            builder.setTagForUnderAgeOfConsent(underAgeOfConsent)
            return this
        }
        /**
         * Builds the [ConsentRequestParameters].
         *
         * @return The built [ConsentRequestParameters].
         */
        public actual fun build(): ConsentRequestParameters =
            ConsentRequestParameters(builder.build())
    }
}