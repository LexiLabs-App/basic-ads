package app.lexilabs.basic.ads

/**
 * For **Android**, complete the steps in AdMob's instructions:
 * * [Configure your app](https://developers.google.com/admob/android/quick-start#import_the_mobile_ads_sdk)
 *
 * For **iOS**, complete the steps in AdMob's instructions:
 * * [Import the Mobile Ads SDK](https://developers.google.com/admob/ios/quick-start#import_the_mobile_ads_sdk)
 * * [Update your Info.plist](https://developers.google.com/admob/ios/quick-start#update_your_infoplist)
 *
 * ***NOTE: For Xcode 13+, you can update your [Custom iOS Target Properties](https://useyourloaf.com/blog/xcode-13-missing-info.plist/).***
 *
 * Once complete, you'll need to call [BasicAds.initialize] in your `commonMain` before calling for ads.
 *
 * ***NOTE: You do not need to initialize within each platform.***
 * @throws ClassNotFoundException
 */
@RequiresOptIn(message = "Depends on Google Mobile Ads library for Android and iOS")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class DependsOnGoogleMobileAds()