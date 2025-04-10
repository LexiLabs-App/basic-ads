package app.lexilabs.basic.ads

/**
 * An object used to hold functions related to AdMob AdUnitIds
 * @see AdUnitId.autoSelect
 */
public actual object AdUnitId {
    /**
     * Provides a way of selecting an AdMob AdUnitId by platform during runtime.
     * This function works for any ad type.
     * @param androidAdUnitId provide an AdUnitId [String] for Android implementation
     * @param iosAdUnitId provide an AdUnitId [String] for iOS implementation
     */
    public actual fun autoSelect(
        androidAdUnitId: String?,
        iosAdUnitId: String?
    ): String = ""

    public actual val BANNER_DEFAULT: String = ""
    public actual val INTERSTITIAL_DEFAULT: String = ""
    public actual val REWARDED_INTERSTITIAL_DEFAULT: String = ""
    public actual val REWARDED_DEFAULT: String = ""
}