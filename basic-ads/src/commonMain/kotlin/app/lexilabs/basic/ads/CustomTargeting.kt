package app.lexilabs.basic.ads


/**
 * Represents custom key-value targeting parameters for Google Mobile Ads requests.
 *
 * Custom targeting allows publishers to pass custom key-value pairs in ad requests to target specific audience segments or ad placements.
 *
 * @property key The custom targeting key.
 * @property value A list of string values associated with the targeting key.
 */
public data class CustomTargeting(
    val key: String,
    val value: List<String>
) {
    /**
     * Converts the custom targeting parameters to a Map format compatible with the iOS Google Mobile Ads SDK.
     *
     * @return A map representation of key-value pairs for iOS GADRequest.
     */
    public fun toIos(): Map<Any?, Any> {
        return mapOf(Pair(this.key, this.value))
    }
}