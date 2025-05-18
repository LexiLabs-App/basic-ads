package app.lexilabs.basic.ads

import com.google.android.ump.ConsentRequestParameters as AndroidConsentRequestParameters

public actual class ConsentRequestParameters internal constructor(
    internal val android: AndroidConsentRequestParameters = AndroidConsentRequestParameters.Builder().build()
) {
    public actual fun getConsentDebugSettings(): ConsentDebugSettings? =
        android.consentDebugSettings?.let { ConsentDebugSettings(it) }

    public actual fun getIsTagForUnderAgeOfConsent(): Boolean = android.isTagForUnderAgeOfConsent

    public actual class Builder actual constructor() {
        @Suppress("MemberVisibilityCanBePrivate")
        internal val builder = AndroidConsentRequestParameters.Builder()

        public actual fun setConsentDebugSettings(debugSettings: ConsentDebugSettings): Builder{
            builder.setConsentDebugSettings(debugSettings.android)
            return this
        }
        public actual fun setAdMobAppId(adMobAppId: String): Builder {
            builder.setAdMobAppId(adMobAppId)
            return this
        }
        public actual fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean): Builder {
            builder.setTagForUnderAgeOfConsent(underAgeOfConsent)
            return this
        }
        public actual fun build(): ConsentRequestParameters =
            ConsentRequestParameters(builder.build())
    }
}