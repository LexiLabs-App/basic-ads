package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform
import app.lexilabs.basic.ads.ExperimentalBasicAds

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
@ExperimentalBasicAds
@DependsOnGoogleUserMessagingPlatform
@Composable
public fun rememberConsent(activity: Any?): MutableState<Consent> {
    val consent = remember(activity) { mutableStateOf(Consent(activity)) }
    consent.value.isPrivacyOptionsRequired()
    consent.value.canRequestAds()
    return consent
}