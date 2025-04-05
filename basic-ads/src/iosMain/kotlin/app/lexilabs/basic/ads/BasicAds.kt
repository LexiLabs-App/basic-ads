package app.lexilabs.basic.ads

import androidx.annotation.MainThread
import cocoapods.Google_Mobile_Ads_SDK.GADErrorDomain
import cocoapods.Google_Mobile_Ads_SDK.GADMobileAds
import cocoapods.Google_Mobile_Ads_SDK.GADPublisherPrivacyPersonalizationState
import cocoapods.Google_Mobile_Ads_SDK.GADRequestConfiguration
import cocoapods.Google_Mobile_Ads_SDK.GADVersionNumber
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSNumber

//@OptIn(ExperimentalForeignApi::class)
//public actual typealias InitializationStatus = GADInitializationStatus
@OptIn(ExperimentalForeignApi::class)
public actual object BasicAds {

    public actual val errorDomain: String = GADErrorDomain

    @DependsOnGoogleMobileAds
    public actual var configuration: RequestConfiguration
        get() = GADMobileAds.sharedInstance().requestConfiguration().toCommon()
        set(config) { config.setConfigurationForIos() }

    public actual val version: String = GADMobileAds.sharedInstance().versionNumber.useContents {
        "$majorVersion.$minorVersion.$patchVersion"
    }

    public actual val initialized: Boolean =
        GADMobileAds.sharedInstance().initializationStatus.adapterStatusesByClassName.isNotEmpty()

    @MainThread
    public actual fun initialize(context: Any?) {
        GADMobileAds.sharedInstance().startWithCompletionHandler(null)
    }

//    @OptIn(ExperimentalForeignApi::class)
//    public actual fun getInitializationStatus(): InitializationStatus? =
//        GADMobileAds.sharedInstance().initializationStatus

    @OptIn(ExperimentalForeignApi::class)
    public actual fun disableMediationAdapterInitialization(context: Any?) {
        GADMobileAds.sharedInstance().disableMediationInitialization()
    }

    public actual fun openDebugMenu(context: Any?, adUnitId: String) {
        // DO NOTHING. This doesn't exist in iOS
    }

    @OptIn(ExperimentalForeignApi::class)
    public actual fun setAppMuted(muted: Boolean) {
        GADMobileAds.sharedInstance().setApplicationMuted(muted)
    }

    @OptIn(ExperimentalForeignApi::class)
    public actual fun setAppVolume(volume: Float) {
        GADMobileAds.sharedInstance().setApplicationVolume(volume)
    }
}

@DependsOnGoogleMobileAds
@OptIn(ExperimentalForeignApi::class)
private fun RequestConfiguration.setConfigurationForIos() {
    GADMobileAds.sharedInstance().requestConfiguration.let {
        it.setMaxAdContentRating(this.maxAdContentRating)
        it.setPublisherPrivacyPersonalizationState(this.publisherPrivacyPersonalizationState.toIos())
        it.setTagForUnderAgeOfConsent(NSNumber(this.tagForUnderAgeOfConsent))
        it.setTagForChildDirectedTreatment(NSNumber(this.tagForChildDirectedTreatment))
        it.setTestDeviceIdentifiers(this.testDeviceIds)
    }
}

@DependsOnGoogleMobileAds
@OptIn(ExperimentalForeignApi::class)
private fun GADRequestConfiguration.toCommon(): RequestConfiguration =
    RequestConfiguration(
        maxAdContentRating = this.maxAdContentRating,
        publisherPrivacyPersonalizationState = this.publisherPrivacyPersonalizationState.toCommon(),
        tagForChildDirectedTreatment = this.tagForChildDirectedTreatment?.intValue ?: 0,
        tagForUnderAgeOfConsent = this.tagForUnderAgeOfConsent?.intValue ?: 0,
        testDeviceIds = this.testDeviceIdentifiers?.map { it.toString() }
    )

@DependsOnGoogleMobileAds
private fun RequestConfiguration.PublisherPrivacyPersonalizationState.toIos(): GADPublisherPrivacyPersonalizationState =
    this.ordinal.toLong()

@DependsOnGoogleMobileAds
private fun GADPublisherPrivacyPersonalizationState.toCommon(): RequestConfiguration.PublisherPrivacyPersonalizationState =
    RequestConfiguration.PublisherPrivacyPersonalizationState.fromInt(this.toInt())
