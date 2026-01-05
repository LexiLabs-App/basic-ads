package app.lexilabs.basic.ads

import android.app.Activity
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A utility object for initializing the Mobile Ads SDK.
 */
public actual object BasicAds {

    /** The error domain for the Mobile Ads SDK. */
    public actual val errorDomain: String? = com.google.android.gms.ads.MobileAds.ERROR_DOMAIN

    /** The request configuration for the Mobile Ads SDK. */
    @DependsOnGoogleMobileAds
    public actual var configuration: RequestConfiguration
        get() = com.google.android.gms.ads.MobileAds.getRequestConfiguration().toCommon()
        set(config) = com.google.android.gms.ads.MobileAds.setRequestConfiguration(config.toAndroid())

    /** The version of the Mobile Ads SDK. */
    public actual val version: String = com.google.android.gms.ads.MobileAds.getVersion().toString()

    /** Whether the Mobile Ads SDK has been initialized. */
    public actual val initialized: Boolean
        get() = com.google.android.gms.ads.MobileAds.getInitializationStatus()?.adapterStatusMap?.isNotEmpty() ?: false

    /**
     * Initializes the Mobile Ads SDK.
     */
    @MainThread
    @Composable
    @RequiresPermission("android.permission.INTERNET")
    public actual fun Initialize() {
        val context = LocalContext.current
        LaunchedEffect(context) {
            com.google.android.gms.ads.MobileAds.initialize(context)
        }
    }

    /**
     * Initializes the Mobile Ads SDK.
     *
     * @param context The context to use for initialization. Must be an `Activity` on Android.
     */
    @MainThread
    @Deprecated("The `context` argument is no longer required as of v1.1.0-beta01")
    @RequiresPermission("android.permission.INTERNET")
    public actual fun initialize(context: Any?) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        CoroutineScope(Dispatchers.IO).launch {
            com.google.android.gms.ads.MobileAds.initialize(context as Activity)
        }
    }

    /**
     * Disables the initialization of mediation adapters.
     */
    @Composable
    public actual fun DisableMediationAdapterInitialization() {
        com.google.android.gms.ads.MobileAds.disableMediationAdapterInitialization(LocalContext.current)
    }

    /**
     * Disables the initialization of mediation adapters.
     *
     * @param context The context to use. Must be an `Activity` on Android.
     */
    @Deprecated("The `context` argument is no longer required as of v1.1.0-beta01")
    public actual fun disableMediationAdapterInitialization(context: Any?) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.disableMediationAdapterInitialization(context as Activity)
    }

    /**
     * Opens the ad inspector.
     *
     * @param adUnitId The ad unit ID to use.
     */
    @Composable
    public actual fun OpenDebugMenu(adUnitId: String) {
        com.google.android.gms.ads.MobileAds.openDebugMenu(LocalContext.current, adUnitId)
    }

    /**
     * Opens the ad inspector.
     *
     * @param context The context to use. Must be an `Activity` on Android.
     * @param adUnitId The ad unit ID to use.
     */
    @Deprecated("The `context` argument is no longer required as of v1.1.0-beta01")
    public actual fun openDebugMenu(context: Any?, adUnitId: String) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.openDebugMenu(context as Activity, adUnitId)
    }

    /**
     * Sets whether the app's audio is muted.
     *
     * @param muted Whether to mute the app's audio.
     */
    public actual fun setAppMuted(muted: Boolean) {
        com.google.android.gms.ads.MobileAds.setAppMuted(muted)
    }

    /**
     * Sets the app's volume.
     *
     * @param volume The volume to set, from 0.0 to 1.0.
     */
    public actual fun setAppVolume(volume: Float) {
        com.google.android.gms.ads.MobileAds.setAppVolume(volume)
    }
}
