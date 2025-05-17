package app.lexilabs.basic.ads

import cocoapods.GoogleUserMessagingPlatform.UMPRequestParameters
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
public actual class ConsentRequestParameters internal constructor(
    internal val ios: UMPRequestParameters = UMPRequestParameters()
) {
    public actual fun getConsentDebugSettings(): ConsentDebugSettings? =
        ios.debugSettings?.let { ConsentDebugSettings(it) }

    public actual fun getIsTagForUnderAgeOfConsent(): Boolean = ios.tagForUnderAgeOfConsent

    public actual class Builder actual constructor() {
        @Suppress("MemberVisibilityCanBePrivate")
        internal val builder = UMPRequestParameters()

        public actual fun setConsentDebugSettings(debugSettings: ConsentDebugSettings){
            builder.debugSettings = debugSettings.ios
        }
        public actual fun setAdMobAppId(adMobAppId: String){ /* DO NOTHING */ }
        public actual fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean){
            builder.setTagForUnderAgeOfConsent(underAgeOfConsent)
        }
        public actual fun build(): ConsentRequestParameters =
            ConsentRequestParameters(builder)
    }
}