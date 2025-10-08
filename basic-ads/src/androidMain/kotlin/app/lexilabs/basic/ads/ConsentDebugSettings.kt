package app.lexilabs.basic.ads

import android.app.Activity

/**
 * Debug settings for consent.
 */
public actual class ConsentDebugSettings internal constructor(
    internal val android: com.google.android.ump.ConsentDebugSettings
){
    /**
     * Gets the debug geography.
     *
     * @return The debug geography.
     */
    public actual fun getDebugGeography(): DebugGeography = DebugGeography.entries[android.debugGeography]
    /**
     * Checks if the device is a test device.
     *
     * @return `true` if the device is a test device, `false` otherwise.
     */
    public actual fun isTestDevice(): Boolean = android.isTestDevice

    /**
     * A builder for [ConsentDebugSettings].
     *
     * @param activity The activity context.
     */
    public actual class Builder actual constructor(activity: Any?) {
        @Suppress("MemberVisibilityCanBePrivate")
        internal val builder =
            com.google.android.ump.ConsentDebugSettings.Builder(activity as Activity)

        /**
         * Sets the debug geography.
         *
         * @param geography The debug geography.
         * @return This builder.
         */
        public actual fun setDebugGeography(geography: DebugGeography): Builder {
            builder.setDebugGeography(geography.value)
            return this
        }
        /**
         * Adds a test device hashed ID.
         *
         * @param hashedId The hashed ID of the test device.
         * @return This builder.
         */
        public actual fun addTestDeviceHashedId(hashedId: String): Builder {
            builder.addTestDeviceHashedId(hashedId)
            return this
        }
        /**
         * Sets whether to force testing.
         *
         * @param forceTesting Whether to force testing.
         * @return This builder.
         */
        public actual fun setForceTesting(forceTesting: Boolean): Builder {
            builder.setForceTesting(forceTesting)
            return this
        }
        /**
         * Builds the [ConsentDebugSettings].
         *
         * @return The built [ConsentDebugSettings].
         */
        public actual fun build(): ConsentDebugSettings = ConsentDebugSettings(builder.build())
    }
    /**
     * The debug geography.
     */
    public actual enum class DebugGeography(public val value: Int) {
        /** Debug geography is disabled. */
        DEBUG_GEOGRAPHY_DISABLED(0),
        /** Debug geography is set to EEA. */
        DEBUG_GEOGRAPHY_EEA(1),
        /** Debug geography is set to not EEA. */
        DEBUG_GEOGRAPHY_NOT_EEA(2),
        /** Debug geography is set to a regulated US state. */
        DEBUG_GEOGRAPHY_REGULATED_US_STATE(3),
        /** Debug geography is set to other. */
        DEBUG_GEOGRAPHY_OTHER(4);
    }
}