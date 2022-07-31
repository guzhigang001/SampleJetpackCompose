package org.devio.proj.jetpack.jetpack.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.devio.proj.jetpack.jetpack.compose.ui.theme.SampleJetpackComposeTheme

//hint 要继承自ComponentActivity
class SampleMainActivity : ComponentActivity()/*AppCompatActivity()*/ {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Greetings(name = "Composable")
            SampleJetpackComposeTheme {
//                MessageCard(Message("Android", "Jetpack Compose"))
                PreviewMessageCard()
            }
        }
    }


}

data class Message(val author: String, val body: String)

@Composable
fun Greetings(name: String) {
    Text(text = "Android First Page $name")
}

@Composable
fun Newmsg() {

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(color = Color.Gray)
            .absoluteOffset(x = 15.dp, y = 15.dp)


    ) {
        Text(text = "this is home")
        Text(text = "this is my foot")
        Text(text = "this is your body")
    }

}

@Composable
fun MessageCard(msg: Message) {
    //note Row Box 相对布局
    Row {
        Image(
            painter = painterResource(id = R.drawable.picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp 图片大小40x40
                .size(40.dp)
                // Clip image to be shaped as a circle 裁剪 并裁剪成圆形
                .clip(CircleShape)
                    //圆形描边
                .border(width = 1.5.dp, MaterialTheme.colors.secondary, CircleShape)

        )

        // Add a horizontal space between the image and the column
        //插入什么位置就对应着两个空间之间的距离
        Spacer(modifier = Modifier.width(8.dp))

        //可组合函数可以使用 remember 将本地状态存储在内存中，并跟踪传递给 mutableStateOf 的值的变化。该值更新时，
        // 系统会自动重新绘制使用此状态的可组合项（及其子项）。这称为重组。
        // We keep track if the message is expanded or not in this variable
        // mutableStateOf 有点像stateful 状态变化 hint 自动订阅
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                //hint 设置字体和间距  subtitle2点进去里面有具体的字体型号
                style = MaterialTheme.typography.subtitle2
            )
            // Add a vertical space between the author and msg texts
            Spacer(modifier = Modifier.height(4.dp))

            //hint 添加阴影边框
            Surface(
                shape = MaterialTheme.shapes.medium,//圆角程度
                elevation = 1.dp,
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {

            }

        }
    }
}

/*hint @Preview AOP 即时编译 直接预览 */
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"//黑白主题
)
@Composable
fun PreviewMessageCard() {
//    msgCard("Android")

    SampleJetpackComposeTheme {
//        MessageCard(Message("Android", "Jetpack Compose"))

/*        val list= mutableListOf<Message>()
        repeat(20){
            list.add(Message(author = "Jetpack $it","举报 Facebook 上违规内容或垃圾信息的最佳方式是使用内容旁的“举报”链接。以下是如何向我们举报内容的一些示例。详细了解如何举报违规内容。\n" +
                    "如果您没有帐户或者无法看到想举报的内容（例如：某用户将您拉黑），请了解您可以采取哪些措施。"))
        }*/
        Conversation(SampleData.conversationSample)

    }

}

@Composable
fun Conversation(messages: List<Message>) {
    //hint LazyColumn 和 LazyRow
    //     这些可组合项只会呈现屏幕上显示的元素，因此，对于较长的列表，使用它们会非常高效
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}
