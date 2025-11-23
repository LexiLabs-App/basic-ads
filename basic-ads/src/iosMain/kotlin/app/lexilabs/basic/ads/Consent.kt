package app.lexilabs.basic.ads

import cocoapods.GoogleUserMessagingPlatform.UMPConsentForm
import cocoapods.GoogleUserMessagingPlatform.UMPConsentInformation
import cocoapods.GoogleUserMessagingPlatform.UMPPrivacyOptionsRequirementStatusRequired
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
@DependsOnGoogleUserMessagingPlatform
public actual class Consent actual constructor(activity: Any?) {

    public actual val canRequestAds: Boolean
        get() = canRequestAds()

    public actual val privacyOptionsRequired: Boolean
        get() = isPrivacyOptionsRequired()

    public actual fun requestConsentInfoUpdate(
        onCompletion: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        UMPConsentInformation.sharedInstance().requestConsentInfoUpdateWithParameters(
            parameters = null,
            completionHandler = { updateError ->
                if (updateError != null) {
                    onError(ConsentException(updateError.description))
                } else {
                    isPrivacyOptionsRequired()
                    canRequestAds()
                    onCompletion()
                }
            }
        )
    }

    public actual fun requestConsentInfoUpdate(
        params: ConsentRequestParameters,
        onCompletion: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        UMPConsentInformation.sharedInstance().requestConsentInfoUpdateWithParameters(
            parameters = params.ios,
            completionHandler = { updateError ->
                if (updateError != null) {
                    onError(ConsentException(updateError.description))
                } else {
                    isPrivacyOptionsRequired()
                    canRequestAds()
                    onCompletion()
                }
            }
        )
    }

    public actual fun loadAndShowConsentForm(
        onLoaded: () -> Unit,
        onShown: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        UMPConsentForm.loadAndPresentIfRequiredFromViewController(
            viewController = getCurrentViewController(),
            completionHandler = { loadError ->
                if (loadError != null) {
                    onError(ConsentException(loadError.description))
                } else {
                    canRequestAds()
                    onLoaded()
                    onShown()
                }
            }
        )
    }

    public actual fun isPrivacyOptionsRequired(): Boolean {
        return UMPConsentInformation.sharedInstance.privacyOptionsRequirementStatus == UMPPrivacyOptionsRequirementStatusRequired
    }

    public actual fun showPrivacyOptionsForm(
        onDismissed: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        UMPConsentForm.presentPrivacyOptionsFormFromViewController(
            viewController = getCurrentViewController(),
            completionHandler = { error: NSError? ->
                error?.let { onError(ConsentException(it.description)) }
                onDismissed()
            }
        )
    }

    public actual fun canRequestAds(): Boolean {
        return UMPConsentInformation.sharedInstance.canRequestAds
    }

    public actual fun reset() {
        UMPConsentInformation.sharedInstance.reset()
    }
}