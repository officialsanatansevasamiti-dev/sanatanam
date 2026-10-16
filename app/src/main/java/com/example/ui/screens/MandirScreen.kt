package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeityInfo
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.ChandanCard
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.ui.theme.TempleCharcoal
import com.example.ui.theme.TempleSurfaceDark
import com.example.viewmodel.MainViewModel
import kotlin.random.Random

data class FallingPetal(
    val id: Long,
    val xOffset: Float,
    val emoji: String,
    val rotation: Float
)

@Composable
fun MandirScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedDeity by viewModel.selectedDeity.collectAsState()
    val isDiyaLit by viewModel.isDiyaLit.collectAsState()
    val isDhoopLit by viewModel.isDhoopLit.collectAsState()
    val isAartiRotating by viewModel.isAartiRotating.collectAsState()
    val flowerShowerCount by viewModel.flowerShowerTrigger.collectAsState()

    // Diya flame flicker animation
    val infiniteTransition = rememberInfiniteTransition(label = "diya_flame")
    val flameFlicker by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flame_flicker"
    )

    // Aarti thali circular rotation angle
    val aartiAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "aarti_rotation"
    )

    // Pushpa Varsha (Flower Shower Animation)
    val petals = remember { mutableStateListOf<FallingPetal>() }
    LaunchedEffect(flowerShowerCount) {
        if (flowerShowerCount > 0) {
            val flowerIcons = listOf("🌸", "🌺", "🌼", "🌹", "🪷")
            petals.clear()
            repeat(16) {
                petals.add(
                    FallingPetal(
                        id = System.currentTimeMillis() + it,
                        xOffset = Random.nextFloat(),
                        emoji = flowerIcons.random(),
                        rotation = Random.nextFloat() * 360f
                    )
                )
            }
        }
    }

    // Petal vertical fall progress
    val petalProgress by animateFloatAsState(
        targetValue = if (flowerShowerCount > 0) 1f else 0f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = "petal_fall"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 96.dp)
            .testTag("mandir_screen")
    ) {
        // Deity Selection Bar
        Text(
            text = "आराध्य देव / देवी चुनें",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = BhagwaDark
            ),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.deities) { deity ->
                val isSelected = selectedDeity.id == deity.id
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectDeity(deity) },
                    label = {
                        Text(
                            text = deity.nameHindi,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BhagwaPrimary,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("deity_chip_${deity.id}")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Sanctum Sanctorum (Garbhagriha Shrine Box)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            TempleSurfaceDark,
                            TempleCharcoal
                        )
                    )
                )
                .border(2.dp, SwarnaGold, RoundedCornerShape(24.dp))
                .testTag("garbhagriha_sanctum"),
            contentAlignment = Alignment.Center
        ) {
            // Decorative Temple Pillars & Archway
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Draw Temple Arch top
                val archPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w, 0f)
                    lineTo(w, 70f)
                    cubicTo(w * 0.75f, 40f, w * 0.25f, 40f, 0f, 70f)
                    close()
                }
                drawPath(archPath, color = BhagwaDark.copy(alpha = 0.5f), style = Fill)

                // Gold border accent
                drawLine(
                    color = SwarnaGold,
                    start = Offset(0f, 70f),
                    end = Offset(w, 70f),
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Divine Aura Background Glow
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                SwarnaGold.copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Center Deity Focus
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Sacred Deity Symbol
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(selectedDeity.primaryColorHex),
                                    BhagwaPrimary
                                )
                            )
                        )
                        .border(3.dp, SwarnaGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (selectedDeity.id) {
                            "shri_ram" -> "🏹"
                            "mahadev" -> "🔱"
                            "hanuman_ji" -> "🚩"
                            "radha_krishna" -> "🦚"
                            "maa_durga" -> "🦁"
                            "ganesh_ji" -> "🐘"
                            "maa_lakshmi" -> "🪷"
                            else -> "🕉️"
                        },
                        fontSize = 50.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = selectedDeity.nameHindi,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SwarnaGoldLight,
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    text = selectedDeity.titleHindi,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFFFCC80),
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // Left: Glowing Akhand Diya
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 24.dp, bottom = 16.dp)
                    .clickable { viewModel.toggleDiya() }
                    .testTag("mandir_diya_item"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (isDiyaLit) {
                        Text(
                            text = "🔥",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .scale(flameFlicker)
                                .offset(y = (-4).dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    Text(text = "🪔", fontSize = 32.sp)
                    Text(
                        text = if (isDiyaLit) "अखण्ड दीप" else "दीप बुझा है",
                        fontSize = 10.sp,
                        color = if (isDiyaLit) SwarnaGoldLight else Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Right: Dhoop / Incense Stick
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 16.dp)
                    .clickable { viewModel.toggleDhoop() }
                    .testTag("mandir_dhoop_item"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (isDhoopLit) {
                        Text(
                            text = "💨",
                            fontSize = 20.sp,
                            modifier = Modifier.scale(flameFlicker)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Text(text = "🕯️", fontSize = 28.sp)
                    Text(
                        text = if (isDhoopLit) "सुगन्धित धूप" else "धूप",
                        fontSize = 10.sp,
                        color = if (isDhoopLit) SwarnaGoldLight else Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Bottom Center: Rotating Aarti Thali if activated
            if (isAartiRotating) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .size(80.dp)
                        .rotate(aartiAngle),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✨🪔✨", fontSize = 22.sp)
                }
            }

            // Pushpa Varsha Falling Animation Overlay
            petals.forEach { petal ->
                val curY = petalProgress * 260f
                Text(
                    text = petal.emoji,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset {
                            IntOffset(
                                x = (petal.xOffset * 800).toInt(),
                                y = curY.toInt()
                            )
                        }
                        .rotate(petal.rotation)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Devotional Ritual Action Buttons Grid
        Text(
            text = "मन्दिर पूजा एवं अनुष्ठान",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BhagwaDark
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PoojaActionButton(
                label = "घंटी बजाएं",
                iconEmoji = "🔔",
                onClick = { viewModel.soundManager.playTempleBell() },
                modifier = Modifier.weight(1f),
                testTag = "pooja_btn_bell"
            )
            PoojaActionButton(
                label = "शंख नाद",
                iconEmoji = "🐚",
                onClick = { viewModel.soundManager.playShankhNaad() },
                modifier = Modifier.weight(1f),
                testTag = "pooja_btn_shankh"
            )
            PoojaActionButton(
                label = "पुष्प वर्षा",
                iconEmoji = "🌸",
                onClick = { viewModel.offerFlowers() },
                modifier = Modifier.weight(1f),
                testTag = "pooja_btn_flowers"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PoojaActionButton(
                label = if (isDiyaLit) "दीप शान्त" else "दीप जलाएं",
                iconEmoji = "🪔",
                onClick = { viewModel.toggleDiya() },
                modifier = Modifier.weight(1f),
                testTag = "pooja_btn_diya"
            )
            PoojaActionButton(
                label = if (isDhoopLit) "धूप शान्त" else "धूप जलाएं",
                iconEmoji = "💨",
                onClick = { viewModel.toggleDhoop() },
                modifier = Modifier.weight(1f),
                testTag = "pooja_btn_dhoop"
            )
            PoojaActionButton(
                label = if (isAartiRotating) "आरती रोकें" else "आरती करें",
                iconEmoji = "🕉️",
                onClick = { viewModel.toggleAartiThali() },
                modifier = Modifier.weight(1f),
                highlighted = isAartiRotating,
                testTag = "pooja_btn_aarti"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Deity Sacred Mantra Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("deity_mantra_card"),
            colors = CardDefaults.cardColors(
                containerColor = ChandanCard
            ),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "॥ ${selectedDeity.nameHindi} मूल मंत्र ॥",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = KumkumRed
                        )
                    )
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Mantra", "${selectedDeity.mantra}\n\n— श्री सनातनम्")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "मंत्र कॉपी हो गया!", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "कॉपी करें",
                            tint = BhagwaPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = selectedDeity.mantra,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = selectedDeity.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun PoojaActionButton(
    label: String,
    iconEmoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
    testTag: String = ""
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag(testTag),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted) BhagwaPrimary else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (highlighted) SwarnaGold else SwarnaGold.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = iconEmoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (highlighted) Color.White else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )
        }
    }
}
