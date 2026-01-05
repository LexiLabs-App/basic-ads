package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.ConsentDebugSettings
import app.lexilabs.basic.ads.ConsentRequestParameters
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
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
public expect fun rememberConsent(): MutableState<Consent>

/**
 * Composable function to remember and manage user consent for ads.
 *
 * This function creates and remembers a [Consent] object, which handles interactions
 * with the Google User Messaging Platform (UMP) SDK to obtain and manage user consent
 * for personalized advertising.
 *
 * It initializes the consent state and checks if privacy options are required and if ads can be requested.
 *
 * @param activity The current Activity context. This is crucial for the UMP SDK to function correctly.
 *                 It's recommended to pass the result of `LocalContext.current as? Activity`.
 * @return A [MutableState] holding the [Consent] object. This allows observing and reacting
 *         to changes in the consent status within your Composable UI.
 *
 * @see Consent
 * @see DependsOnGoogleUserMessagingPlatform
 */
@DependsOnGoogleUserMessagingPlatform
@Deprecated("The `activity` argument is no longer required as of v1.1.0-beta01")
@Composable
public fun rememberConsent(
    activity: Any?
): MutableState<Consent> {
    val consent = remember(activity) { mutableStateOf(Consent(activity)) }
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
@DependsOnGoogleMobileAds
@DependsOnGoogleUserMessagingPlatform
@Composable
public expect fun rememberConsent(
    requestParameters: ConsentRequestParameters
): MutableState<Consent>

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
@DependsOnGoogleMobileAds
@DependsOnGoogleUserMessagingPlatform
@Composable
public expect fun rememberConsent(
    debugSettings: ConsentDebugSettings
): MutableState<Consent>