package app.lexilabs.basic.ads.nativead

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public expect class NativeAdDefault: NativeAdTemplate {
    override val nativeAdData: NativeAdData

    override operator fun invoke(nativeAdData: NativeAdData): NativeAdTemplate

    @Composable
    override fun Show(modifier: Modifier)

    @Composable
    override fun Advertiser(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun Body(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun CallToAction(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun AdChoices(modifier: Modifier)

    @Composable
    override fun Headline(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun Icon(
        modifier: Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    override fun Media(
        modifier: Modifier,
        scaleType: ScaleType?
    )

    @Composable
    override fun Price(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun StarRating(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun Store(
        modifier: Modifier,
        content: @Composable (() -> Unit)
    )

    @Composable
    override fun Attribution(
        text: String,
        modifier: Modifier
    )
}