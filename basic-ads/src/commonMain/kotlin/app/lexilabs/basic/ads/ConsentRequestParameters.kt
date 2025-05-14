package app.lexilabs.basic.ads

@Suppress("unused")
public expect class ConsentRequestParameters {
    public fun getConsentDebugSettings(): ConsentDebugSettings?
    public fun getIsTagForUnderAgeOfConsent(): Boolean
    public companion object {
        public class Builder() {
            public fun setConsentDebugSettings(debugSettings: ConsentDebugSettings)
            public fun setAdMobAppId(adMobAppId: String)
            public fun setTagForUnderAgeOfConsent(underAgeOfConsent: Boolean)
            public fun build(): ConsentRequestParameters
        }
    }
}