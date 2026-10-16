package com.example.ui.screens

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class SplashBridge(private val onComplete: (Boolean) -> Unit) {
    @JavascriptInterface
    fun onSplashComplete(hasActiveSession: Boolean) {
        onComplete(hasActiveSession)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SplashScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF170105))
            .testTag("splash_screen")
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        allowFileAccess = true
                        allowContentAccess = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        cacheMode = WebSettings.LOAD_NO_CACHE
                    }
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    webViewClient = WebViewClient()
                    addJavascriptInterface(
                        SplashBridge { _ ->
                            scope.launch {
                                viewModel.setAppStage(AppStage.WELCOME)
                            }
                        },
                        "AndroidBridge"
                    )
                    loadUrl("file:///android_asset/splash.html")
                }
            }
        )
    }
}
