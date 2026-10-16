package com.example.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

val ColorMaroonDark = Color(0xFF180207)
val ColorGoldLight = Color(0xFFFFE7A0)
val ColorMuted = Color(0xFFDDCBB8)

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RegisterScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorMaroonDark)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar with Back/Exit button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "🚩 श्री सनातनम् पंजीकरण",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = ColorGoldLight
                        )
                    )
                }

                OutlinedButton(
                    onClick = { viewModel.setAppStage(AppStage.WELCOME) },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorGoldLight.copy(alpha = 0.4f))
                ) {
                    Text(text = "वापस जाएँ ✕", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // WebView hosting the live PHP Registration Form & API
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
            ) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            webViewClient = WebViewClient()
                            // Load your live server URL where the API/Register page resides
                            loadUrl("https://shrisanatanam.com/com/api/register.php")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
