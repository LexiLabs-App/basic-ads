package app.lexilabs.basic.ads

@Suppress("unused")
public expect class ConsentRequestParameters {
    public fun getConsentDebugSettings(): ConsentDebugSettings?
    public fun getIsTagForUnderAgeOfConsent(): Boolean
    public class Builder() {
        public fun setConsentDebugSettings(debugSettings: ConsentDebugSettings): Builder
        public fun setAdMobAppId(adMobAppId: String): Builder
        public fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean): Builder
        public fun build(): ConsentRequestParameters
    }
}