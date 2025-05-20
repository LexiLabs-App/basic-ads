package app.lexilabs.basic.ads

/**
 * Indicates an issue with AdMob.
 * @param message Typically a summary of what caused the error
 */
public class AdException(message: String? = null): Exception(
    "AdMob failed to obtain, load, or show an ad: $message"
)