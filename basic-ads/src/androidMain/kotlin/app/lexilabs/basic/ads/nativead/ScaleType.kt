package app.lexilabs.basic.ads.nativead

import android.widget.ImageView

public fun ScaleType.toAndroid(): ImageView.ScaleType {
    return when(this) {
        ScaleType.CENTER,
        ScaleType.CENTER_CROP,
        ScaleType.CENTER_INSIDE,
        ScaleType.FIT_CENTER,
        ScaleType.FIT_END,
        ScaleType.FIT_START,
        ScaleType.FIT_XY,
        ScaleType.MATRIX -> ImageView.ScaleType.valueOf(this.name)
        else -> ImageView.ScaleType.FIT_CENTER
    }
}

public fun ImageView.ScaleType.toCommon(): ScaleType =
    ScaleType.valueOf(this.name)