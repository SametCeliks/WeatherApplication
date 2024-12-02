package com.samet.weatherapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.samet.weatherapplication.ui.theme.WeatherApplicationTheme
import org.intellij.lang.annotations.Language

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weatherViewModel= ViewModelProvider(this)[WeatherViewModel::class.java]
        setContent {
            WeatherApplicationTheme {

                val context = LocalContext.current
                Toast.makeText(context, stringResource(id = R.string.If_you_want_to_change_the_language_settings_of_the_application_Settings_System_Languages_and_change),Toast.LENGTH_LONG).show()



                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    WeatherPage(weatherViewModel)

                }


            }
        }
    }
}

