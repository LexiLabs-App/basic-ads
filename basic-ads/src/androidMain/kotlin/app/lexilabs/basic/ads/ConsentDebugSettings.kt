package app.lexilabs.basic.ads

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

@DependsOnGoogleUserMessagingPlatform
public actual class ConsentDebugSettings internal constructor(
    internal val android: com.google.android.ump.ConsentDebugSettings
) {
    public actual val debugGeography: DebugGeography
        get() = DebugGeography.fromValue(android.debugGeography)

    public actual val isTestDevice: Boolean
        get() = android.isTestDevice

    @ReadOnlyComposable
    @Composable
    public actual fun Build(
        debugGeography: DebugGeography?,
        hashedId: String?,
        forceTesting: Boolean?,
    ): ConsentDebugSettings {
        val builder = Builder(LocalContext.current.getActivity())
        debugGeography?.let { builder.setDebugGeography(it) }
        hashedId?.let { builder.addTestDeviceHashedId(it) }
        forceTesting?.let { builder.setForceTesting(it) }
        return builder.build()
    }

    public actual class Builder actual constructor(activity: Any?) {
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

        internal companion object {
            fun fromValue(value: Int): DebugGeography = entries.first { it.value == value }
        }
    }
}