package app.lexilabs.basic.ads

public actual object AdUnitId {
    public actual fun autoSelect(androidAdUnitId: String?, iosAdUnitId: String?): String {
        return iosAdUnitId ?: ""
    }

    public actual const val BANNER_DEFAULT: String = "ca-app-pub-3940256099942544/2435281174"
    public actual const val INTERSTITIAL_DEFAULT: String = "ca-app-pub-3940256099942544/4411468910"
    public actual const val REWARDED_INTERSTITIAL_DEFAULT: String = "ca-app-pub-3940256099942544/6978759866"
    public actual const val REWARDED_DEFAULT: String = "ca-app-pub-3940256099942544/1712485313"
}