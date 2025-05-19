package app.lexilabs.basic.ads

/**
 * Indicates an issue with UMP module for consent.
 * @param message Typically a summary of what caused the error
 */
public class ConsentException(message: String?): Exception(
    "UserMessagingPlatform failed to obtain, load, or show ConsentInformation: $message"
)