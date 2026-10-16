package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FestivalItem
import com.example.ui.theme.BhagwaContainer
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.ChandanCard
import com.example.ui.theme.DivineGreen
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.MainViewModel

@Composable
fun PanchangScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val panchang = viewModel.todayPanchang
    val festivals = viewModel.festivals

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 96.dp)
            .testTag("panchang_screen")
    ) {
        // Calendar Header Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = BhagwaPrimary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    text = "॥ श्री पञ्चाङ्गम् ॥",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SwarnaGoldLight
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = panchang.gregorianDate,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
                Text(
                    text = "${panchang.dayOfWeekHindi} | ${panchang.monthNameHindi}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SwarnaGoldLight,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${panchang.vikramSamvat} • ${panchang.shakaSamvat}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFFFE0B2)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Planetary Tithi, Nakshatra, Yog, Karan Table
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "दैनिक पंचांग विवरण",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                PanchangRowItem("पक्ष", panchang.paksha)
                Divider(color = Color(0xFFEEEEEE))
                PanchangRowItem("तिथि", panchang.tithi)
                Divider(color = Color(0xFFEEEEEE))
                PanchangRowItem("नक्षत्र", panchang.nakshatra)
                Divider(color = Color(0xFFEEEEEE))
                PanchangRowItem("योग", panchang.yog)
                Divider(color = Color(0xFFEEEEEE))
                PanchangRowItem("करण", panchang.karan)
                Divider(color = Color(0xFFEEEEEE))
                PanchangRowItem("सूर्योदय / सूर्यास्त", "${panchang.sunRise} / ${panchang.sunSet}")
                Divider(color = Color(0xFFEEEEEE))
                PanchangRowItem("चन्द्रोदय", panchang.moonRise)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Shubh & Ashubh Muhurat Cards
        Text(
            text = "शुभ एवं अशुभ मुहूर्त",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BhagwaDark
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Abhijit Muhurat
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, DivineGreen)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "✨ अभिजीत मुहूर्त",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = DivineGreen
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = panchang.abhijitMuhurat,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "सर्व कार्य सिद्धिदायक",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                    )
                }
            }

            // Rahu Kaal
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, KumkumRed)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "⚠️ राहुकाल (अशुभ)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = KumkumRed
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = panchang.rahuKaal,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "शुभ कार्य वर्जित",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Choghadiya Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ChandanCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "दिन का चौघड़िया (Day Choghadiya)",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = panchang.choghadiyaDay,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Upcoming Festivals & Vrats Section
        Text(
            text = "आगामी प्रमुख सनातन व्रत व त्यौहार",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BhagwaDark
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        festivals.forEach { fest ->
            FestivalDetailCard(fest)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun PanchangRowItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun FestivalDetailCard(fest: FestivalItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fest.titleHindi,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark
                    )
                )
                Surface(
                    color = BhagwaContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = fest.dateDisplay,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KumkumRed,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "आराध्य: ${fest.deity}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = BhagwaPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = fest.significance,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                color = ChandanCard,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "पूजा विधि: ${fest.vidhi}",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF4E342E),
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}
