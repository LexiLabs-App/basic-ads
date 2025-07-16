package app.lexilabs.basic.ads

/**
 * Marks declarations that are still **experimental** in the Basic Ads API.
 *
 * Such declarations may be changed in backward-incompatible ways or even removed in the future.
 * Do not use them in production code.
 *
 * This API is not considered 'production' until reaching version 1.0.0 or higher.
 */
@RequiresOptIn(message = "This API is experimental, unstable, and will definitely change.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class ExperimentalBasicAds


