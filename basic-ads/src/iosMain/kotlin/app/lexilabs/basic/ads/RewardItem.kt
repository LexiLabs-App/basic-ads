package app.lexilabs.basic.ads

import cocoapods.Google_Mobile_Ads_SDK.GADAdReward
import kotlinx.cinterop.ExperimentalForeignApi

@Suppress("CanBeParameter")
@OptIn(ExperimentalForeignApi::class)
public actual class RewardItem(
    internal val ios: GADAdReward
) {
    public actual val amount: Int = ios.amount.intValue
    public actual val type: String = ios.type
}