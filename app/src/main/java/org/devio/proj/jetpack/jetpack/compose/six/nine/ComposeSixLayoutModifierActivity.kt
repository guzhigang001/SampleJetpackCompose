package org.devio.proj.jetpack.jetpack.compose.six.nine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.devio.proj.jetpack.jetpack.compose.six.eight.Chip
import org.devio.proj.jetpack.jetpack.compose.six.eight.StaggeredGrid
import org.devio.proj.jetpack.jetpack.compose.six.eight.topics
import org.devio.proj.jetpack.jetpack.compose.six.nine.ui.theme.SampleJetpackComposeTheme

/**
 * LayoutModifier 可以改变界面组件的测量和布局方式。
 *
 * hint  1. 串联修饰符时顺序非常重要
 *          (1) 修饰符会从左到右更新约束条件，然后从右到左返回大小
 *          (2)
 *       2. placeRelative 与 place的不同
 */
class ComposeSixLayoutModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BodyContent1()
                }
            }
        }
    }


}
/*============= 布局顺序对最终生成布局效果的影响 =============*/
@Composable
fun BodyContent1(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .size(200.dp)
            .padding(16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}

@Composable
fun BodyContent2(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray, shape = RectangleShape)
            .padding(16.dp)
            .size(200.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}
@Preview
@Composable
fun LayoutModifierShow1() {
    SampleJetpackComposeTheme {
        BodyContent1()
    }
}

@Preview
@Composable
fun LayoutModifierShow2() {
    SampleJetpackComposeTheme {
        BodyContent2()
    }
}

/*============= 布局顺序对最终生成布局效果的影响 =============*/