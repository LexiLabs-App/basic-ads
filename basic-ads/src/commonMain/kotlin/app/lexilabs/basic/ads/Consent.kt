package app.lexilabs.basic.ads

public expect object Consent {

    public fun requestConsentInfoUpdate(activity: Any?, onError: (Exception) -> Unit)

    public fun loadAndShowConsentForm(activity: Any?, onError: (Exception) -> Unit)

}