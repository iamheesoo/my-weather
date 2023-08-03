package com.example.myweather.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

/**
 * LazyColumn.background = Transparent 일 때
 * 위로 스크롤 시 stickyHeader 뒤로 columnItem이 겹쳐서 노출되기 때문에
 * ConstraintLayout으로 두 범위를 분할하여
 * columnItem이 stickyHeader 영역을 침범하지 않고 화면에서 스크롤되도록 구현
 */
@Composable
fun TransparentColumn(
    modifier: Modifier = Modifier,
    header: @Composable (Modifier) -> Unit,
    content: @Composable (Modifier) -> Unit,
    footer: @Composable ((Modifier) -> Unit)? = null
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (headerRef, contentRef, footerRef) = createRefs()

        header.invoke(Modifier.constrainAs(headerRef) {
            linkTo(
                top = parent.top,
                bottom = contentRef.top
            )
        })
        content.invoke(Modifier.constrainAs(contentRef) {
            linkTo(
                top = headerRef.bottom,
                bottom = footer?.let {
                    footerRef.top
                } ?: run {
                    parent.bottom
                }
            )
            height = Dimension.preferredWrapContent
        })
        footer?.invoke(Modifier.constrainAs(footerRef) {
            linkTo(
                top = contentRef.bottom,
                bottom = parent.bottom
            )
        })
        createVerticalChain(headerRef, contentRef, footerRef, chainStyle = ChainStyle.Packed)
    }
}