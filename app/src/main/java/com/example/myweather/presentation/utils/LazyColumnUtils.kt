package com.example.myweather.presentation.utils

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.derivedStateOf
import kotlin.math.max

/**
 * sticky header listener
 */
fun isStickyHeader(listState: LazyListState, itemKey: String): Boolean {
    return derivedStateOf {
        listState.layoutInfo.visibleItemsInfo.getOrNull(0)?.let { _firstVisibleItem ->
            _firstVisibleItem.key == itemKey // key를 통해 stickyHeader 체크
                    && visibilityPercent(listState, _firstVisibleItem) == 100f // 100% 보여질 때
        } ?: false
    }.value
}

fun visibilityPercent(listState: LazyListState, info: LazyListItemInfo): Float {
    val cutTop = max(0, listState.layoutInfo.viewportStartOffset - info.offset)
    val cutBottom = max(0, info.offset + info.size - listState.layoutInfo.viewportEndOffset)

    return max(0f, 100f - (cutTop + cutBottom) * 100f / info.size)
}