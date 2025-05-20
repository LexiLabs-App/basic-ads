package app.lexilabs.basic.ads

import android.app.Activity
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.IO).launch {
            com.google.android.gms.ads.MobileAds.initialize(context as Activity)
        }
    }

    public actual fun disableMediationAdapterInitialization(context: Any?) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.disableMediationAdapterInitialization(context as Activity)
    }

    public actual fun openDebugMenu(context: Any?, adUnitId: String) {
        require(context != null) {
            "Context must be set to non-null value in Android"
        }
        com.google.android.gms.ads.MobileAds.openDebugMenu(context as Activity, adUnitId)
    }

    public actual fun setAppMuted(muted: Boolean) {
        com.google.android.gms.ads.MobileAds.setAppMuted(muted)
    }

    public actual fun setAppVolume(volume: Float) {
        com.google.android.gms.ads.MobileAds.setAppVolume(volume)
    }
}
