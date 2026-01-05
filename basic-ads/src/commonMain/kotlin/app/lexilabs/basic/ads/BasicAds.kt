package app.lexilabs.basic.ads

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable

/**
 * A utility object for initializing and configuring the Mobile Ads SDK.
 */
@Suppress("unused")
@DependsOnGoogleMobileAds
public expect object BasicAds {
    /** The error domain for the Mobile Ads SDK. */
    public val errorDomain: String?

    /** The global [RequestConfiguration] for the Mobile Ads SDK. */
    public var configuration: RequestConfiguration

    /** The version of the Mobile Ads SDK. */
    public val version: String

    /** Indicates whether the Mobile Ads SDK has been initialized. */
    public val initialized: Boolean

    /**
     * Initializes the Mobile Ads SDK.
     * This method should be called on the main thread as early as possible, ideally on app launch.
     */
    @MainThread
    @Composable
    public fun initialize()

    /**
     * Initializes the Mobile Ads SDK.
     * This method should be called on the main thread as early as possible, ideally on app launch.
     *
     * @param context A platform-specific context. For Android, this must be an `Activity` or `Context`.
     *                For iOS, this parameter is not used and can be `null`.
     */
    @MainThread
    @Deprecated("The `context` argument is no longer required as of v1.1.0-beta01")
    public fun initialize(context: Any?)

    /**
     * Disables the automatic initialization of mediation adapters.
     * This can be useful to optimize app startup time. If you disable this, you must initialize
     * mediation adapters manually before making ad requests.
     */
    @Composable
    public fun disableMediationAdapterInitialization()

    /**
     * Disables the automatic initialization of mediation adapters.
     * This can be useful to optimize app startup time. If you disable this, you must initialize
     * mediation adapters manually before making ad requests.
     *
     * @param context A platform-specific context. For Android, this must be an `Activity` or `Context`.
     *                For iOS, this parameter is not used and can be `null`.
     */
    @Deprecated("The `context` argument is no longer required as of v1.1.0-beta01")
    public fun disableMediationAdapterInitialization(context: Any?)

    /**
     * Opens the ad inspector, which allows for testing ad configurations and troubleshooting ad delivery.
     *
     * @param context A platform-specific context. For Android, this must be an `Activity`.
     *                For iOS, this parameter is not used and can be `null`.
     * @param adUnitId The ad unit ID to use for the ad inspector.
     */
    @Composable
    public fun openDebugMenu(adUnitId: String)

    /**
     * Opens the ad inspector, which allows for testing ad configurations and troubleshooting ad delivery.
     *
     * @param context A platform-specific context. For Android, this must be an `Activity`.
     *                For iOS, this parameter is not used and can be `null`.
     * @param adUnitId The ad unit ID to use for the ad inspector.
     */
    @Deprecated("The `context` argument is no longer required as of v1.1.0-beta01")
    public fun openDebugMenu(context: Any?, adUnitId: String)

    /**
     * Sets whether the app's audio is muted for ads.
     *
     * @param muted `true` to mute the app's audio, `false` to unmute.
     */
    public fun setAppMuted(muted: Boolean)

    /**
     * Sets the app's volume for ads.
     *
     * @param volume The volume to set, ranging from 0.0 (silent) to 1.0 (full volume).
     */
    public fun setAppVolume(volume: Float)
}
