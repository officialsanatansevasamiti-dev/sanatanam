package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.DivineMaroon
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.82f) }

    val infiniteTransition = rememberInfiniteTransition(label = "halo_glow")
    val haloPulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "halo_scale"
    )

    // Fade in animation and 2.8s automatic transition
    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit) {
        delay(2700)
        viewModel.setAppStage(AppStage.WELCOME)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BhagwaPrimary,
                        BhagwaDark,
                        KumkumRed,
                        DivineMaroon
                    )
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // Allow tap to skip splash immediately
                viewModel.setAppStage(AppStage.WELCOME)
            }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        // Divine Mandala Aura Background
        Box(
            modifier = Modifier
                .size(310.dp)
                .scale(haloPulse)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            SwarnaGold.copy(alpha = 0.32f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Center Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
                .alpha(alphaAnim.value)
                .scale(scaleAnim.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Sacred Shloka Mantra Badge
            Surface(
                color = Color.White.copy(alpha = 0.16f),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "॥ ॐ नमो भगवते वासुदेवाय ॥",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = SwarnaGoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Official Logo Container (Strictly 1:1 Aspect Ratio, No cropping, No distortion)
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .border(3.dp, SwarnaGold, RoundedCornerShape(28.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "SHRI SANATANAM Official Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Title: "SHRI SANATANAM"
            Text(
                text = "SHRI SANATANAM",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = SwarnaGoldLight,
                    letterSpacing = 1.8.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "॥ श्री सनातनम् ॥",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "धर्मो रक्षति रक्षितः",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFFFD180),
                    fontWeight = FontWeight.Medium
                )
            )
        }

        // Mandatory Footer Branding
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                .alpha(alphaAnim.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Powered by Sanatan Seva Samiti",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Developed by DG Developers (Deep Group)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SwarnaGoldLight,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "sanatansevasamiti.shrisanatanam.com",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFFFFF3E0).copy(alpha = 0.85f),
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}
