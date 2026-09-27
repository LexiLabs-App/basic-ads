@file:OptIn(ExperimentalForeignApi::class)

package app.lexilabs.basic.ads

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import swiftPMImport.app.lexilabs.basic.basic.ads.GADErrorDomain
import swiftPMImport.app.lexilabs.basic.basic.ads.GADMobileAds
import swiftPMImport.app.lexilabs.basic.basic.ads.GADPublisherPrivacyPersonalizationState
import swiftPMImport.app.lexilabs.basic.basic.ads.GADRequestConfiguration
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSNumber

@OptIn(ExperimentalForeignApi::class)
public actual object BasicAds {

    public actual val errorDomain: String? = GADErrorDomain

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
    @Composable
    public actual fun Initialize() {
        GADMobileAds.sharedInstance().startWithCompletionHandler(null)
    }

//    @OptIn(ExperimentalForeignApi::class)
//    public actual fun getInitializationStatus(): InitializationStatus? =
//        GADMobileAds.sharedInstance().initializationStatus

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    public actual fun DisableMediationAdapterInitialization() {
        GADMobileAds.sharedInstance().disableMediationInitialization()
    }

    @Composable
    public actual fun OpenDebugMenu(adUnitId: String) {
        GADMobileAds.sharedInstance.presentAdInspectorFromViewController(
            viewController = getCurrentViewController(),
            completionHandler = { it?.let { error -> throw AdException(error.localizedDescription) } }
        )
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
