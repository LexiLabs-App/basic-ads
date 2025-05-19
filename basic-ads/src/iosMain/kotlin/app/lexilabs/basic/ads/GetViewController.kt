package app.lexilabs.basic.ads

import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UITabBarController
import platform.UIKit.UIViewController
import platform.UIKit.UIWindowScene

internal fun getCurrentViewController(): UIViewController? {
    val rootViewController = getRootViewController()
    if (rootViewController is UINavigationController) {
        return rootViewController.visibleViewController
    }
    if (rootViewController is UITabBarController) {
        return rootViewController.selectedViewController
    }
    rootViewController?.presentedViewController?.let {
        return it
    }
    return rootViewController
}

internal fun getRootViewController(): UIViewController? =
    UIApplication.sharedApplication.connectedScenes.map { (it as UIWindowScene).keyWindow }
        .first()?.rootViewController