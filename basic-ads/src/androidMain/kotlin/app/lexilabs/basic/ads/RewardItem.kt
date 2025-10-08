package app.lexilabs.basic.ads

import com.google.android.gms.ads.rewarded.RewardItem

/**
 * A reward item earned by the user.
 *
 * @param android The underlying Android [RewardItem].
 */
public actual class RewardItem(
    internal val android: RewardItem
) {
    /** The amount of the reward. */
    public actual val amount: Int = android.amount
    /** The type of the reward. */
    public actual val type: String = android.type
}