package com.beautyfox.customerapp

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.beautyfox.customerapp.navigation.AppNavHost
import com.beautyfox.customerapp.retrofit.api.OffersApi
import com.beautyfox.customerapp.ui.theme.BeautyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var offersApi: OffersApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Test", "Oncreate")
//     GlobalScope.launch {
//         Log.d("Test", "Before printing response")
//            val response = offersApi.getOffers()
//         Log.d("Test", "after api call")
//            Log.d("Test", response.body().toString())
//        }

        setContent {
            BeautyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController)
//                    WebView()
                }
            }
        }
    }
    @Composable
    fun WebView(){

        // Declare a string that contains a url
        val mUrl = "https://www.google.com"

        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.webChromeClient = CustomWebChromeClient()
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }

    class CustomWebChromeClient : WebChromeClient() {
        override fun onCloseWindow(window: WebView?) {}

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            return true
        }
    }
}
