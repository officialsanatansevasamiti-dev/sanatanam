package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenDestination

@Composable
fun SacredTopBar(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val isOmPlaying by viewModel.soundManager.isOmDronePlaying.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "om_pulse")
    val omScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isOmPlaying) 1.22f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "om_scale"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("sacred_top_bar"),
        color = Color.Transparent,
        tonalElevation = 6.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BhagwaDark,
                            BhagwaPrimary
                        )
                    )
                )
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sacred Emblem / Om Icon
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SwarnaGold)
                        .border(1.5.dp, SwarnaGoldLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ॐ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title & Subtitle
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "श्री सनातनम्",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                    Text(
                        text = "सनातन सेवा समिति",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SwarnaGoldLight,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                // Quick Sacred Sound Actions
                // 1. Temple Bell Button
                IconButton(
                    onClick = { viewModel.soundManager.playTempleBell() },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("action_temple_bell")
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "घंटी बजाएं (Ring Temple Bell)",
                        tint = SwarnaGold,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // 2. Shankh Sound Button
                IconButton(
                    onClick = { viewModel.soundManager.playShankhNaad() },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("action_shankh_naad")
                ) {
                    Text(
                        text = "🐚",
                        fontSize = 20.sp
                    )
                }

                // 3. Cosmic OM Drone Toggle
                IconButton(
                    onClick = { viewModel.soundManager.toggleOmDrone() },
                    modifier = Modifier
                        .size(40.dp)
                        .scale(omScale)
                        .testTag("action_toggle_om")
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (isOmPlaying) KumkumRed else Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ॐ",
                            color = if (isOmPlaying) SwarnaGoldLight else Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 4. About Samiti Button
                IconButton(
                    onClick = { viewModel.navigateTo(ScreenDestination.ABOUT) },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("action_about_samiti")
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "सनातन सेवा समिति व डेवलपर्स",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
