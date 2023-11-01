package com.salt.reza

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.salt.reza.ui.theme.RezaSaltTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1)

        // kehalaman 2
        Handler().postDelayed({
            val intent = Intent(this, Page2Activity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2000ms (2 seconds) delay
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RezaSaltTheme {
        Greeting("Android")
    }
}