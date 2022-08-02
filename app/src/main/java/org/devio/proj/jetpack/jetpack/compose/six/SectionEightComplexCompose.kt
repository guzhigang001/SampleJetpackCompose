package org.devio.proj.jetpack.jetpack.compose.six

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/**
 *@Author :ggxz
 *@Date:  2022/8/2
 *@Desc:  第六章 第八小节 复杂的自定义View
 */


/**
 * 自定义复杂交错网格布局
 */
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,// 布局参数的要求
    rows: Int = 3,//默认行数
    content: @Composable () -> Unit//布局内容 也就是子Item
) {
    Layout(
        content = content,
        modifier = modifier
        // measurables, constraints -> 是重写MeasurePolicy类中的measure方法
    ) { measurables, constraints ->
        // measure and position children given constraints logic here

        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            Log.e("StaggeredGrid", "index:$index row:$row ")
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)
            Log.e("StaggeredGrid", "rowWidths[row] ${rowWidths[row]} rowHeights[row]:${rowHeights[row]} row:$row ")
            Log.e("StaggeredGrid", "placeable.width ${placeable.width} placeable.width:${placeable.width}  ")
            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i-1] + rowHeights[i-1]
        }

        // Set the size of the parent layout
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }

    }
}