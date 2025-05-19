package app.lexilabs.basic.ads

public expect class RewardedInterstitialAd(activity: Any?) {

    public fun load(
        adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    )

    public fun setListeners(
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {}
    )

    public fun show(
        onRewardEarned: () -> Unit
    )
}