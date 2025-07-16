package app.lexilabs.basic.ads

/**
 * Represents an exception that occurred during an ad operation.
 *
 * This exception is thrown when AdMob encounters an issue while attempting to obtain, load, or
 * display an ad. The accompanying message provides a summary of the underlying cause of the error.
 *
 * @param message A descriptive message detailing the nature of the ad-related error. This message
 *                typically summarizes the specific problem encountered during the ad operation.
 */
public class AdException(message: String? = null): Exception(
    "AdMob failed to obtain, load, or show an ad: $message"
)