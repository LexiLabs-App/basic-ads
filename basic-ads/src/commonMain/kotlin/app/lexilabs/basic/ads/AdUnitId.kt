package app.lexilabs.basic.ads

/**
 * A utility object for providing ad unit IDs.
 *
 * This object provides a way to manage and access AdMob AdUnitIds for different ad types and platforms.
 * It includes a utility function for selecting the appropriate AdUnitId based on the current platform (Android or iOS)
 * and provides default AdUnitIds for common ad formats.
 *
 * @see AdUnitId.autoSelect For selecting AdUnitIds dynamically based on the platform.
 * @see BANNER_DEFAULT Default AdUnitId for Banner ads.
 * @see INTERSTITIAL_DEFAULT Default AdUnitId for Interstitial ads.
 * @see REWARDED_INTERSTITIAL_DEFAULT Default AdUnitId for Rewarded Interstitial ads.
 * @see REWARDED_DEFAULT Default AdUnitId for Rewarded ads.
 */
@Suppress("unused")
public expect object AdUnitId {
    /**
     * Selects the appropriate ad unit ID based on the platform at runtime.
     *
     * @param androidAdUnitId The ad unit ID for the Android implementation.
     * @param iosAdUnitId The ad unit ID for the iOS implementation.
     * @return The ad unit ID for the current platform.
     */
    public fun autoSelect(androidAdUnitId: String? = null, iosAdUnitId: String? = null): String

    /** The default test ad unit ID for banner ads. */
    public val BANNER_DEFAULT: String
    /** The default test ad unit ID for interstitial ads. */
    public val INTERSTITIAL_DEFAULT: String
    /** The default test ad unit ID for rewarded interstitial ads. */
    public val REWARDED_INTERSTITIAL_DEFAULT: String
    /** The default test ad unit ID for rewarded ads. */
    public val REWARDED_DEFAULT: String
    /** The default test ad unit ID for native ads. */
    public val NATIVE_DEFAULT: String
}