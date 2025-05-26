package app.lexilabs.basic.ads

/**
 * The different states of an Ad
 * @property NONE Nothing has happened yet
 * @property LOADING The ad is loading
 * @property READY The ad was loaded and is ready to be shown
 * @property SHOWING The ad is playing now
 * @property SHOWN The ad has finished playing
 * @property DISMISSED The ad has been closed by the user but not yet reloaded
 * @property FAILING The ad failed and has not recovered yet
 */
public enum class AdState {
    /** Nothing has happened yet **/
    NONE,
    /** The ad is loading **/
    LOADING,
    /** The ad was loaded and is ready to be shown **/
    READY,
    /** The ad is playing now **/
    SHOWING,
    /** The ad has finished playing **/
    SHOWN,
    /** The ad has been closed by the user but not yet reloaded **/
    DISMISSED,
    /** The ad failed and has not recovered yet **/
    FAILING
}