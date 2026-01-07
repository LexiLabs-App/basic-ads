package app.lexilabs.basic.ads

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import cocoapods.GoogleUserMessagingPlatform.UMPDebugSettings
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@DependsOnGoogleUserMessagingPlatform
public actual class ConsentDebugSettings internal constructor(
    internal val ios: UMPDebugSettings = UMPDebugSettings()
) {
    public actual val debugGeography: DebugGeography
        get() = DebugGeography.entries[ios.geography.toInt()]

    public actual val isTestDevice: Boolean
        get() = !ios.testDeviceIdentifiers.isNullOrEmpty()

    @ReadOnlyComposable
    @Composable
    public actual fun Build(
        debugGeography: DebugGeography?,
        hashedId: String?,
        forceTesting: Boolean?,
    ): ConsentDebugSettings {
        val builder = Builder(null)
        debugGeography?.let { builder.setDebugGeography(it) }
        hashedId?.let { builder.addTestDeviceHashedId(it) }
        forceTesting?.let { builder.setForceTesting(it) }
        return builder.build()
    }

    public actual class Builder actual constructor(activity: Any?) {
        internal val builder = UMPDebugSettings()

        public actual fun setDebugGeography(geography: DebugGeography): Builder {
            builder.geography = geography.value
            return this
        }

        public actual fun addTestDeviceHashedId(hashedId: String): Builder {
            builder.testDeviceIdentifiers = listOf(hashedId)
            return this
        }

        public actual fun setForceTesting(forceTesting: Boolean): Builder {
            // setForceTesting is not available on iOS
            return this
        }

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