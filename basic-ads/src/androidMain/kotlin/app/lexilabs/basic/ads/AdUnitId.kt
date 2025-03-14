package app.lexilabs.basic.ads

public actual object AdUnitId {
    public actual fun autoSelect(androidAdUnitId: String?, iosAdUnitId: String?): String {
        return androidAdUnitId ?: ""
    }
    public actual const val BANNER_DEFAULT: String = "ca-app-pub-3940256099942544/9214589741"
    public actual const val INTERSTITIAL_DEFAULT: String = "ca-app-pub-3940256099942544/1033173712"
    public actual const val REWARDED_INTERSTITIAL_DEFAULT: String = "ca-app-pub-3940256099942544/5354046379"
    public actual const val REWARDED_DEFAULT: String = "ca-app-pub-3940256099942544/5224354917"
}