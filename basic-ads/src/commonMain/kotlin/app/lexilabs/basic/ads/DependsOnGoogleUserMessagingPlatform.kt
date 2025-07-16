package app.lexilabs.basic.ads

/**
 * Annotation for APIs that depend on the Google User Messaging Platform (UMP) library.
 *
 * The UMP library is used to request consent from users in the European Economic Area (EEA), the UK, and Switzerland.
 *
 * For more information on how to integrate the UMP library, please see the following documentation:
 *
 * - **Android**: [Google's UMP instructions for Android](https://developers.google.com/admob/android/privacy)
 * - **iOS**: [Google's UMP instructions for iOS](https://developers.google.com/admob/ios/privacy)
 */
@Suppress("ExperimentalAnnotationRetention")
@RequiresOptIn(message = "Depends on Google User Messaging Platform library for Android and iOS")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
public annotation class DependsOnGoogleUserMessagingPlatform