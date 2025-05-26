package app.lexilabs.basic.ads

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.google.android.ump.ConsentInformation as AndroidConsentInformation
import com.google.android.ump.ConsentRequestParameters as AndroidConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

/**
 * Create consent and privacy forms via the Google User Messaging Platform.
 * Types are found under the Privacy & messaging tab of your AdMob account.
 *
 * The UMP SDK attempts to display a privacy message created from
 * the AdMob Application ID set in your project.
 *
 * The [Consent] class [require]s a non-null [Activity] on Android
 * @param activity [require] non-null [Activity] on Android. All other platforms can pass `null`
 */
@ExperimentalBasicAds
@DependsOnGoogleUserMessagingPlatform
public actual class Consent actual constructor (activity: Any?) {

    private val context: Activity
    private val consentInformation: AndroidConsentInformation

    private val _canRequestAds: MutableState<Boolean> = mutableStateOf(false)
    public actual val canRequestAds: Boolean by _canRequestAds

    private val _privacyOptionsRequired: MutableState<Boolean> = mutableStateOf(false)
    public actual val privacyOptionsRequired: Boolean by _privacyOptionsRequired

    init {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        context = activity as Activity
        consentInformation = UserMessagingPlatform.getConsentInformation(context)
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
    public actual fun requestConsentInfoUpdate(onError: (Exception) -> Unit) {
        val params = AndroidConsentRequestParameters.Builder().build()
        consentInformation.requestConsentInfoUpdate(
            context,
            params,
            {
                if (consentInformation.isConsentFormAvailable) {
                    loadAndShowConsentForm { onError(it) }
                }
            },
            { onError(ConsentException(it.message)) }
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

        consentInformation.requestConsentInfoUpdate(
            context,
            params.android,
            {
                if (consentInformation.isConsentFormAvailable) {
                    loadAndShowConsentForm { onError(it) }
                }
            },
            { onError(ConsentException(it.message)) }
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
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(context) { error ->
            onError(ConsentException(error?.message))
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
        _privacyOptionsRequired.value = consentInformation.privacyOptionsRequirementStatus ==
                AndroidConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
        return _privacyOptionsRequired.value
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
        UserMessagingPlatform.showPrivacyOptionsForm(context) { error ->
            error?.let { onError(ConsentException(it.message)) }
            onDismissed()
        }
    }

    /**
     * Request ads with user consent
     *
     * Before requesting ads, use [Consent.canRequestAds] to
     * check if you've obtained consent from the user.
     */
    public actual fun canRequestAds(): Boolean {
        _canRequestAds.value = consentInformation.canRequestAds()
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
        consentInformation.reset()
    }
}