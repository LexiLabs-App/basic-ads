package app.lexilabs.basic.ads

import com.google.android.gms.ads.rewarded.RewardItem

@Suppress("CanBeParameter")
public actual class RewardItem(
    internal val android: RewardItem
) {
    public actual val amount: Int = android.amount
    public actual val type: String = android.type
}