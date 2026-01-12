package app.lexilabs.basic.ads.nativead

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

public expect abstract class NativeAdTemplate public constructor(
    nativeAdData: NativeAdData?
): NativeAdScope {

    public override val nativeAdData: NativeAdData?

    public operator fun invoke(nativeAdData: NativeAdData?): NativeAdTemplate

    public abstract fun copy(nativeAdData: NativeAdData?): NativeAdTemplate
    @Composable
    public abstract fun Show(modifier: Modifier = Modifier)

    public interface SupervisorScope

    internal object SupervisorScopeInstance: SupervisorScope

    @Composable
    public fun Supervisor(
        modifier: Modifier = Modifier,
        content: @Composable SupervisorScope.() -> Unit
    )

    @Composable
    public fun SupervisorScope.AdChoices(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.Advertiser(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.Attribution(
        text: String = "ad",
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.Body(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.CallToAction(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.Headline(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.Icon(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.Media(
        modifier: Modifier = Modifier,
        scaleType: ScaleType? = null
    )

    @Composable
    public fun SupervisorScope.Price(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.StarRating(
        modifier: Modifier = Modifier,
        stars: @Composable (Double) -> Unit
    )

    @Composable
    public fun SupervisorScope.Store(
        modifier: Modifier = Modifier
    )

    @Composable
    public fun SupervisorScope.NativeAdButton(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = Color.White,
        backgroundColor: Color = Color.Black,
        shape: Shape = RectangleShape
    )
}