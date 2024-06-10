package com.example.practice15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practice15.ui.theme.Practice15Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var text2 by remember { mutableStateOf("") }

            Practice15Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column{
                        Greeting(
                            text2 = text2,
                            onText2Change = { text2 = it },
                            name = "Android"
                        )

                        MessageList()
                    }
                }

            }
        }
    }
}

@Composable
fun MessageList(){
    val messages =  arrayOf("Hello", "World", "Compose")

    Row{
        // 항상 전체를 렌더링
        Column {
            for (msg in messages)
                Text(text=msg)
        }

        // 화면에 보이는 값만 렌더링
        LazyColumn {
            items(messages){
                msg-> Text(text = msg)
            }
        }
    }

}

@Composable
fun Greeting(name: String, text2: String, onText2Change: (String)-> Unit, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
 //   var text2 by remember { mutableStateOf("") }

    Column( modifier = modifier) {
        Text(
            text = "First Text $text"
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hello $name! $text2",
        )

        OutlinedTextField(
            value = text,
            onValueChange = {text = it}
        )

        Button(onClick = { onText2Change(text) }) {
            Text("Submit")
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practice15Theme {
        Greeting("Android")
    }
}
 */