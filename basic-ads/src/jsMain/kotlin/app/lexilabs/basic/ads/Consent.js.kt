package app.lexilabs.basic.ads

public actual class Consent actual constructor(activity: Any?) {


    public actual fun requestConsentInfoUpdate(onError: (Exception) -> Unit) {
        /** DO NOTHING **/
    }

    public actual fun loadAndShowConsentForm(onError: (Exception) -> Unit) {
        /** DO NOTHING **/
    }

    public actual fun isPrivacyOptionsRequired(): Boolean = false

    public actual fun showPrivacyOptionsForm(
        onDismissed: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        /** DO NOTHING **/
    }

    public actual fun canRequestAds(): Boolean = false

}