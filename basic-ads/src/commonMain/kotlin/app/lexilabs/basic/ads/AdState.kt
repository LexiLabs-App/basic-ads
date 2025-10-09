package app.lexilabs.basic.ads

/**
 * Represents the different states of an ad during its lifecycle.
 */
public enum class AdState {
    /** The initial state before any ad operation has started. */
    NONE,
    /** The ad is currently being loaded. */
    LOADING,
    /** The ad has been successfully loaded and is ready to be shown. */
    READY,
    /** The ad is currently being displayed to the user. */
    SHOWING,
    /** The ad has been shown to the user. */
    SHOWN,
    /** The ad has been dismissed by the user. */
    DISMISSED,
    /** An error occurred while loading or showing the ad. */
    FAILING
}