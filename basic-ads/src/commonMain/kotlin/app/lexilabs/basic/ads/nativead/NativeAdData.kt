package app.lexilabs.basic.ads.nativead

public expect class NativeAdData{
    public val adChoicesInfo: AdChoicesInfo?
    public val advertiser: String?
    public val body: String?
    public val callToAction: String?
    public val headline: String?
    public val icon: AdIcon?
    public val mediaContent: MediaContent?
    public val muteThisAdReasons: List<MuteThisAdReason>
    public val placementId: Long?
    public val price: String?
    public val starRating: Double?
    public val store: String?
    public class AdChoicesInfo
    public class AdIcon
    public class MediaContent
    public class MuteThisAdReason
}