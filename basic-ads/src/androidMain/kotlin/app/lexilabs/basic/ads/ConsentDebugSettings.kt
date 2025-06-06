package app.lexilabs.basic.ads

import android.app.Activity

public actual class ConsentDebugSettings internal constructor(
    internal val android: com.google.android.ump.ConsentDebugSettings
){
    public actual fun getDebugGeography(): DebugGeography = DebugGeography.entries[android.debugGeography]
    public actual fun isTestDevice(): Boolean = android.isTestDevice

    public actual class Builder actual constructor(activity: Any?) {
        @Suppress("MemberVisibilityCanBePrivate")
        internal val builder =
            com.google.android.ump.ConsentDebugSettings.Builder(activity as Activity)

        public actual fun setDebugGeography(geography: DebugGeography): Builder {
            builder.setDebugGeography(geography.value)
            return this
        }
        public actual fun addTestDeviceHashedId(hashedId: String): Builder {
            builder.addTestDeviceHashedId(hashedId)
            return this
        }
        public actual fun setForceTesting(forceTesting: Boolean): Builder {
            builder.setForceTesting(forceTesting)
            return this
        }
        public actual fun build(): ConsentDebugSettings = ConsentDebugSettings(builder.build())
    }
    public actual enum class DebugGeography(public val value: Int) {
        DEBUG_GEOGRAPHY_DISABLED(0),
        DEBUG_GEOGRAPHY_EEA(1),
        DEBUG_GEOGRAPHY_NOT_EEA(2),
        DEBUG_GEOGRAPHY_REGULATED_US_STATE(3),
        DEBUG_GEOGRAPHY_OTHER(4);
    }
}