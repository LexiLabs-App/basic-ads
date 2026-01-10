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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import app.lexilabs.basic.ads.ExperimentalBasicAdsFeature

@ExperimentalBasicAdsFeature
public actual class NativeAdDefault actual constructor(
    actual override val nativeAdData: NativeAdData?,
) : NativeAdTemplate(nativeAdData) {

    @Composable
    actual override fun Show(
        modifier: Modifier
    ) {
        require(nativeAdData != null) { "nativeAdData cannot be null" }
        Supervisor(modifier) {
            Box(modifier = Modifier.padding(8.dp).wrapContentHeight(Alignment.Top)) {
                Column(Modifier.align(Alignment.TopStart).wrapContentHeight(Alignment.Top)) {
                    Media()
                    Attribution(text = "ad")
                    Row {
                        nativeAdData.icon?.drawable?.let {
                            Icon { Image(bitmap = it.toBitmap().asImageBitmap(), "Icon") }
                        }
                    }
                    Column {
                        nativeAdData.headline?.let { Headline { BasicText(it) } }
                        nativeAdData.starRating?.let { BasicText("Rated $it") }
                    }

                    nativeAdData.body?.let { Body { BasicText(it) } }

                    Row(Modifier.align(Alignment.End).padding(5.dp)) {
                        nativeAdData.price?.let { BasicText(it) }
                        nativeAdData.store?.let { BasicText(it) }
                        nativeAdData.callToAction?.let {
                            NativeAdButton(it)
                        }
                    }
                }
            }
        }
    }
}