package app.lexilabs.basic.ads

@Suppress("unused")
public expect class ConsentDebugSettings {

    public fun getDebugGeography(): DebugGeography
    public fun isTestDevice(): Boolean

    public class Builder(activity: Any?) {
        public fun setDebugGeography(geography: DebugGeography)
        public fun addTestDeviceHashedId(hashedId: String)
        public fun setForceTesting(forceTesting: Boolean)
        public fun build(): ConsentDebugSettings
    }

    public enum class DebugGeography {
        DEBUG_GEOGRAPHY_DISABLED,
        DEBUG_GEOGRAPHY_EEA,
        DEBUG_GEOGRAPHY_NOT_EEA,
        DEBUG_GEOGRAPHY_REGULATED_US_STATE,
        DEBUG_GEOGRAPHY_OTHER;
    }
}

