package rosa.pay.transaction.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import rosa.pay.app.ui.widget.AnimatedSlideInVertically
import rosa.pay.app.ui.theme.Dimen
import rosa.pay.app.ui.theme.PayColor
import kotlinx.coroutines.delay

private const val INFO_ANIMATION_DELAY_MS = 500L
private val BACK_CARD_SPACE = 50.dp
private val ICON_HEIGHT = 96.dp

@Composable
fun SlideInCard(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color = PayColor.Silver,
    iconResource: Int,
    topContent: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable ColumnScope.() -> Unit
) {
    // use these vars for animation
    var innerSpaceVisible: Boolean by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(INFO_ANIMATION_DELAY_MS)
        // to shrink inner space of info section
        innerSpaceVisible = false
    }
    Box(modifier = modifier) {
        AnimatedSlideInVertically(isSlidIn = true) {
            BackCard(innerSpaceVisible, background = secondaryColor, content = bottomContent) {
                FrontCard(
                    topMargin = ICON_HEIGHT
                        .div(2)
                        .plus(Dimen.defaultMarginDouble),
                    background = primaryColor,
                    content = topContent
                )
            }
        }
        Icon(ICON_HEIGHT, iconResource)
    }
}

@Composable
private fun BackCard(
    innerSpaceVisible: Boolean,
    background: Color,
    content: @Composable (ColumnScope.() -> Unit),
    frontCard: @Composable () -> Unit
) {
    Card(
        elevation = Dimen.defaultElevation,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.defaultCornerRadius))
            .background(background)
    ) {
        Column {
            frontCard()
            AnimatedVisibility(innerSpaceVisible, exit = shrinkOut()) {
                Spacer(modifier = Modifier.height(BACK_CARD_SPACE))
            }
            content()
        }
    }
}

@Composable
private fun FrontCard(
    topMargin: Dp,
    background: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.defaultCornerRadius))
            .background(color = background)
            .padding(
                top = topMargin,
                start = Dimen.defaultMargin,
                end = Dimen.defaultMargin,
                bottom = Dimen.defaultMarginDouble
            )
    ) {
        content()
    }
}

@Composable
private fun BoxScope.Icon(height: Dp, painterResource: Int) {
    Image(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .size(height)
            .offset(
                y = height
                    .div(-2)
                    .minus(4.dp)
            ),
        painter = painterResource(painterResource),
        contentDescription = ""
    )
}