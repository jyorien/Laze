package com.example.laze.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.laze.composables.pager.rememberPagerState
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun <T : Any> Pager(
    items: List<T>,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    initialIndex: Int = 0,
    // fraction of overall width/height of container the item will take
    itemFraction: Float = 1f,
    itemSpacing: Dp = 0.dp,
    // how much first and last item can be scrolled off position (0.5f means halfway)
    overshootFraction: Float = 0.5f,
    onItemSelect: (T) -> Unit = {},
    contentFactory: @Composable (T) -> Unit
) {
    // check fractions are within range & selected index within bounds
    require(initialIndex in 0..items.lastIndex) { "Initial index out of bounds" }
    require(itemFraction > 0f && itemFraction <= 1f) { "Item fraction must be in the (0f, 1f) range" }
    require(overshootFraction > 0f && itemFraction <= 1f) { "Overshoot fraction must be in the (0f, 1f) range" }
    val scope = rememberCoroutineScope()
    val state = rememberPagerState()
    state.currentIndex = initialIndex
    state.numberOfItems = items.size
    state.itemFraction = itemFraction
    state.overshootFraction = overshootFraction
    state.itemSpacing = with(LocalDensity.current) { itemSpacing.toPx() }
    state.orientation = orientation
    state.listener = { index -> onItemSelect(items[index]) }
    state.scope = scope

    Layout(
        content = {
            items.map { item ->
                Box(
                    modifier = when (orientation) {
                        Orientation.Horizontal -> Modifier.fillMaxWidth()
                        Orientation.Vertical -> Modifier.fillMaxHeight()
                    },
                    contentAlignment = Alignment.Center,
                ) {
                    contentFactory(item)
                }
            }
        },
        modifier = modifier
            .clipToBounds()
            .then(state.inputModifier),
    ) { measurables, constraints ->
        val dimension = constraints.dimension(orientation)
        val looseConstraints = constraints.toLooseConstraints(orientation, state.itemFraction)
        val placeables = measurables.map { measurable -> measurable.measure(looseConstraints) }
        val size = placeables.getSize(orientation, dimension)
        val itemDimension = (dimension * state.itemFraction).roundToInt()
        state.itemDimension = itemDimension
        val halfItemDimension = itemDimension / 2
        layout(size.width, size.height) {
            val centerOffset = dimension / 2 - halfItemDimension
            val dragOffset = state.dragOffset.value
            val roundedDragOffset = dragOffset.roundToInt()
            val spacing = state.itemSpacing.roundToInt()
            val itemDimensionWithSpace = itemDimension + state.itemSpacing
            val first = ceil(
                (dragOffset -itemDimension - centerOffset) / itemDimensionWithSpace
            ).toInt().coerceAtLeast(0)
            val last = ((dimension + dragOffset - centerOffset) / itemDimensionWithSpace).toInt()
                .coerceAtMost(items.lastIndex)
            for (i in first..last) {
                val offset = i * (itemDimension + spacing) - roundedDragOffset + centerOffset
                placeables[i].place(
                    x = when (orientation) {
                        Orientation.Horizontal -> offset
                        Orientation.Vertical -> 0
                    },
                    y = when (orientation) {
                        Orientation.Horizontal -> 0
                        Orientation.Vertical -> offset
                    }
                )
            }
        }
    }

}

// returns maximum dimensions on constraints based on scroll orientation
private fun Constraints.dimension(orientation: Orientation) = when (orientation) {
    Orientation.Vertical -> maxHeight
    Orientation.Horizontal -> maxWidth
}

// make a copy of constraints where min dimensions not in scroll direction is set to 0,
// while on the scroll direction it sets the min dimensions == max dimensions
// use itemFraction to constrain Pager items to a fraction of container dimensions in scroll direction
private fun Constraints.toLooseConstraints(
    orientation: Orientation,
    itemFraction: Float,
): Constraints {
    val dimension = dimension(orientation)
    val adjustedDimension = (dimension * itemFraction).roundToInt()
    return when (orientation) {
        Orientation.Horizontal -> copy(
            minWidth = adjustedDimension,
            maxWidth = adjustedDimension,
            minHeight = 0,
        )
        Orientation.Vertical -> copy(
            minWidth = 0,
            minHeight = adjustedDimension,
            maxHeight = adjustedDimension,
        )
    }
}

// get size of parcelables based on scroll direction
private fun List<Placeable>.getSize(
    orientation: Orientation,
    dimension: Int,
): IntSize = when (orientation) {
    Orientation.Horizontal -> IntSize(
        dimension,
        maxByOrNull { it.height }?.height ?: 0
    )
    Orientation.Vertical -> IntSize(
        maxByOrNull { it.width }?.width ?: 0,
        dimension
    )
}

// provide calculated velocity on scroll axis
private fun VelocityTracker.calculateVelocity(orientation: Orientation) = when (orientation) {
    Orientation.Horizontal -> calculateVelocity().x
    Orientation.Vertical -> calculateVelocity().y
}

// calculates the pointer input change based on our scroll direction.
private fun PointerInputChange.calculateDragChange(orientation: Orientation) =
    when (orientation) {
        Orientation.Horizontal -> positionChange().x
        Orientation.Vertical -> positionChange().y
    }