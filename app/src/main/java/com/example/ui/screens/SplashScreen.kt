package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    // Entrance animations
    val entranceAlpha = remember { Animatable(0f) }
    val entranceScale = remember { Animatable(0.7f) }

    LaunchedEffect(Unit) {
        entranceAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit) {
        entranceScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 950, easing = FastOutSlowInEasing)
        )
    }

    // Auto navigation after 2.2 seconds
    LaunchedEffect(Unit) {
        delay(2200)
        viewModel.setAppStage(AppStage.WELCOME)
    }

    // Continuous animations
    val infiniteTransition = rememberInfiniteTransition(label = "splash_infinite")

    // Slow ring rotation (35s)
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(35000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_rotation"
    )

    // Reverse light rays rotation (42s)
    val raysRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(42000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rays_rotation"
    )

    // Orbit 1 rotation (8s)
    val orbitRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_rotation"
    )

    // Orbit 2 counter-rotation (12s)
    val orbitTwoRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_two_rotation"
    )

    // Ambient glow pulse (4s)
    val ambientPulse by infiniteTransition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambient_pulse"
    )

    // Particles float oscillation
    val particleOffset by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle_offset"
    )

    // Loader sweep animation
    val loaderProgress by infiniteTransition.animateFloat(
        initialValue = -0.3f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "loader_progress"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                viewModel.setAppStage(AppStage.WELCOME)
            }
            .testTag("splash_screen")
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()
        val centerX = widthPx / 2f
        val centerY = heightPx * 0.40f

        // 1. Exact Radial Gradient Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFB3360A),
                        Color(0xFF820B13),
                        Color(0xFF55060D),
                        Color(0xFF2C0208),
                        Color(0xFF130104)
                    ),
                    center = Offset(centerX, centerY),
                    radius = (widthPx * 0.95f).coerceAtLeast(heightPx * 0.65f)
                )
            )

            // Background Core Highlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x2EFFB92F),
                        Color(0x1AD84109),
                        Color.Transparent
                    ),
                    center = Offset(centerX, centerY),
                    radius = widthPx * 0.55f
                ),
                radius = widthPx * 0.55f,
                center = Offset(centerX, centerY)
            )
        }

        // 2. Ambient Glow Pulse
        Box(
            modifier = Modifier
                .size(380.dp)
                .align(Alignment.Center)
                .offset(y = (-50).dp)
                .scale(ambientPulse)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x2EFFC037),
                            Color(0x14FF680A),
                            Color.Transparent
                        )
                    )
                )
        )

        // 3. Rotating Light Rays
        Box(
            modifier = Modifier
                .size(340.dp)
                .align(Alignment.Center)
                .offset(y = (-50).dp)
                .rotate(raysRotation)
                .clip(CircleShape)
                .background(
                    Brush.sweepGradient(
                        listOf(
                            Color.Transparent,
                            Color(0x0CFFD664),
                            Color.Transparent,
                            Color(0x09FFD664),
                            Color.Transparent,
                            Color(0x0CFFD664),
                            Color.Transparent,
                            Color(0x0AFFD664),
                            Color.Transparent
                        )
                    )
                )
        )

        // 4. Concentric Ring System
        Canvas(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.Center)
                .offset(y = (-50).dp)
                .rotate(ringRotation)
        ) {
            val radius = size.minDimension / 2f
            // Outer Ring
            drawCircle(
                color = Color(0x18FFD76C),
                radius = radius - 4.dp.toPx(),
                style = Stroke(width = 1.dp.toPx())
            )
            // Middle Ring
            drawCircle(
                color = Color(0x14FFDC78),
                radius = radius - 30.dp.toPx(),
                style = Stroke(width = 1.dp.toPx())
            )
            // Inner Dashed Ring
            drawCircle(
                color = Color(0x1BFFDC78),
                radius = radius - 55.dp.toPx(),
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                )
            )
        }

        // 5. Floating Glowing Gold Particles
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val particleColor = Color(0xFFFFD866)
            val pOffsets = listOf(
                Pair(0.18f, 0.28f),
                Pair(0.82f, 0.23f),
                Pair(0.19f, 0.64f),
                Pair(0.87f, 0.69f),
                Pair(0.28f, 0.17f),
                Pair(0.72f, 0.16f),
                Pair(0.10f, 0.48f),
                Pair(0.90f, 0.45f)
            )

            pOffsets.forEachIndexed { idx, pair ->
                val dir = if (idx % 2 == 0) 1f else -1f
                Box(
                    modifier = Modifier
                        .offset(
                            x = (widthPx * pair.first).dp / 2.7f,
                            y = (heightPx * pair.second).dp / 2.7f + (particleOffset * dir).dp
                        )
                        .size(3.dp)
                        .clip(CircleShape)
                        .background(particleColor)
                )
            }
        }

        // 6. Central Content (Logo Zone + Branding)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .alpha(entranceAlpha.value)
                .scale(entranceScale.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Zone with Orbits and Metallic Frame
            Box(
                modifier = Modifier
                    .size(220.dp),
                contentAlignment = Alignment.Center
            ) {
                // Orbit 1 with Gold Orb Indicator
                Box(
                    modifier = Modifier
                        .size(198.dp)
                        .rotate(orbitRotation)
                        .border(1.dp, Color(0x73FFDC78), CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = (-3.5).dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFF1A6))
                    )
                }

                // Orbit 2 Dashed Counter-Rotating Ring
                Canvas(
                    modifier = Modifier
                        .size(184.dp)
                        .rotate(orbitTwoRotation)
                ) {
                    drawCircle(
                        color = Color(0x30FFDD80),
                        style = Stroke(
                            width = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
                        )
                    )
                }

                // Outer Conic Metallic Gold Frame
                Box(
                    modifier = Modifier
                        .size(158.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.sweepGradient(
                                listOf(
                                    Color(0xFF8D5A0E),
                                    Color(0xFFFFE48A),
                                    Color(0xFFBD801A),
                                    Color(0xFFFFF0A9),
                                    Color(0xFF9C650E),
                                    Color(0xFFFFD85C),
                                    Color(0xFF8D5A0E)
                                )
                            )
                        )
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Inner Frame
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFFFF4BB),
                                        Color(0xFFD39A25),
                                        Color(0xFFFFF0A4)
                                    )
                                )
                            )
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Official Logo Image (R.drawable.logo)
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Sanatan Seva Samiti Logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Motto Badge: ✦ जय भवानी ✦
            Surface(
                color = Color(0xE039040B),
                shape = RoundedCornerShape(999.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x60FFD667))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "✦", color = Color(0xFFFFD866), fontSize = 10.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "जय भवानी",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color(0xFFFFF0A8),
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.7.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "✦", color = Color(0xFFFFD866), fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Brand Title: "sanatanam"
            Text(
                text = "sanatanam",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = (-2.5).sp,
                    fontSize = 46.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Hindi Name: "सनातन सेवा समिति"
            Text(
                text = "सनातन सेवा समिति",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFD866),
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // English Subtitle: "SANATAN SEVA SAMITI"
            Text(
                text = "SANATAN SEVA SAMITI",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.55f),
                    letterSpacing = 3.4.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Loading System Bar
            Box(
                modifier = Modifier
                    .width(86.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(99.dp))
                    .background(Color(0x25FFD664))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = (loaderProgress * 86).dp)
                        .width(30.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color(0xFFFFF2B0),
                                    Color(0xFFFFD14D),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }

        // 7. Footer: "सेवा ही परमो धर्मः" & "समर्पित राष्ट्र एवं सनातन धर्म सेवा"
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 26.dp, start = 18.dp, end = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(25.dp)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, Color(0x90FFD664))
                            )
                        )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "सेवा ही परमो धर्मः",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFD866),
                        letterSpacing = 0.5.sp
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(25.dp)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0x90FFD664), Color.Transparent)
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "समर्पित राष्ट्र एवं सनातन धर्म सेवा",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.55f),
                    letterSpacing = 0.2.sp
                )
            )
        }
    }
}
