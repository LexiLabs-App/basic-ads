package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform

@DependsOnGoogleUserMessagingPlatform
@Composable
public fun ConsentPopup(
    activity: Any?,
    onFailure: (Exception) -> Unit = {}
){
    val consent by rememberConsent(activity)
    consent.requestConsentInfoUpdate(onError = onFailure)
}

@DependsOnGoogleUserMessagingPlatform
@Composable
public fun ConsentPopup(
    consent: Consent,
    onFailure: (Exception) -> Unit = {}
){
    consent.requestConsentInfoUpdate(onError = onFailure)
}