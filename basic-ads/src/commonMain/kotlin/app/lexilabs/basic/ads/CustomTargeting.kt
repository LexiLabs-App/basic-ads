package app.lexilabs.basic.ads


public data class CustomTargeting(
    val key: String,
    val value: List<String>
) {
    public fun toIos(): Map<Any?, Any> {
        return mapOf(Pair(this.key, this.value))
    }
}