package app.lexilabs.basic.ads

/**
 * Create consent and privacy forms via the Google User Messaging Platform.
 * Types are found under the Privacy & messaging tab of your AdMob account.
 *
 * The UMP SDK attempts to display a privacy message created from
 * the AdMob Application ID set in your project.
 *
 * The [Consent] class [require]s the Activity be a non-null Context on Android
 * @param activity [require]s non-null Activity Context on Android. All other platforms can pass `null`
 */
public expect class Consent(activity: Any?) {

    /**
     * Gets the user's consent information
     *
     * You should request an update of the user's consent information at every app launch,
     * using [requestConsentInfoUpdate]. This request checks the following:
     *
     * __Whether consent is required.__ For example, consent is required for
     * the first time, or the previous consent decision expired.
     *
     * __Whether a privacy options entry point is required.__
     * Some privacy messages require apps to allow users to modify their
     * privacy options at any time.
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public fun requestConsentInfoUpdate(onError: (Exception) -> Unit)

    /**
     * Gets the user's consent information
     *
     * You should request an update of the user's consent information at every app launch,
     * using [requestConsentInfoUpdate]. This request checks the following:
     *
     * __Whether consent is required.__ For example, consent is required for
     * the first time, or the previous consent decision expired.
     *
     * __Whether a privacy options entry point is required.__
     * Some privacy messages require apps to allow users to modify their
     * privacy options at any time.
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public fun requestConsentInfoUpdate(params: ConsentRequestParameters, onError: (Exception) -> Unit)

    /**
     * Load and present the privacy message form
     *
     * After you have received the most up-to-date consent status,
     * call [loadAndShowConsentForm] to load any forms required to collect user consent.
     * After loading, the forms present immediately.
     *
     * __Key Point:__ If no privacy message forms require collection of user
     * consent prior to requesting ads, the callback is invoked immediately.
     *
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public fun loadAndShowConsentForm(onError: (Exception) -> Unit)

    /**
     * Check if a privacy options entry point is required
     *
     * After you have called [requestConsentInfoUpdate], check
     * [Consent.isPrivacyOptionsRequired] to determine if a privacy
     * options entry point is required for your app.
     *
     * If an entry point is required, add a visible and interactable
     * UI element to your app that presents the privacy options form.
     *
     * If a privacy entry point is not required, configure your UI element
     * to be not visible and interactable.
     */
    public fun isPrivacyOptionsRequired(): Boolean

    /**
     * Present the privacy options form
     *
     * When the user interacts with your element, present the privacy options form:
     * @param onDismissed lambda which executes when form is exited by user
     * @param onError lambda which passes a [ConsentException] on failure
     */
    public fun showPrivacyOptionsForm(onDismissed: () -> Unit, onError: (Exception) -> Unit)

    /**
     * Request ads with user consent
     *
     * Before requesting ads, use [Consent.canRequestAds] to
     * check if you've obtained consent from the user.
     */
    public fun canRequestAds(): Boolean

    /**
     * Reset consent state
     *
     * When testing your app with the UMP SDK, you might find it helpful to reset
     * the state of the SDK so that you can simulate a user's first install experience.
     *
     * All SDKs provide the reset() method to do this.
     */
    public fun reset()
}