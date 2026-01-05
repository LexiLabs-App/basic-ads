package app.lexilabs.basic.ads.composable

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import app.lexilabs.basic.ads.Consent
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
    val activity = LocalContext.current as Activity?
    val consent = remember(activity) { mutableStateOf(Consent(activity)) }
    consent.value.isPrivacyOptionsRequired()
    consent.value.canRequestAds()
    return consent
}