package app.lexilabs.basic.ads

/**
 * Manages consent and privacy forms using the Google User Messaging Platform (UMP) SDK.
 *
 * This class helps you gather consent from your users, which is often a legal requirement (e.g., GDPR).
 * The UMP SDK will attempt to display a privacy message that you configure in the
 * "Privacy & messaging" tab of your AdMob account.
 *
 * On Android, this class requires an `Activity` context. For other platforms, you can pass `null`.
 *
 * @property activity The activity context, required on Android.
 */
@Suppress("unused")
@DependsOnGoogleUserMessagingPlatform
public expect class Consent(activity: Any?) {

    /**
     * Indicates whether you can request ads.
     *
     * Before requesting ads, check this property to ensure you have obtained the necessary consent from the user.
     */
    public val canRequestAds: Boolean

    /**
     * Indicates whether a privacy options entry point is required.
     *
     * After calling [requestConsentInfoUpdate], check this property to determine if your app
     * needs to provide a way for users to modify their privacy settings.
     *
     * If `true`, your app's UI should include a button or link that, when clicked,
     * calls [showPrivacyOptionsForm].
     */
    public val privacyOptionsRequired: Boolean

    /**
     * Requests an update of the user's consent information.
     *
     * You should call this at every app launch to determine if consent is required
     * and whether a privacy options entry point needs to be shown.
     *
     * @param onCompletion Called when the operation is successful.
     * @param onError Called when the operation fails, passing an [Exception].
     */
    public fun requestConsentInfoUpdate(
        onCompletion: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    )

    /**
     * Requests an update of the user's consent information with specific parameters.
     *
     * You should call this at every app launch. This overload allows you to provide
     * [ConsentRequestParameters] for the request, for example to specify debug settings.
     *
     * @param params The parameters for the consent information request.
     * @param onCompletion Called when the operation is successful.
     * @param onError Called when the operation fails, passing an [Exception].
     */
    public fun requestConsentInfoUpdate(
        params: ConsentRequestParameters,
        onCompletion: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    )

    /**
     * Loads and shows a consent form if one is required.
     *
     * After getting the latest consent status with [requestConsentInfoUpdate], call this function.
     * If consent is needed, it loads and displays the appropriate form to the user.
     * If no form is needed, [onLoaded] is called immediately.
     *
     * @param onLoaded Called when the form is loaded (or if no form is needed).
     * @param onShown Called when the form is shown to the user.
     * @param onError Called when an error occurs during loading or showing the form, passing an [Exception].
     */
    public fun loadAndShowConsentForm(
        onLoaded: () -> Unit = {},
        onShown: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    )

    /**
     * Checks if a privacy options entry point is required.
     *
     * @return `true` if a privacy options entry point is required, `false` otherwise.
     * @see privacyOptionsRequired
     * @deprecated Use the [privacyOptionsRequired] property instead.
     */
    public fun isPrivacyOptionsRequired(): Boolean

    /**
     * Shows the privacy options form.
     *
     * This function should be called when a user wants to change their privacy settings.
     * It should only be called if [privacyOptionsRequired] is `true`.
     *
     * @param onDismissed Called when the user dismisses the form.
     * @param onError Called if an error occurs while showing the form, passing an [Exception].
     */
    public fun showPrivacyOptionsForm(
        onDismissed: () -> Unit,
        onError: (Exception) -> Unit = {}
    )

    /**
     * Checks if ads can be requested.
     *
     * @return `true` if ads can be requested, `false` otherwise.
     * @see canRequestAds
     * @deprecated Use the [canRequestAds] property instead.
     */
    public fun canRequestAds(): Boolean

    /**
     * Resets the consent state.
     *
     * This is useful for testing, to simulate a first-time install experience.
     * It clears the consent status for the current user.
     */
    public fun reset()
}