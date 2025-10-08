package app.lexilabs.basic.ads.nativead

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public expect open class NativeAdDefault : NativeAdTemplate {

    override val nativeAdData: NativeAdData?

    public constructor(nativeAdData: NativeAdData? = null)

    override operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate

    override fun copy(nativeAdData: NativeAdData?): NativeAdTemplate

    @Composable
    override fun Show(modifier: Modifier)

    @Composable
    public fun Supervisor(
        modifier: Modifier,
        content: @Composable () -> Unit
    )

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