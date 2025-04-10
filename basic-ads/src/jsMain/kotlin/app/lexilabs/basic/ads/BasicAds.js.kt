package app.lexilabs.basic.ads

import androidx.annotation.MainThread

/**
 * The main module for instantiating Android and iOS implementations of AdMob
 * @property errorDomain The domain name of which errors occur on
 * @property configuration The [RequestConfiguration] for the instantiation of AdMob
 * @property version The version of AdMob being used
 * @property initialized Whether or not the [BasicAds.initialize] was called successfully
 * @see initialize
 * @see disableMediationAdapterInitialization
 * @see openDebugMenu
 * @see setAppMuted
 * @see setAppVolume
 */
@DependsOnGoogleMobileAds
public actual object BasicAds {

    //    public fun getInitializationStatus(): InitializationStatus?
    /**
     * The domain name of which errors occur on
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public actual val errorDomain: String? = null

    /**
     * The [RequestConfiguration] for the instantiation of AdMob
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public actual var configuration: RequestConfiguration =
        RequestConfiguration(
            null,
            RequestConfiguration.PublisherPrivacyPersonalizationState.DEFAULT,
            0,
            0,
            null
        )

    /**
     * The version of AdMob being used
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public actual val version: String = ""

    /**
     * Whether or not the [BasicAds.initialize] was called successfully
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public actual val initialized: Boolean = true

    /**
     * The main function for instantiating Android and iOS implementations of AdMob.
     * It is equivalent to Android's `MobileAds.Initialize(context)`.
     * @throws Exception when run outside the [MainThread]
     * @param context Android Context or null for iOS, passed in an [Any] variable
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    @MainThread
    public actual fun initialize(context: Any?) {
        // DO NOTHING
    }

    /**
     * I have no idea what this does, but its here and it works on Android and iOS
     * @param context Android Context or null for iOS, passed in an [Any] variable
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public actual fun disableMediationAdapterInitialization(context: Any?) {
        // DO NOTHING
    }

    /**
     * This opens the Debug Menu, which I have never personally done myself.
     * @param context Android Context or null for iOS, passed in an [Any] variable
     * @param adUnitId AdMob AdUnitId [String] for [AdRequest]
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public actual fun openDebugMenu(context: Any?, adUnitId: String) {
        // DO NOTHING
    }

    /**
     * Mutes or unmutes ads
     * @param muted a [Boolean] value that should evaluate `true` when muted and `false` when unmuted
     */
    public actual fun setAppMuted(muted: Boolean) {
        // DO NOTHING
    }

    /**
     * Sets the volume of ads based on a [Float] value between `0.0` for muted and `1.0` for full volume.
     * @param volume a [Float] value between `0.0` for muted and `1.0` for full volume.
     */
    public actual fun setAppVolume(volume: Float) {
        // DO NOTHING
    }
}