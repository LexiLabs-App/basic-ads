package app.lexilabs.basic.ads

import cocoapods.GoogleUserMessagingPlatform.UMPDebugGeographyEEA
import cocoapods.GoogleUserMessagingPlatform.UMPDebugSettings
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
public actual class ConsentDebugSettings internal constructor(
    internal val ios: UMPDebugSettings = UMPDebugSettings()
){
    init {
        UMPDebugGeographyEEA
    }
    public actual fun getDebugGeography(): DebugGeography =
        DebugGeography.entries[ios.geography.toInt()]
    public actual fun isTestDevice(): Boolean = !ios.testDeviceIdentifiers.isNullOrEmpty()

    public actual companion object {
        public actual class Builder actual constructor(activity: Any?) {
            internal val builder = UMPDebugSettings()

            public actual fun setDebugGeography(geography: DebugGeography){
                builder.geography = geography.value
            }
            public actual fun addTestDeviceHashedId(hashedId: String){
                builder.testDeviceIdentifiers = listOf<String>(hashedId)
            }
            public actual fun setForceTesting(forceTesting: Boolean){/* DO NOTHING */ }
            public actual fun build(): ConsentDebugSettings = ConsentDebugSettings(builder)
        }
        public actual enum class DebugGeography(public val value: Long) {
            DEBUG_GEOGRAPHY_DISABLED(0),
            DEBUG_GEOGRAPHY_EEA(1),
            DEBUG_GEOGRAPHY_NOT_EEA(2),
            DEBUG_GEOGRAPHY_REGULATED_US_STATE(3),
            DEBUG_GEOGRAPHY_OTHER(4);
        }
    }
}