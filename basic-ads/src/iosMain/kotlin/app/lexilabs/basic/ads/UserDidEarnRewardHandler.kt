package app.lexilabs.basic.ads

import cocoapods.Google_Mobile_Ads_SDK.GADUserDidEarnRewardHandler

public class UserDidEarnRewardHandler(
    private val onRewardEarned: () -> Unit
): GADUserDidEarnRewardHandler {
    override fun invoke() {
        onRewardEarned()
    }
}