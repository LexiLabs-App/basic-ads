package app.lexilabs.basic.ads

import swiftPMImport.app.lexilabs.basic.basic.ads.GADAdReward
import kotlinx.cinterop.ExperimentalForeignApi

@Suppress("CanBeParameter")
@OptIn(ExperimentalForeignApi::class)
public actual class RewardItem(
    internal val ios: GADAdReward
) {
    public actual val amount: Int = ios.amount.intValue
    public actual val type: String = ios.type
}