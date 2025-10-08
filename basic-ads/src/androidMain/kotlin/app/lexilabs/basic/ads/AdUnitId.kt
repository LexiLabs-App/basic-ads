package app.lexilabs.basic.ads

/**
 * A utility object for providing ad unit IDs.
 */
public actual object AdUnitId {
    /**
     * Selects the appropriate ad unit ID for the current platform.
     * On Android, it returns the `androidAdUnitId`.
     *
     * @param androidAdUnitId The ad unit ID for Android.
     * @param iosAdUnitId The ad unit ID for iOS.
     * @return The selected ad unit ID.
     */
    public actual fun autoSelect(androidAdUnitId: String?, iosAdUnitId: String?): String {
        return androidAdUnitId ?: ""
    }
    /** The default ad unit ID for banner ads. */
    public actual const val BANNER_DEFAULT: String = "ca-app-pub-3940256099942544/9214589741"
    /** The default ad unit ID for interstitial ads. */
    public actual const val INTERSTITIAL_DEFAULT: String = "ca-app-pub-3940256099942544/1033173712"
    /** The default ad unit ID for rewarded interstitial ads. */
    public actual const val REWARDED_INTERSTITIAL_DEFAULT: String = "ca-app-pub-3940256099942544/5354046379"
    /** The default ad unit ID for rewarded ads. */
    public actual const val REWARDED_DEFAULT: String = "ca-app-pub-3940256099942544/5224354917"
    /** The default ad unit ID for native ads. */
    public actual const val NATIVE_DEFAULT: String = "ca-app-pub-3940256099942544/2247696110"
}