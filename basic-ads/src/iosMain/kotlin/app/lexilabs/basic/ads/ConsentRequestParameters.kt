package app.lexilabs.basic.ads

import cocoapods.GoogleUserMessagingPlatform.UMPRequestParameters
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@DependsOnGoogleUserMessagingPlatform
public actual class ConsentRequestParameters internal constructor(
    internal val ios: UMPRequestParameters = UMPRequestParameters()
) {
    public actual val consentDebugSettings: ConsentDebugSettings?
        get() = ios.debugSettings?.let { ConsentDebugSettings(it) }

    public actual val isTagForUnderAgeOfConsent: Boolean
        get() = ios.tagForUnderAgeOfConsent

    public actual class Builder actual constructor() {
        internal val builder = UMPRequestParameters()

        public actual fun setConsentDebugSettings(debugSettings: ConsentDebugSettings): Builder {
            builder.debugSettings = debugSettings.ios
            return this
        }

        public actual fun setAdMobAppId(adMobAppId: String): Builder {
            // AdMob App ID is set in Info.plist
            return this
        }

        public actual fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean): Builder {
            builder.setTagForUnderAgeOfConsent(underAgeOfConsent)
            return this
        }

        public actual fun build(): ConsentRequestParameters =
            ConsentRequestParameters(builder)
    }
}