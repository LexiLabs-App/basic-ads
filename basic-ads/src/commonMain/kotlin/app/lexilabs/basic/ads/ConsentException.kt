package app.lexilabs.basic.ads

/**
 * Indicates an issue with the User Messaging Platform (UMP) module, which is responsible for
 * obtaining, loading, or showing consent information from the user.
 *
 * This exception is typically thrown when the UMP encounters an error during its operation,
 * such as failing to connect to the server, experiencing a network issue, or encountering
 * an unexpected internal error.
 *
 * @param message A descriptive message summarizing the cause of the error. This message
 *                provides additional context to help diagnose and resolve the issue.
 */
@DependsOnGoogleUserMessagingPlatform
public class ConsentException(message: String?): Exception(
    "UserMessagingPlatform failed to obtain, load, or show ConsentInformation: $message"
)