package app.lexilabs.basic.ads

/**
 * For **Android**, complete [these steps in Google's UMP instructions](https://developers.google.com/admob/android/privacy).
 *
 * For **iOS**, complete [these steps in Google's UMP instructions](https://developers.google.com/admob/ios/privacy).
 */
@RequiresOptIn(message = "Depends on Google User Messaging Platform library for Android and iOS")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class DependsOnGoogleUserMessagingPlatform