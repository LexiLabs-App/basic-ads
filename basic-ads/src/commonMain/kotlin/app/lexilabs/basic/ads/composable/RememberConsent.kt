package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.Consent
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform

@DependsOnGoogleUserMessagingPlatform
@Composable
public fun rememberConsent(activity: Any?): MutableState<Consent> {
    val consent = remember(activity) { mutableStateOf(Consent(activity)) }
    consent.value.isPrivacyOptionsRequired()
    consent.value.canRequestAds()
    return consent
}