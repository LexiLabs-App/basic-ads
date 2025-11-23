package app.lexilabs.basic.ads

import com.google.android.ump.ConsentRequestParameters as AndroidConsentRequestParameters

@DependsOnGoogleUserMessagingPlatform
public actual class ConsentRequestParameters internal constructor(
    internal val android: AndroidConsentRequestParameters = AndroidConsentRequestParameters.Builder().build()
) {
    public actual val consentDebugSettings: ConsentDebugSettings?
        get() = android.consentDebugSettings?.let { ConsentDebugSettings(it) }

    public actual val isTagForUnderAgeOfConsent: Boolean
        get() = android.isTagForUnderAgeOfConsent

    public actual class Builder actual constructor() {
        internal val builder = AndroidConsentRequestParameters.Builder()

        public actual fun setConsentDebugSettings(debugSettings: ConsentDebugSettings): Builder {
            builder.setConsentDebugSettings(debugSettings.android)
            return this
        }

        public actual fun setAdMobAppId(adMobAppId: String): Builder {
            // AdMob App ID is set in AndroidManifest.xml
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