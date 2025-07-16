package app.lexilabs.basic.ads

/**
 * An object used to hold functions related to AdMob AdUnitIds.
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
     * Provides a way of selecting an AdMob AdUnitId by platform during runtime.
     * This function works for any ad type.
     * @param androidAdUnitId provide an AdUnitId [String] for Android implementation
     * @param iosAdUnitId provide an AdUnitId [String] for iOS implementation
     */
    public fun autoSelect(androidAdUnitId: String? = null, iosAdUnitId: String? = null): String

    public val BANNER_DEFAULT: String
    public val INTERSTITIAL_DEFAULT: String
    public val REWARDED_INTERSTITIAL_DEFAULT: String
    public val REWARDED_DEFAULT: String
}