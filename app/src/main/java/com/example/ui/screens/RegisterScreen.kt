package com.example.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

// Sanatan Dark Maroon & Gold Palette
private val ScreenDarkBackground = Color(0xFF180207)
private val ScreenGoldAccent = Color(0xFFFFE7A0)
private val ScreenMaroonHighlight = Color(0xFF650D16)
private val ScreenGoldBorder = Color(0xFFDCA72F)

private const val REGISTRATION_URL = "https://shrisanatanam.com/com/api/register.php"

/**
 * Production-ready Android App Registration Screen in Jetpack Compose
 * adhering strictly to the Royal Sanatan Dark Maroon & Gold theme and hosting
 * the full-featured hybrid onboarding WebView engine.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RegisterScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var loadProgress by remember { mutableIntStateOf(0) }
    var hasError by remember { mutableStateOf(false) }

    // Intercept back presses: go back in WebView history if available, else navigate back to WELCOME
    BackHandler {
        if (webViewInstance?.canGoBack() == true) {
            webViewInstance?.goBack()
        } else {
            viewModel.setAppStage(AppStage.WELCOME)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewInstance?.destroy()
            webViewInstance = null
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenDarkBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("register_screen")
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ScreenMaroonHighlight.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "🚩 श्री सनातनम् पंजीकरण",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = ScreenGoldAccent,
                            fontSize = 17.sp
                        ),
                        maxLines = 1
                    )
                }

                // Action controls: Refresh & Exit
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            hasError = false
                            webViewInstance?.reload()
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("register_reload_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "पृष्ठ पुनः लोड करें",
                            tint = ScreenGoldAccent
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.setAppStage(AppStage.WELCOME) },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = ScreenMaroonHighlight.copy(alpha = 0.35f),
                            contentColor = ScreenGoldAccent
                        ),
                        border = BorderStroke(1.dp, ScreenGoldBorder.copy(alpha = 0.6f)),
                        modifier = Modifier.testTag("register_exit_button")
                    ) {
                        Text(
                            text = "वापस जाएँ ✕",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ScreenGoldAccent
                        )
                    }
                }
            }

            // Web Loading Linear Progress Bar
            AnimatedVisibility(
                visible = isLoading && !hasError,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LinearProgressIndicator(
                    progress = { loadProgress / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = ScreenGoldAccent,
                    trackColor = ScreenMaroonHighlight
                )
            }

            // Main Content Area: Rounded Card hosting the Android WebView
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, ScreenGoldBorder.copy(alpha = 0.45f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AndroidView(
                        factory = { ctx ->
                            WebView(ctx).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )

                                settings.apply {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    loadWithOverviewMode = true
                                    useWideViewPort = true
                                    cacheMode = WebSettings.LOAD_DEFAULT
                                    databaseEnabled = true
                                    allowFileAccess = false
                                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                    setSupportZoom(true)
                                    builtInZoomControls = true
                                    displayZoomControls = false
                                }

                                webChromeClient = object : WebChromeClient() {
                                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                        loadProgress = newProgress
                                        if (newProgress >= 100) {
                                            isLoading = false
                                        }
                                    }
                                }

                                webViewClient = object : WebViewClient() {
                                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                        isLoading = true
                                        hasError = false
                                    }

                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        isLoading = false
                                    }

                                    override fun onReceivedError(
                                        view: WebView?,
                                        request: WebResourceRequest?,
                                        error: WebResourceError?
                                    ) {
                                        // Only trigger full screen error for main frame requests
                                        if (request?.isForMainFrame == true) {
                                            hasError = true
                                            isLoading = false
                                        }
                                    }
                                }

                                loadUrl(REGISTRATION_URL)
                                webViewInstance = this
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                    )

                    // Error fallback banner if internet is unreachable
                    if (hasError) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ScreenDarkBackground)
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(ScreenMaroonHighlight, CircleShape)
                                    .border(1.dp, ScreenGoldBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("⚠️", fontSize = 28.sp)
                            }

                            Text(
                                text = "पंजीकरण पोर्टल से संपर्क नहीं हो पा रहा है",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ScreenGoldAccent
                                ),
                                modifier = Modifier.padding(top = 16.dp)
                            )

                            Text(
                                text = "कृपया अपना इंटरनेट कनेक्शन जांचें और पुनः प्रयास करें।",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFFDDCBB8)
                                ),
                                modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
                            )

                            OutlinedButton(
                                onClick = {
                                    hasError = false
                                    isLoading = true
                                    webViewInstance?.loadUrl(REGISTRATION_URL)
                                },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = ScreenMaroonHighlight,
                                    contentColor = ScreenGoldAccent
                                ),
                                border = BorderStroke(1.dp, ScreenGoldBorder)
                            ) {
                                Text("पुनः प्रयास करें ⟳", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Centered loading spinner for initial load
                    if (isLoading && !hasError && loadProgress < 40) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(alpha = 0.85f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(
                                    color = ScreenMaroonHighlight,
                                    strokeWidth = 3.dp,
                                    modifier = Modifier.size(44.dp)
                                )
                                Text(
                                    text = "पंजीकरण पृष्ठ लोड हो रहा है...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ScreenDarkBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
