package app.lexilabs.basic.ads

import android.content.Context
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission

public actual typealias AdError = com.google.android.gms.ads.AdError
//public actual typealias InitializationStatus = com.google.android.gms.ads.initialization.InitializationStatus
public typealias AdView = com.google.android.gms.ads.AdView

public actual object BasicAds {

    public actual val errorDomain: String? = com.google.android.gms.ads.MobileAds.ERROR_DOMAIN

    @DependsOnGoogleMobileAds
    public actual var configuration: RequestConfiguration
        get() = com.google.android.gms.ads.MobileAds.getRequestConfiguration().toCommon()
        set(config) = com.google.android.gms.ads.MobileAds.setRequestConfiguration(config.toAndroid())

    public actual val version: String = com.google.android.gms.ads.MobileAds.getVersion().toString()

    public actual val initialized: Boolean
        get() = com.google.android.gms.ads.MobileAds.getInitializationStatus()?.adapterStatusMap?.isNotEmpty() ?: false

    @MainThread
    @RequiresPermission("android.permission.INTERNET")
    public actual fun initialize(context: Any?) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.initialize(context as Context)
    }

    public actual fun disableMediationAdapterInitialization(context: Any?) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.disableMediationAdapterInitialization(context as Context)
    }

    public actual fun openDebugMenu(context: Any?, adUnitId: String) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.openDebugMenu(context as Context, adUnitId)
    }

    public actual fun setAppMuted(muted: Boolean) {
        com.google.android.gms.ads.MobileAds.setAppMuted(muted)
    }

    public actual fun setAppVolume(volume: Float) {
        com.google.android.gms.ads.MobileAds.setAppVolume(volume)
    }

//    public actual fun getInitializationStatus(): InitializationStatus? =
//        com.google.android.gms.ads.MobileAds.getInitializationStatus()
}
