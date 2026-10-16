package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MainViewModel

@Composable
fun AppHomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(ColorMaroon, ColorDeep)))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top App Bar Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ColorRoyal),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "॥ श्री सनातनम् ॥",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ColorGold2
                        )
                    )
                    Text(
                        text = "Sanatan Seva Samiti Official App",
                        style = MaterialTheme.typography.bodySmall.copy(color = ColorWhite.copy(alpha = 0.8f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Dashboard Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "मुख्य डैशबोर्ड (Home Dashboard)\nआरती, चालीसा, पंचांग व लाइव दर्शन जल्द लोड हो रहे हैं...",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ColorGold3),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Footer Branding
            Text(
                text = "Developed by DG Developers (Deep Group)",
                style = MaterialTheme.typography.labelSmall.copy(color = ColorWhite.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    AppHomeScreen(viewModel = viewModel, modifier = modifier)
}
