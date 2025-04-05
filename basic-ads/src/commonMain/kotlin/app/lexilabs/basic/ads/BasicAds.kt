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
public expect object BasicAds {
    /**
     * The domain name of which errors occur on
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public val errorDomain: String?

    /**
     * The [RequestConfiguration] for the instantiation of AdMob
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public var configuration: RequestConfiguration

    /**
     * The version of AdMob being used
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public val version: String

    /**
     * Whether or not the [BasicAds.initialize] was called successfully
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public val initialized: Boolean

    /**
     * The main function for instantiating Android and iOS implementations of AdMob.
     * It is equivalent to Android's `MobileAds.Initialize(context)`.
     * @throws Exception when run outside the [MainThread]
     * @param context Android Context or null for iOS, passed in an [Any] variable
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    @MainThread
    public fun initialize(context: Any?)
//    public fun getInitializationStatus(): InitializationStatus?

    /**
     * I have no idea what this does, but its here and it works on Android and iOS
     * @param context Android Context or null for iOS, passed in an [Any] variable
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public fun disableMediationAdapterInitialization(context: Any?)

    /**
     * This opens the Debug Menu, which I have never personally done myself.
     * @param context Android Context or null for iOS, passed in an [Any] variable
     * @param adUnitId AdMob AdUnitId [String] for [AdRequest]
     * @see DependsOnGoogleMobileAds
     * @see BasicAds
     */
    public fun openDebugMenu(context: Any?, adUnitId: String)

    /**
     * Mutes or unmutes ads
     * @param muted a [Boolean] value that should evaluate `true` when muted and `false` when unmuted
     */
    public fun setAppMuted(muted: Boolean)

    /**
     * Sets the volume of ads based on a [Float] value between `0.0` for muted and `1.0` for full volume.
     * @param volume a [Float] value between `0.0` for muted and `1.0` for full volume.
     */
    public fun setAppVolume(volume: Float)
}

