package org.devio.proj.jetpack.jetpack.compose.six.eight

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import kotlin.math.max

/**
 *@Author :ggxz
 *@Date:  2022/8/2
 *@Desc:  第六章 第八小节 复杂的自定义View
 */


/**
 * 自定义复杂交错网格布局
 */
@Composable
fun StaggeredGridCopy(
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
            rowHeights[row] = rowHeights[row].coerceAtLeast(placeable.height)
            Log.e(
                "StaggeredGrid",
                "rowWidths[row] ${rowWidths[row]} rowHeights[row]:${rowHeights[row]} row:$row "
            )
            Log.e(
                "StaggeredGrid",
                "placeable.width ${placeable.width} placeable.width:${placeable.width}  "
            )
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
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
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

//hint 了解此自定义布局的思路: 是从左至右 一列一列去布局生成的
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

        // 记录每一行的宽度
        val rowWidths = IntArray(rows) { 0 }
        // 记录每一行的高度
        val rowHeights = IntArray(rows) { 0 }

        //
        val placeables = measurables.mapIndexed { index, measurable ->
            //测量每个子View
            val placeable = measurable.measure(constraints)

            // 0，1，2
            val row = index % rows
            //记录每一行的宽度
            rowWidths[row] += placeable.width
            //以此行高度最高的那个View 为此行的高度  note 1 记录每行的高度
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        // note 这行代码是计算此row 整体的宽度
        val width = rowWidths.maxOrNull()/*找出数组中 宽度最大的那个row的数值*/
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))/*如果找到的max比最小取最小 比最大取最大*/
            ?: constraints.minWidth/*计算后出来的值如果是null直接用minWidth*/

        // note 2 计算整个布局的整体高度
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // note 3 每行的Y轴 基于前面布局Y的累加 用来计算每行的高度
        val rowY = IntArray(rows) { 0 }

        // 1 2 第一行肯定是0 不用计算
        for (i in 1 until rows) {
            // 第2行= 第一行rowY 0 + 第一行的高度 rowHeights[i - 1] 0
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // 设置父布局的大小 hint 上面计算的 width, height 是用来确定整体的宽高
        layout(width, height) {
            //每行的其实X 轴
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