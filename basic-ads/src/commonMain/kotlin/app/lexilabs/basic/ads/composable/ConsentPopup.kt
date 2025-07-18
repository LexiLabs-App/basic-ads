package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform

/**
 * A composable function that requests and updates user consent information using the Google User Messaging Platform (UMP) SDK.
 * It is designed to be used within a Composable UI context.
 *
 * This function handles the process of checking the current consent status and requesting an update if necessary.
 * If a consent form is required and available, it will be presented to the user.
 *
 * This function relies on the [Consent] class to manage the underlying UMP SDK interactions.
 *
 * @param activity The current Android Activity. This is required by the UMP SDK to display the consent form.
 *                 It's typed as `Any?` to allow for flexibility, but it should be an instance of `android.app.Activity`.
 * @param onFailure A lambda function that will be invoked if an error occurs during the consent information update process.
 *                  It receives an [Exception] object describing the error. Defaults to an empty lambda.
 *
 * @see Consent
 * @see rememberConsent
 * @see DependsOnGoogleUserMessagingPlatform
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public fun ConsentPopup(
    activity: Any?,
    onFailure: (Exception) -> Unit = {}
){
    val consent by rememberConsent(activity)
    consent.requestConsentInfoUpdate(onError = onFailure)
}

/**
 * A Composable function that requests an update to the consent information.
 *
 * This function is used to ensure that the app has the latest consent status from the user.
 * It is typically called when the app starts or when the user's consent status might have changed.
 *
 * This function is annotated with `@DependsOnGoogleUserMessagingPlatform` because it relies on the
 * Google User Messaging Platform (UMP) SDK to manage user consent. The UMP SDK provides a
 * standardized way to obtain and manage user consent for personalized advertising.
 *
 * @param consent The [Consent] object that manages the consent state. This object is typically
 * obtained using the `rememberConsent` function.
 * @param onFailure A lambda function that is called if an error occurs while updating the consent
 * information. The lambda function takes an [Exception] object as a parameter, which provides
 * details about the error. By default, this parameter is an empty lambda function, which means
 * that errors are ignored.
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public fun ConsentPopup(
    consent: Consent,
    onFailure: (Exception) -> Unit = {}
){
    consent.requestConsentInfoUpdate(onError = onFailure)
}