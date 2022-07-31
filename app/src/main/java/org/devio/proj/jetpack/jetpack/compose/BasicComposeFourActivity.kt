package org.devio.proj.jetpack.jetpack.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.devio.proj.jetpack.jetpack.compose.ui.theme.SampleJetpackComposeTheme

/**
 * https://developer.android.google.cn/codelabs/jetpack-compose-basics?continue=https%3A%2F%2Fdeveloper.android.google.cn%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-basics#6
 *
 * hint 学习compose 第四章节
 *
 */
class BasicComposeFourActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SampleJetpackComposeTheme {
                MyApp()
            }
        }
    }

    @Composable
    private fun Greeting(name: String) {
        //note： 此章节关于重置的解释和使用解决了我的很大疑惑 --> hint 7. Compose 中的状态
        //note 您可以使用 rememberSaveable  hint 10. 保留状态
        //     而不使用 remember。这会保存每个在配置更改（如旋转）和进程终止后保留下来的状态。
        val expanded = rememberSaveable { mutableStateOf(false) }

        //var isExpanded by remember { mutableStateOf(false)}

        // 展开和关闭 您无需在重组后记住 extraPadding，因为该设置依赖于状态，只需执行一个简单的计算即可。
//        val extraPadding = if (expanded.value) 48.dp else 0.dp
        //note : 如果您想探索不同类型的动画，请尝试为 spring 提供不同的参数
        //    尝试使用不同的规范（tween、repeatable）和不同的函数（animateColorAsState 或不同类型的动画 API）。
        val extraPadding by animateDpAsState(
            targetValue = if (expanded.value) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        //hint 动画属性 animationSpec 确保内边距不会为负数，否则可能会导致应用崩溃
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))//展开和关闭
                ) {
                    Text(text = "Hello, ")
                    Text(
                        text = name, style = MaterialTheme.typography.h4.copy(//加粗
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                OutlinedButton(
                    onClick = { expanded.value = !expanded.value }
                ) {
                    Text(if (!expanded.value) "Show more" else "Show less")
                }
            }
        }
    }


    @Composable
    fun MyApp() {
        var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
//通过向 OnboardingScreen 传递函数而不是状态，可以提高该可组合项的可重用性，并防止状态被其他可组合项更改。
// 一般而言，这可以让事情变得简单。一个很好的例子就是，现在需要如何修改初始配置屏幕预览来调用
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }

    @Composable
    private fun Greetings(names: List<String> = List(1000) { "$it" }) {
        LazyColumn(modifier = Modifier.padding(4.dp)) {
            items(items = names) { name ->
//                Greeting(name = name)
                GreetingCard(name)
            }
        }
    }

    @Composable
    private fun GreetingCard(name: String) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(name)
        }
    }

    @Preview(
        showBackground = true,
        widthDp = 320,
        uiMode = UI_MODE_NIGHT_YES,
        name = "DefaultPreviewDark"
    )
    @Composable
    fun DefaultPreview() {
        SampleJetpackComposeTheme {
            MyApp()
        }
    }


    @Composable
    fun OnboardingScreen(onContinueClicked: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome to the Basics Codelab!")
                Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onContinueClicked
                ) {
                    Text("Continue")
                }
            }
        }
    }

    @Preview(showBackground = true, widthDp = 320, heightDp = 320)
    @Composable
    fun OnboardingPreview() {
        SampleJetpackComposeTheme {
            //将 onContinueClicked 分配给空 lambda 表达式就等于“什么也不做”，这非常适合于预览。
            OnboardingScreen(onContinueClicked = {})
        }
    }


    @Composable
    private fun CardContent(name: String) {
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }
        }
    }
}