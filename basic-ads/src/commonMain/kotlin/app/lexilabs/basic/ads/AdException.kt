package app.lexilabs.basic.ads

public class AdException(message: String? = null): Exception(
    message ?: "AdMob failed to obtain, load, or show an ad"
)