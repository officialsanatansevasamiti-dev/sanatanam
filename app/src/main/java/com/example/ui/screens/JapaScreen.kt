package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BhagwaContainer
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaLight
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.ChandanCard
import com.example.ui.theme.DivineGreen
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldDark
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.MainViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun JapaScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val beadCount by viewModel.malaBeadCount.collectAsState()
    val totalMalas by viewModel.totalMalasCompleted.collectAsState()
    val targetMalas by viewModel.targetMalas.collectAsState()
    val currentMantra by viewModel.currentMantra.collectAsState()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "button_scale"
    )

    val progress = beadCount.toFloat() / 108f

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 96.dp)
            .testTag("japa_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mantra Selector Section
        Text(
            text = "जाप हेतु पावन मंत्र चुनें",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = BhagwaDark
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.availableMantras) { mantra ->
                val isSelected = mantra == currentMantra
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectMantra(mantra) },
                    label = {
                        Text(
                            text = mantra.take(18) + if (mantra.length > 18) "..." else "",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BhagwaPrimary,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("mantra_chip_${mantra.take(6)}")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Selected Mantra Display Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("current_mantra_card"),
            colors = CardDefaults.cardColors(containerColor = ChandanCard),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, SwarnaGold)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "॥ श्री हरि ॐ ॥",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = KumkumRed
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentMantra,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big Circular 108 Japa Mala Wheel
        Box(
            modifier = Modifier
                .size(260.dp)
                .testTag("japa_wheel_container"),
            contentAlignment = Alignment.Center
        ) {
            // Draw Circular Mala Beads & Progress Track
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.width / 2 - 24.dp.toPx()

                // Background Ring
                drawCircle(
                    color = SwarnaGold.copy(alpha = 0.2f),
                    radius = radius,
                    style = Stroke(width = 8.dp.toPx())
                )

                // Foreground Progress Arc
                drawArc(
                    brush = Brush.sweepGradient(
                        listOf(
                            BhagwaPrimary,
                            KumkumRed,
                            SwarnaGold,
                            BhagwaPrimary
                        )
                    ),
                    startAngle = -90f,
                    sweepAngle = progress * 360f,
                    useCenter = false,
                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw representative 27 primary bead markers (quarter-mala dots)
                val totalBeadMarkers = 27
                for (i in 0 until totalBeadMarkers) {
                    val angleRad = Math.toRadians((i.toDouble() / totalBeadMarkers) * 360.0 - 90.0)
                    val beadX = center.x + radius * cos(angleRad).toFloat()
                    val beadY = center.y + radius * sin(angleRad).toFloat()

                    val isPassed = (i.toFloat() / totalBeadMarkers) <= progress
                    drawCircle(
                        color = if (isPassed) KumkumRed else SwarnaGoldDark,
                        radius = if (i % 9 == 0) 6.dp.toPx() else 4.dp.toPx(),
                        center = Offset(beadX, beadY)
                    )
                }

                // Sumeru (Guru Bead) at the top
                drawCircle(
                    color = BhagwaPrimary,
                    radius = 9.dp.toPx(),
                    center = Offset(center.x, center.y - radius)
                )
            }

            // Central Touch Button (Tapping here advances the bead)
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .scale(buttonScale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                BhagwaLight,
                                BhagwaPrimary,
                                BhagwaDark
                            )
                        )
                    )
                    .border(3.dp, SwarnaGold, CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.incrementBead()
                    }
                    .testTag("japa_counter_touch_area"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$beadCount",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "/ १०८ मनके",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SwarnaGoldLight
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "मनका फेरें 📿",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Target Malas and Progress Counters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.4f)),
                modifier = Modifier.weight(1f).padding(end = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "पूर्ण माला", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                    Text(
                        text = "$totalMalas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BhagwaDark
                    )
                    Text(text = "माला", fontSize = 11.sp, color = BhagwaPrimary)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.4f)),
                modifier = Modifier.weight(1f).padding(start = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "लक्ष्य माला", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                    Text(
                        text = "$targetMalas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DivineGreen
                    )
                    Text(text = "माला", fontSize = 11.sp, color = DivineGreen)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Target Picker Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "लक्ष्य: ",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            listOf(1, 3, 11, 21, 108).forEach { target ->
                FilterChip(
                    selected = targetMalas == target,
                    onClick = { viewModel.setTargetMalas(target) },
                    label = { Text("$target") },
                    modifier = Modifier.padding(horizontal = 3.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BhagwaPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Counter Button
        OutlinedButton(
            onClick = { viewModel.resetJapa() },
            modifier = Modifier.testTag("btn_reset_japa"),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "रीसेट करें",
                tint = KumkumRed
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "वर्तमान मनका रीसेट करें (Reset)", color = KumkumRed)
        }
    }
}
