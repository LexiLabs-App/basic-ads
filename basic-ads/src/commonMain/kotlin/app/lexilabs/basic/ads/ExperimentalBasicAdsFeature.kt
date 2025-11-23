package app.lexilabs.basic.ads

/**
 * Annotation for Basic-Ads APIs that are experimental and may be deprecated between releases.
 */
@Suppress("ExperimentalAnnotationRetention")
@RequiresOptIn(
    message = "This Basic-Ads feature is experimental and may be deprecated between releases.",
    level = RequiresOptIn.Level.ERROR
)
@Target(AnnotationTarget.TYPEALIAS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
public annotation class ExperimentalBasicAdsFeature()
