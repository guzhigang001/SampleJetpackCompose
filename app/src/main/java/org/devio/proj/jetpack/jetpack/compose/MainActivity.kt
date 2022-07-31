package org.devio.proj.jetpack.jetpack.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.devio.proj.jetpack.jetpack.compose.ui.theme.SampleJetpackComposeTheme
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

/**
 * Jetpack Compose 中的布局 hint 第六章
 *
 * 自定义Compose
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleJetpackComposeTheme {
                /* // A surface container using the 'background' color from the theme
                 Surface(
                     modifier = Modifier.fillMaxSize(),
                     color = MaterialTheme.colors.background
                 ) {
                     Greeting("Android")
                 }*/

                //MaterialTheme 主题的使用
                /*LayoutsCodelab()*/

                //列表
                /*SimpleList()*/
                /*LazyList()*/
                /*ImageList()*/
               /* ScrollingList()*/
            }
        }
    }

    /**
     * 为了提高代码的可重用性和可测试性，我们应该将其构造为多个小的数据块
     */
    @Composable
    fun BodyContent(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            Text(text = "Hi there!")
            Text(text = "Thanks for going through the Layouts codelab")
        }
    }
    @Composable
    fun LayoutsCodelab() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "LayoutsCodelab")
                    },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            BodyContent(Modifier.padding(innerPadding))
        }
    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SampleJetpackComposeTheme {
            /*Greeting("Android")*/
            //MaterialTheme 主题的使用
            /*LayoutsCodelab()*/

            //列表
            /*SimpleList()*/
            /*LazyList()*/
            /*ImageList()*/
            /*ScrollingList()*/
        }
    }
}


@Composable
fun SimpleList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun ImageList() {
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Image(
            painter = rememberImagePainter(
                data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F9b%2F90%2F71%2F9b9071f3242f403db7a9b01178ac7405.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661852885&t=441749b461117dd2e2597d9d56f1e851"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}


fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
   layout { measurable, constraints ->
       val placeable = measurable.measure(constraints)

       // Check the composable has a first baseline
       check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
       val firstBaseline = placeable[FirstBaseline]

       // Height of the composable with padding - first baseline
       val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
       val height = placeable.height + placeableY
       layout(placeable.width, height) {
           // Where the composable gets placed
           placeable.placeRelative(0, placeableY)
       }
   }
)
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun TextWithPaddingToBaselinePreview() {
    SampleJetpackComposeTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun TextWithNormalPaddingPreview() {
    SampleJetpackComposeTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}