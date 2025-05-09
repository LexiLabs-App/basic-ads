package app.lexilabs.basic.ads

import android.app.Activity
import app.lexilabs.basic.logging.Log
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform


public actual object Consent {

    private const val TAG = "Consent"

    public actual fun requestConsentInfoUpdate(activity: Any?, onError: (Exception) -> Unit) {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        val context = activity as Activity
        val consentInformation = UserMessagingPlatform.getConsentInformation(context)
        val params = ConsentRequestParameters.Builder().build()
        consentInformation.requestConsentInfoUpdate(
            context,
            params,
            {
                if (consentInformation.isConsentFormAvailable) {
                    loadAndShowConsentForm(context) {
                        onError(it)
                    }
                }
            },
            { Log.w(TAG, "requestConsentInfoUpdate:failure: $it") }
        )
    }

    public actual fun loadAndShowConsentForm(activity: Any?, onError: (Exception) -> Unit) {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        val context = activity as Activity
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(context) { error ->
            onError(ConsentException(error?.message))
        }
    }
}