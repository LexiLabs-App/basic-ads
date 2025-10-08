/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.lexilabs.basic.ads.nativead

import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * A CompositionLocal that can provide a `NativeAdView` to ad attributes such as `NativeHeadline`.
 */
internal val LocalNativeAdView = staticCompositionLocalOf<NativeAdView?> { null }

/**
 * This is the Compose wrapper for a NativeAdView.
 *
 * @param modifier The modifier to apply to the native ad.
 * @param content A composable function that defines the rest of the native ad view's elements,
 * as well as the `NativeAd` object containing the ad assets to be displayed in this view.
 */
@Composable
public fun NativeAdView(
    nativeAd: NativeAdData,
    modifier: Modifier = Modifier,
    content: @Composable NativeAdScope.(NativeAdData) -> Unit,
) {
    val localContext = LocalContext.current
    val nativeAdView = remember { NativeAdView(localContext).apply { id = View.generateViewId() } }

    AndroidView(
        factory = {
            nativeAdView.apply {
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                addView(
                    ComposeView(context).apply {
                        layoutParams =
                            ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                        setContent {
                            // Set `nativeAdView` as the current LocalNativeAdView so that
                            // `content` can access the `NativeAdView` via `LocalNativeAdView.current`.
                            // This would allow ad attributes (such as `NativeHeadline`) to attribute
                            // its contained View subclass via setter functions (e.g. nativeAdView.headlineView =
                            // view)
                            CompositionLocalProvider(LocalNativeAdView provides nativeAdView) {
                                val scope = NativeAdScopeInstance(nativeAd)
                                scope.content(nativeAd)
                            }
                        }
                    }
                )
            }
        },
        modifier = modifier,
    )
    SideEffect { nativeAdView.setNativeAd(nativeAd.android) }
}