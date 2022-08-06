package org.devio.proj.jetpack.jetpack.compose.six

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.devio.proj.jetpack.jetpack.compose.firstBaselineToTop
import org.devio.proj.jetpack.jetpack.compose.six.ui.theme.SampleJetpackComposeTheme

class ComposeSixCustomViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text("Hi there!", Modifier.firstBaselineToTopDP(32.dp))
                }
            }
        }
    }
}





private const val TAG = "ComposeSixCustomViewActivity"
// hint 这里要明白一个非常重要的概念 padding 的设置是 top顶部到firstBaseLine 的距离
fun Modifier.firstBaselineToTopDP(
    firstBaselineToTop: Dp
) = this.then(
    //hint layout是扩展函数 并不是Compose
    layout { measurable, constraints ->
        //1. 对自定义View进行测量 目的是获取placeable对象 constraints约束条件 里面可以宽高的最大值和最小值等约束条件
        val placeable = measurable.measure(constraints)
        // Check the composable has a first baseline
        //2. 检查组合物 是否存在第一基准线FirstBaseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        //3. 取出基准线的Int值 24
        val firstBaseline = placeable[FirstBaseline]
        Log.e(TAG, "firstBaseline: $firstBaseline")
        // Height of the composable with padding - first baseline
        //4. 设置基准线到顶部的高度 - 基准线 就是计算文本的高度
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        Log.e(
            TAG,
            "firstBaselineToTop: $firstBaseline  - placeableY: $placeableY  " +
                    "firstBaselineToTop.roundToPx()：${firstBaselineToTop.roundToPx()}"
        )
        //5. 文本的高度 + 预设高度
        val height = placeable.height + placeableY
        Log.e(TAG, "height: $height placeable.height:${placeable.height} ")
        layout(placeable.width, height) {
            // Where the composable gets placed
            //6. 确定放置位置 如果您不调用 placeRelative，该可组合项将不可见
            placeable.placeRelative(0, placeableY)
        }
    }
)



@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    SampleJetpackComposeTheme {
        Text("Hi there!!", Modifier.firstBaselineToTopDP(32.dp))
//        BodyContent()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun TextWithNormalPaddingPreview() {
    SampleJetpackComposeTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun ColumnOwnColumn() {
    SampleJetpackComposeTheme{
        BodyContent()
    }
}



