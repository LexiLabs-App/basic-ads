package app.lexilabs.basic.ads.nativead

import platform.UIKit.UIViewContentMode

public fun ScaleType.toIos(): UIViewContentMode {
    return when(this) {
        ScaleType.ScaleAspectFill,
        ScaleType.BottomRight,
        ScaleType.TopRight,
        ScaleType.BottomLeft,
        ScaleType.Left,
        ScaleType.Bottom,
        ScaleType.ScaleAspectFit,
        ScaleType.TopLeft,
        ScaleType.ScaleToFill,
        ScaleType.Redraw,
        ScaleType.Right,
        ScaleType.Top -> UIViewContentMode.valueOf("UIViewContentMode" + this.name)
        ScaleType.CENTER -> UIViewContentMode.UIViewContentModeCenter
        else -> UIViewContentMode.UIViewContentModeScaleToFill
    }
}

public fun UIViewContentMode.toCommon(): ScaleType {
    return when(this) {
        UIViewContentMode.UIViewContentModeScaleAspectFill -> ScaleType.ScaleAspectFill
        UIViewContentMode.UIViewContentModeBottomRight -> ScaleType.BottomRight
        UIViewContentMode.UIViewContentModeTopRight -> ScaleType.TopRight
        UIViewContentMode.UIViewContentModeBottomLeft -> ScaleType.BottomLeft
        UIViewContentMode.UIViewContentModeLeft -> ScaleType.Left
        UIViewContentMode.UIViewContentModeBottom -> ScaleType.Bottom
        UIViewContentMode.UIViewContentModeScaleAspectFit -> ScaleType.ScaleAspectFit
        UIViewContentMode.UIViewContentModeTopLeft -> ScaleType.TopLeft
        UIViewContentMode.UIViewContentModeScaleToFill -> ScaleType.ScaleAspectFill
        UIViewContentMode.UIViewContentModeRedraw -> ScaleType.Redraw
        UIViewContentMode.UIViewContentModeRight -> ScaleType.Right
        UIViewContentMode.UIViewContentModeTop -> ScaleType.Top
        UIViewContentMode.UIViewContentModeCenter -> ScaleType.CENTER
        else -> ScaleType.ScaleToFill
    }
}