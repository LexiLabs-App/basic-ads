package app.lexilabs.basic.ads.nativead

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public abstract class NativeAdTemplate public constructor(
    public override val nativeAdData: NativeAdData
): NativeAdScope {

    public abstract operator fun invoke(nativeAdData: NativeAdData): NativeAdTemplate
    @Composable
    public abstract fun Show(modifier: Modifier = Modifier)

    @Composable
    public abstract fun AdChoices(
        modifier: Modifier = Modifier
    )

    @Composable
    public abstract fun Advertiser(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun Attribution(
        text: String = "ad",
        modifier: Modifier = Modifier
    )

    @Composable
    public abstract fun Body(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun CallToAction(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun Headline(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun Icon(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun Media(
        modifier: Modifier = Modifier,
        scaleType: ScaleType? = null
    )

    @Composable
    public abstract fun Price(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun StarRating(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )

    @Composable
    public abstract fun Store(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    )
}