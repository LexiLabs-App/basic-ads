package app.lexilabs.basic.ads

import android.app.Activity
import com.google.android.ump.UserMessagingPlatform
import com.google.android.ump.ConsentInformation as AndroidConsentInformation
import com.google.android.ump.ConsentRequestParameters as AndroidConsentRequestParameters

@DependsOnGoogleUserMessagingPlatform
public actual class Consent actual constructor(private val activity: Any?) {

    private val context: Activity
        get() = activity as Activity
    private val consentInformation: AndroidConsentInformation
        get() = UserMessagingPlatform.getConsentInformation(context)

    public actual val canRequestAds: Boolean
        get() = canRequestAds()

    public actual val privacyOptionsRequired: Boolean
        get() = isPrivacyOptionsRequired()

    init {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
    }

    public actual fun requestConsentInfoUpdate(
        onCompletion: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val params = AndroidConsentRequestParameters.Builder().build()
        consentInformation.requestConsentInfoUpdate(
            context,
            params,
            {
                isPrivacyOptionsRequired()
                canRequestAds()
                onCompletion()
            },
            { onError(ConsentException(it.message)) }
        )
    }

    public actual fun requestConsentInfoUpdate(
        params: ConsentRequestParameters,
        onCompletion: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        consentInformation.requestConsentInfoUpdate(
            context,
            params.android,
            {
                isPrivacyOptionsRequired()
                canRequestAds()
                onCompletion()
            },
            { formError -> onError(ConsentException(formError.message)) }
        )
    }

    public actual fun loadAndShowConsentForm(
        onLoaded: () -> Unit,
        onShown: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(context) { error ->
            if (error != null) {
                onError(ConsentException(error.message))
            } else {
                canRequestAds()
                onLoaded()
                onShown()
            }
        }
    }

    public actual fun isPrivacyOptionsRequired(): Boolean {
        return consentInformation.privacyOptionsRequirementStatus == AndroidConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
    }

    public actual fun showPrivacyOptionsForm(
        onDismissed: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(context) { error ->
            error?.let { onError(ConsentException(it.message)) }
            onDismissed()
        }
    }

    public actual fun canRequestAds(): Boolean {
        return consentInformation.canRequestAds()
    }

    public actual fun reset() {
        consentInformation.reset()
    }
}