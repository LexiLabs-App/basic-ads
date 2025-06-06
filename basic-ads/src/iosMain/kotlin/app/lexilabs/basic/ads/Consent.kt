package app.lexilabs.basic.ads

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import cocoapods.GoogleUserMessagingPlatform.UMPConsentForm
import cocoapods.GoogleUserMessagingPlatform.UMPConsentInformation
import cocoapods.GoogleUserMessagingPlatform.UMPPrivacyOptionsRequirementStatusRequired
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

/**
 * Create consent and privacy forms via the Google User Messaging Platform.
 * Types are found under the Privacy & messaging tab of your AdMob account.
 *
 * The UMP SDK attempts to display a privacy message created from
 * the AdMob Application ID set in your project.
 *
 * The [Consent] class requires the Activity be a non-null Context on Android
 * @param activity [require] non-null Activity Context on Android. All other platforms can pass `null`
 */
@OptIn(ExperimentalForeignApi::class)
@DependsOnGoogleUserMessagingPlatform
public actual class Consent actual constructor(activity: Any?) {

    private val _canRequestAds: MutableState<Boolean> = mutableStateOf(false)
    public actual val canRequestAds: Boolean by _canRequestAds

    private val _isPrivacyOptionsRequired: MutableState<Boolean> = mutableStateOf(false)
    public actual val privacyOptionsRequired: Boolean by _isPrivacyOptionsRequired

    /**
     * Gets the user's consent information
     *
     * You should request an update of the user's consent information at every app launch,
     * using [requestConsentInfoUpdate]. This request checks the following:
     *
     * __Whether consent is required.__ For example, consent is required for
     * the first time, or the previous consent decision expired.
     *
     * __Whether a privacy options entry point is required.__
     * Some privacy messages require apps to allow users to modify their
     * privacy options at any time.
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public actual fun requestConsentInfoUpdate(onError: (Exception) -> Unit) {
        UMPConsentInformation.sharedInstance().requestConsentInfoUpdateWithParameters(
            parameters = null,
            completionHandler = { updateError ->
                if (updateError != null) {
                    onError(ConsentException(updateError.description))
                } else {
                    UMPConsentForm.loadAndPresentIfRequiredFromViewController(
                        viewController = getCurrentViewController(),
                        completionHandler = { loadError ->
                            loadError?.let { onError(ConsentException(it.description)) }
                        }
                    )
                }
            }
        )
    }

    /**
     * Gets the user's consent information
     *
     * You should request an update of the user's consent information at every app launch,
     * using [requestConsentInfoUpdate]. This request checks the following:
     *
     * __Whether consent is required.__ For example, consent is required for
     * the first time, or the previous consent decision expired.
     *
     * __Whether a privacy options entry point is required.__
     * Some privacy messages require apps to allow users to modify their
     * privacy options at any time.
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public actual fun requestConsentInfoUpdate(params: ConsentRequestParameters, onError: (Exception) -> Unit) {
        UMPConsentInformation.sharedInstance().requestConsentInfoUpdateWithParameters(
            parameters = params.ios,
            completionHandler = { updateError ->
                if (updateError != null) {
                    onError(ConsentException(updateError.description))
                } else {
                    UMPConsentForm.loadAndPresentIfRequiredFromViewController(
                        viewController = getCurrentViewController(),
                        completionHandler = { loadError ->
                            loadError?.let { onError(ConsentException(it.description)) }
                        }
                    )
                }
            }
        )
    }

    /**
     * Load and present the privacy message form
     *
     * After you have received the most up-to-date consent status,
     * call [loadAndShowConsentForm] to load any forms required to collect user consent.
     * After loading, the forms present immediately.
     *
     * __Key Point:__ If no privacy message forms require collection of user
     * consent prior to requesting ads, the callback is invoked immediately.
     *
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public actual fun loadAndShowConsentForm(onError: (Exception) -> Unit) {
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

    /**
     * Check if a privacy options entry point is required
     *
     * After you have called [requestConsentInfoUpdate], check
     * [Consent.privacyOptionsRequired] to determine if a privacy
     * options entry point is required for your app.
     *
     * If an entry point is required, add a visible and interactable
     * UI element to your app that presents the privacy options form.
     *
     * If a privacy entry point is not required, configure your UI element
     * to be not visible and interactable.
     */
    public actual fun isPrivacyOptionsRequired(): Boolean {
        _isPrivacyOptionsRequired.value = UMPConsentInformation.sharedInstance.privacyOptionsRequirementStatus ==
                UMPPrivacyOptionsRequirementStatusRequired
        return _isPrivacyOptionsRequired.value
    }

    /**
     * Present the privacy options form
     *
     * When the user interacts with your element, present the privacy options form:
     * @param onDismissed lambda which executes when form is exited by user
     * @param onError lambda which passes a [ConsentException] on failure
     */
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

    /**
     * Request ads with user consent
     *
     * Before requesting ads, use [Consent.canRequestAds] to
     * check if you've obtained consent from the user.
     */
    public actual fun canRequestAds(): Boolean {
        _canRequestAds.value = UMPConsentInformation.sharedInstance.canRequestAds
        return _canRequestAds.value
    }

    /**
     * Reset consent state
     *
     * When testing your app with the UMP SDK, you might find it helpful to reset
     * the state of the SDK so that you can simulate a user's first install experience.
     *
     * All SDKs provide the reset() method to do this.
     */
    public actual fun reset(){
        UMPConsentInformation.sharedInstance.reset()
    }
}