package app.lexilabs.basic.ads

import cocoapods.GoogleUserMessagingPlatform.UMPConsentForm
import cocoapods.GoogleUserMessagingPlatform.UMPConsentInformation
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
public actual object Consent {

    public actual fun requestConsentInfoUpdate(activity: Any?, onError: (Exception) -> Unit) {
        UMPConsentInformation.sharedInstance().requestConsentInfoUpdateWithParameters(
            parameters = null,
            completionHandler = { updateError ->
                updateError?.let { onError(ConsentException(it.description)) }
            }
        )
        UMPConsentForm.loadAndPresentIfRequiredFromViewController(
            viewController = getCurrentViewController(),
            completionHandler = { loadError ->
                loadError?.let { onError(ConsentException(it.description)) }
            }
        )
    }

    public actual fun loadAndShowConsentForm(activity: Any?, onError: (Exception) -> Unit) {
        UMPConsentForm.loadWithCompletionHandler { umpConsentForm, loadError ->
            loadError?.let { onError(ConsentException(it.description)) }
            umpConsentForm?.let {
                UMPConsentForm.presentPrivacyOptionsFormFromViewController(
                    viewController = getCurrentViewController(),
                    completionHandler = { presentError ->
                        presentError?.let { onError(ConsentException(it.description)) }
                    }
                )
            }
        }
    }

    private fun getCurrentViewController(): UIViewController? {
        return UIApplication.sharedApplication().keyWindow()?.rootViewController?.presentedViewController ?:
        UIApplication.sharedApplication.keyWindow?.rootViewController
    }
}