package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ContentCategory
import com.example.ui.theme.BhagwaContainer
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.ChandanCard
import com.example.ui.theme.ChandanCream
import com.example.ui.theme.DivineGreen
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenDestination

data class QuickActionItem(
    val titleHindi: String,
    val iconEmoji: String,
    val category: ContentCategory? = null,
    val destination: ScreenDestination? = null,
    val color: Color
)

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val panchang = viewModel.todayPanchang
    val suvichar = viewModel.suvicharList.firstOrNull()
    val allItems by viewModel.devotionalItems.collectAsState()
    val featuredAarti = allItems.firstOrNull { it.id == "hanuman_chalisa" } ?: allItems.firstOrNull()

    val quickActions = listOf(
        QuickActionItem("आरती", "🪔", category = ContentCategory.AARTI, color = BhagwaPrimary),
        QuickActionItem("चालीसा", "📖", category = ContentCategory.CHALISA, color = KumkumRed),
        QuickActionItem("स्तोत्र", "📜", category = ContentCategory.STOTRA, color = Color(0xFF6A1B9A)),
        QuickActionItem("मंत्र", "🕉️", category = ContentCategory.MANTRA, color = SwarnaGold),
        QuickActionItem("पूजा घर", "🛕", destination = ScreenDestination.MANDIR, color = Color(0xFFD84315)),
        QuickActionItem("जाप माला", "📿", destination = ScreenDestination.JAPA, color = DivineGreen),
        QuickActionItem("पंचांग", "📅", destination = ScreenDestination.PANCHANG, color = Color(0xFF0277BD)),
        QuickActionItem("समिति", "🚩", destination = ScreenDestination.ABOUT, color = BhagwaDark)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Hero Image Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_shri_mandir_hero),
                    contentDescription = "श्री सनातन मन्दिर",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x80140D0B),
                                    Color(0xF0140D0B)
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, SwarnaGoldLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "श्री सनातनम्",
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Surface(
                            color = BhagwaPrimary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "॥ धर्मो रक्षति रक्षितः ॥",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "श्री सनातनम्",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = SwarnaGoldLight
                            )
                        )
                        Text(
                            text = "सनातन सेवा समिति द्वारा जन-कल्याणार्थ समर्पित",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFE0E0E0)
                            )
                        )
                    }
                }
            }
        }

        // Today's Panchang Glance Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .testTag("panchang_glance_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, SwarnaGold.copy(alpha = 0.6f))
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "📅", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "आज का पंचांग",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BhagwaDark
                                )
                            )
                        }
                        Text(
                            text = panchang.gregorianDate,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${panchang.vikramSamvat} | ${panchang.monthNameHindi}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KumkumRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "तिथि: ${panchang.tithi}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    panchang.vratOrFestival?.let { vrat ->
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            color = BhagwaContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "🚩 पर्व: $vrat",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = BhagwaDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "✨ अभिजीत मुहूर्त",
                                style = MaterialTheme.typography.labelSmall.copy(color = DivineGreen, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = panchang.abhijitMuhurat,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                        Column {
                            Text(
                                text = "⚠️ राहुकाल (अशुभ)",
                                style = MaterialTheme.typography.labelSmall.copy(color = KumkumRed, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = panchang.rahuKaal,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { viewModel.navigateTo(ScreenDestination.PANCHANG) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BhagwaPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("विस्तृत पंचांग व शुभ मुहूर्त देखें", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Quick Action Shortcuts Grid
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "सनातन सेवाएं व संग्रह",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BhagwaDark
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                // 2 Rows of 4 items each
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickActions.take(4).forEach { action ->
                        QuickActionCard(
                            action = action,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (action.destination != null) {
                                    viewModel.navigateTo(action.destination)
                                } else if (action.category != null) {
                                    viewModel.setCategory(action.category)
                                    viewModel.navigateTo(ScreenDestination.SANGRAH)
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickActions.drop(4).take(4).forEach { action ->
                        QuickActionCard(
                            action = action,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (action.destination != null) {
                                    viewModel.navigateTo(action.destination)
                                } else if (action.category != null) {
                                    viewModel.setCategory(action.category)
                                    viewModel.navigateTo(ScreenDestination.SANGRAH)
                                }
                            }
                        )
                    }
                }
            }
        }

        // Daily Suvichar / Gita Shloka
        suvichar?.let { quote ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .testTag("daily_suvichar_card"),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.5f))
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🕉️", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "आज का सुविचार (भगवद्गीता)",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BhagwaDark
                                    )
                                )
                            }
                            IconButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Gita Sloka", "${quote.sanskritVerse}\n\n${quote.hindiMeaning}\n\n— ${quote.source}")
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "श्लोक कॉपी हो गया!", Toast.LENGTH_SHORT).show()
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
                            text = quote.sanskritVerse,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = KumkumRed,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = quote.hindiMeaning,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "— ${quote.source}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = BhagwaPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }

        // Featured Aarti Card
        featuredAarti?.let { aarti ->
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "दैनिक पावन पाठ",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BhagwaDark
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.openReader(aarti) }
                            .testTag("featured_aarti_card"),
                        colors = CardDefaults.cardColors(
                            containerColor = BhagwaContainer
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(BhagwaPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🕉️", fontSize = 24.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = aarti.titleHindi,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BhagwaDark
                                    )
                                )
                                Text(
                                    text = aarti.summary,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    maxLines = 2
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "पाठ करें",
                                tint = BhagwaDark
                            )
                        }
                    }
                }
            }
        }

        // Upcoming Festivals List
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "आगामी सनातन पर्व व व्रत",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BhagwaDark
                        )
                    )
                    Text(
                        text = "सभी देखें >",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = BhagwaPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable { viewModel.navigateTo(ScreenDestination.PANCHANG) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.festivals) { fest ->
                        Card(
                            modifier = Modifier
                                .width(220.dp)
                                .clickable { viewModel.navigateTo(ScreenDestination.PANCHANG) },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SwarnaGold.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = fest.dateDisplay,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = KumkumRed,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = fest.titleHindi,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BhagwaDark
                                    ),
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = fest.significance,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    action: QuickActionItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag("action_${action.titleHindi}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, action.color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = action.iconEmoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = action.titleHindi,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )
        }
    }
}
