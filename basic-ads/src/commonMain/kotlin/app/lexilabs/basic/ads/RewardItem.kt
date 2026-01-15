package app.lexilabs.basic.ads

/**
 * Represents a reward that the user can earn.
 *
 * @property amount The amount of the reward.
 * @property type The type of the reward.
 */
public expect class RewardItem {
    /** The amount of the reward. */
    public val amount: Int
    /** The type of the reward. */
    public val type: String
}
