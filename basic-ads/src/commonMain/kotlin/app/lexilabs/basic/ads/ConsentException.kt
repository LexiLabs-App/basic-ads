package app.lexilabs.basic.ads

public class ConsentException(message: String?): Exception(
    message ?: "UserMessagingPlatform failed to obtain, load, or show ConsentInformation"
)