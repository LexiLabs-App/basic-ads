package app.lexilabs.basic.ads

/**
 * Debug settings to hardcode in test requests to [Consent.requestConsentInfoUpdate].
 *
 * @see getDebugGeography
 * @see isTestDevice
 * @see Builder
 * @see DebugGeography
 */
@Suppress("unused")
@DependsOnGoogleUserMessagingPlatform
public expect class ConsentDebugSettings {

    /**
     * Gets the debug geography.
     * @see DebugGeography
     */
    public fun getDebugGeography(): DebugGeography

    /**
     * Returns `true` if this device is a test device.
     */
    public fun isTestDevice(): Boolean

    /**
     * Builder of [ConsentDebugSettings].
     *
     * @param activity Required for Android, but can be null on all other platforms.
     * @see setDebugGeography
     * @see addTestDeviceHashedId
     * @see setForceTesting
     * @see build
     */
    public class Builder(activity: Any?) {

        /**
         * Sets the debug geography for testing purposes.
         *
         * Default value is [DebugGeography.DEBUG_GEOGRAPHY_DISABLED].
         * @param geography the [DebugGeography] to set while testing [Consent.requestConsentInfoUpdate]
         * @return [Builder]
         */
        public fun setDebugGeography(geography: DebugGeography): Builder

        /**
         * Registers a device as a test device.
         * Test devices respect debug geography settings to enable easier testing.
         * Test devices must be added individually so that debug geography settings
         * won't accidentally get released to all users.
         *
         * You can access the hashedDeviceId from logcat once your app calls
         * [Consent.requestConsentInfoUpdate].
         * @param hashedId The hashId from the test device
         * @return [Builder]
         */
        public fun addTestDeviceHashedId(hashedId: String): Builder

        /**
         * Forces testing for [Consent.requestConsentInfoUpdate]
         * @param forceTesting `true` if you want to force testing
         * @return [Builder]
         */
        public fun setForceTesting(forceTesting: Boolean): Builder

        /**
         * Builds the [ConsentDebugSettings]
         * @return [ConsentDebugSettings]
         */
        public fun build(): ConsentDebugSettings
    }

    /**
     * Location of [DebugGeography] to test the [Consent.requestConsentInfoUpdate]
     *
     * @see DEBUG_GEOGRAPHY_DISABLED
     * @see DEBUG_GEOGRAPHY_EEA
     * @see DEBUG_GEOGRAPHY_NOT_EEA
     * @see DEBUG_GEOGRAPHY_REGULATED_US_STATE
     * @see DEBUG_GEOGRAPHY_OTHER
     */
    public enum class DebugGeography {
        DEBUG_GEOGRAPHY_DISABLED,
        DEBUG_GEOGRAPHY_EEA,
        DEBUG_GEOGRAPHY_NOT_EEA,
        DEBUG_GEOGRAPHY_REGULATED_US_STATE,
        DEBUG_GEOGRAPHY_OTHER;
    }
}

