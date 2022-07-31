package org.devio.proj.jetpack.jetpack.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.devio.proj.jetpack.jetpack.compose.ui.theme.SampleJetpackComposeTheme

/**
 * hint 第六章 此页面+MAinActivity页面
 */
class BasicComposeSixActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleJetpackComposeTheme {
                PhotographerCard()
            }
        }
    }
}

@Composable//note modifier 可作为参数传递
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            //hint 串联修饰符时请务必小心，因为顺序很重要。
            //          由于修饰符会串联成一个参数，所以顺序将影响最终结果
            /*.padding(16.dp)//note 比如将padding和clickable调换位置 就能看到响应点击效果的区别发生了变化
            .clickable(onClick = {})*/
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { /* Ignoring onClick */ })
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }

        Column(
            modifier = Modifier

                .padding(start = 8.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun PhotographerCardPreview() {
    SampleJetpackComposeTheme {
        PhotographerCard()
    }
}
