package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DevotionalItem
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.ChandanCard
import com.example.ui.theme.ChandanCream
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.MainViewModel

@Composable
fun ContentReaderSheet(
    item: DevotionalItem,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isAartiPlaying by viewModel.soundManager.isAartiPlaying.collectAsState()
    val fontSizeStep by viewModel.readerFontSizeStep.collectAsState()
    val favorites by viewModel.favoriteIds.collectAsState()
    val isFav = favorites.contains(item.id)

    // Handle Android system back gesture/button
    BackHandler {
        viewModel.closeReader()
    }

    val baseFontSize = when (fontSizeStep) {
        0 -> 16.sp
        2 -> 22.sp
        else -> 19.sp
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .testTag("reader_sheet"),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                // Top Action Bar
                Surface(
                    color = BhagwaPrimary,
                    tonalElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { viewModel.closeReader() },
                            modifier = Modifier.testTag("reader_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "वापस जाएं (Back)",
                                tint = Color.White
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.titleHindi,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                maxLines = 1
                            )
                            Text(
                                text = item.deity,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SwarnaGoldLight
                                )
                            )
                        }

                        // Font size stepper
                        IconButton(
                            onClick = {
                                val next = (fontSizeStep + 1) % 3
                                viewModel.setReaderFontSizeStep(next)
                            },
                            modifier = Modifier.testTag("reader_font_size_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = "अक्षर आकार (Font Size)",
                                tint = Color.White
                            )
                        }

                        // Bookmark toggle
                        IconButton(
                            onClick = { viewModel.toggleFavorite(item.id) },
                            modifier = Modifier.testTag("reader_favorite_btn")
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "पसंदीदा (Bookmark)",
                                tint = SwarnaGold
                            )
                        }

                        // Copy
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText(item.titleHindi, "${item.titleHindi}\n\n${item.lyricsHindi}\n\n— श्री सनातनम् (Sanatan Seva Samiti)")
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "पाठ कॉपी हो गया!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.testTag("reader_copy_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "कॉपी करें (Copy)",
                                tint = Color.White
                            )
                        }

                        // Share
                        IconButton(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "${item.titleHindi}\n\n${item.lyricsHindi}\n\nडाउनलोड करें श्री सनातनम् ऐप: sanatansevasamiti.shrisanatanam.com")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "साझा करें"))
                            },
                            modifier = Modifier.testTag("reader_share_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "साझा करें (Share)",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    // Title Header Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SwarnaGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "॥ श्री गणेशाय नमः ॥",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = KumkumRed
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.titleHindi,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BhagwaDark,
                                    textAlign = TextAlign.Center
                                )
                            )
                            Text(
                                text = item.titleEnglish,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🕉️ ${item.deity}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = BhagwaPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "📖 ${item.category.labelHindi}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = KumkumRed,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Audio Chime / Bell accompaniment banner
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isAartiPlaying) BhagwaPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isAartiPlaying) BhagwaPrimary else SwarnaGold.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { viewModel.soundManager.toggleAartiRhythm() },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (isAartiPlaying) KumkumRed else BhagwaPrimary)
                            ) {
                                Icon(
                                    imageVector = if (isAartiPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = "घंटी ताल (Bell Rhythm)",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isAartiPlaying) "🔔 आरती घंटी ताल चालू है..." else "🔔 पाठ के साथ मन्दिर घंटी ताल बजाएं",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "पाठ करते समय मन्दिर का दिव्य अनुभव प्राप्त करें",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Main Devanagari Lyrics Body
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = item.lyricsHindi,
                                fontSize = baseFontSize,
                                lineHeight = (baseFontSize.value * 1.6f).sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (item.meaningHindi.isNotBlank()) {
                                Spacer(modifier = Modifier.height(24.dp))
                                Divider(color = SwarnaGold.copy(alpha = 0.4f))
                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "॥ दिव्य भावार्थ ॥",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BhagwaDark,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = item.meaningHindi,
                                    fontSize = (baseFontSize.value - 2).sp,
                                    lineHeight = ((baseFontSize.value - 2) * 1.5f).sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
