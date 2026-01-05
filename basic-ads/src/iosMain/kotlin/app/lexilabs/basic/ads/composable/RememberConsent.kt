package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.ConsentDebugSettings
import app.lexilabs.basic.ads.ConsentRequestParameters
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform

/**
 * Composable function to remember and manage user consent for ads.
 *
 * This function creates and remembers a [Consent] object, which handles interactions
 * with the Google User Messaging Platform (UMP) SDK to obtain and manage user consent
 * for personalized advertising.
 *
 * It initializes the consent state and checks if privacy options are required and if ads can be requested.
 *
 * @return A [MutableState] holding the [Consent] object. This allows observing and reacting
 *         to changes in the consent status within your Composable UI.
 *
 * @see Consent
 * @see DependsOnGoogleUserMessagingPlatform
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public actual fun rememberConsent(): MutableState<Consent> {
    val consent = remember(null) { mutableStateOf(Consent(null)) }
    consent.value.isPrivacyOptionsRequired()
    consent.value.canRequestAds()
    return consent
}

/**
 * Composable function to remember and manage user consent for ads.
 *
 * This function creates and remembers a [Consent] object, which handles interactions
 * with the Google User Messaging Platform (UMP) SDK to obtain and manage user consent
 * for personalized advertising.
 *
 * It initializes the consent state and checks if privacy options are required and if ads can be requested.
 *
 * @param requestParameters The [ConsentRequestParameters] to use for consent requests.
 * @return A [MutableState] holding the [Consent] object. This allows observing and reacting
 *         to changes in the consent status within your Composable UI.
 *
 * @see Consent
 * @see DependsOnGoogleUserMessagingPlatform
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public actual fun rememberConsent(requestParameters: ConsentRequestParameters): MutableState<Consent> {
    val consent = remember(null) { mutableStateOf(Consent(null)) }
    consent.value.requestConsentInfoUpdate(
        params = requestParameters,
        onCompletion = {
            consent.value.isPrivacyOptionsRequired()
            consent.value.canRequestAds()
        }
    )
    return consent
}

/**
 * Composable function to remember and manage user consent for ads.
 *
 * This function creates and remembers a [Consent] object, which handles interactions
 * with the Google User Messaging Platform (UMP) SDK to obtain and manage user consent
 * for personalized advertising.
 *
 * It initializes the consent state and checks if privacy options are required and if ads can be requested.
 *
 * @param debugSettings The [ConsentDebugSettings] to use for debugging purposes.
 * @return A [MutableState] holding the [Consent] object. This allows observing and reacting
 *         to changes in the consent status within your Composable UI.
 *
 * @see Consent
 * @see DependsOnGoogleUserMessagingPlatform
 */
@DependsOnGoogleUserMessagingPlatform
@Composable
public actual fun rememberConsent(debugSettings: ConsentDebugSettings): MutableState<Consent> {
    val consent = remember(null) { mutableStateOf(Consent(null)) }
    val params = ConsentRequestParameters.Builder().setConsentDebugSettings(debugSettings).build()
    consent.value.requestConsentInfoUpdate(
        params = params,
        onCompletion = {
            consent.value.isPrivacyOptionsRequired()
            consent.value.canRequestAds()
        }
    )
    return consent
}