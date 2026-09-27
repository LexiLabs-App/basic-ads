package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform

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
 * @param onLoaded A lambda function that is called when the consent form is loaded.
 * @param onShown A lambda function that is called when the consent form is shown to the user.
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public fun ConsentPopup(
    consent: Consent,
    onFailure: (Exception) -> Unit = {},
    onLoaded: () -> Unit = {},
    onShown: () -> Unit = {}
){
    consent.requestConsentInfoUpdate(
        onCompletion = {
            consent.loadAndShowConsentForm(
                onLoaded = onLoaded,
                onShown = onShown,
                onError = onFailure
            )
        }
    )
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
 * @param onFailure A lambda function that is called if an error occurs while updating the consent
 * information. The lambda function takes an [Exception] object as a parameter, which provides
 * details about the error. By default, this parameter is an empty lambda function, which means
 * that errors are ignored.
 * @param onLoaded A lambda function that is called when the consent form is loaded.
 * @param onShown A lambda function that is called when the consent form is shown to the user.
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public fun ConsentPopup(
    onFailure: (Exception) -> Unit = {},
    onLoaded: () -> Unit = {},
    onShown: () -> Unit = {}
){
    val consent by rememberConsent()
    consent.requestConsentInfoUpdate(
        onCompletion = {
            consent.loadAndShowConsentForm(
                onLoaded = onLoaded,
                onShown = onShown,
                onError = onFailure
            )
        }
    )
}