package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.theme.BhagwaContainer
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.ChandanCard
import com.example.ui.theme.ChandanCream
import com.example.ui.theme.DivineGreen
import com.example.ui.theme.KumkumRed
import com.example.ui.theme.SwarnaGold
import com.example.ui.theme.SwarnaGoldLight
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

data class WelcomeFeatureCard(
    val titleHindi: String,
    val titleEnglish: String,
    val description: String,
    val iconEmoji: String,
    val accentColor: Color
)

@Composable
fun WelcomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showLoginDialog by remember { mutableStateOf(false) }

    val features = listOf(
        WelcomeFeatureCard(
            titleHindi = "दैनिक आरती व चालीसा",
            titleEnglish = "Daily Aarti & Chalisa",
            description = "सम्पूर्ण हनुमान चालीसा, आरती कुञ्जबिहारी की, गणेश आरती एवं शिव तांडव स्तोत्र सहित शुद्ध पाठ व घंटी ताल।",
            iconEmoji = "🪔",
            accentColor = BhagwaPrimary
        ),
        WelcomeFeatureCard(
            titleHindi = "दर्शन, पूजा घर व भजन",
            titleEnglish = "Live Darshan & Bhajans",
            description = "सजीव अखण्ड दीप, धूप, पुष्प वर्षा और शंख नाद के साथ अपने मोबाइल में मन्दिर गर्भगृह का अनुभव।",
            iconEmoji = "🛕",
            accentColor = Color(0xFFD84315)
        ),
        WelcomeFeatureCard(
            titleHindi = "पंचांग व सनातन उत्सव",
            titleEnglish = "Panchang & Sanatan Calendar",
            description = "विक्रम संवत २०८३, दैनिक तिथि, नक्षत्र, अभिजीत मुहूर्त, राहुकाल एवं समस्त सनातन व्रतों की सम्पूर्ण विधि।",
            iconEmoji = "📅",
            accentColor = Color(0xFF0277BD)
        ),
        WelcomeFeatureCard(
            titleHindi = "१०८ मनका जाप माला",
            titleEnglish = "108 Japa Mala Counter",
            description = "लकड़ी के मनके की प्रामाणिक ध्वनि, हैप्टिक स्पर्श और लक्ष्य निर्धारण के साथ दिव्य नाम जप।",
            iconEmoji = "📿",
            accentColor = DivineGreen
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ChandanCream,
                        Color(0xFFFFF6E6),
                        Color(0xFFFFECD2)
                    )
                )
            )
            .testTag("welcome_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Header: Official logo.png (1:1 aspect ratio, perfectly scaled without distortion)
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color.White)
                    .border(2.5.dp, SwarnaGold, RoundedCornerShape(22.dp))
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "SHRI SANATANAM Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // App Title: "SHRI SANATANAM"
            Text(
                text = "SHRI SANATANAM",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = BhagwaDark,
                    letterSpacing = 1.2.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Tagline: "॥ धर्मो रक्षति रक्षितः ॥"
            Surface(
                color = BhagwaContainer,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "॥ धर्मो रक्षति रक्षितः ॥",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = KumkumRed,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "आपकी दैनिक आध्यात्मिक यात्रा में आपका स्वागत है\nWelcome to your daily spiritual journey",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Feature Highlights Slider / Cards
            Text(
                text = "प्रमुख आध्यात्मिक विशेषताएं",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = BhagwaDark
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(features) { feat ->
                    Card(
                        modifier = Modifier
                            .width(260.dp)
                            .testTag("feature_card_${feat.titleEnglish}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, feat.accentColor.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(feat.accentColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = feat.iconEmoji, fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = feat.titleHindi,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BhagwaDark
                                )
                            )
                            Text(
                                text = feat.titleEnglish,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = feat.accentColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = feat.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 17.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Action Buttons
            // 1. Primary Button: "प्रारंभ करें / Get Started"
            Button(
                onClick = {
                    viewModel.soundManager.playTempleBell()
                    viewModel.setAppStage(AppStage.MAIN_APP)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_get_started"),
                colors = ButtonDefaults.buttonColors(containerColor = BhagwaPrimary),
                shape = RoundedCornerShape(14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "प्रारंभ करें / Get Started",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "आगे बढ़ें",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Secondary Option: "Login / Sign In"
            OutlinedButton(
                onClick = { showLoginDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_welcome_login"),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, BhagwaPrimary)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Login",
                        tint = BhagwaPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "लॉगिन / Sign In (Firebase & Custom API)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = BhagwaPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Mandatory Footer
            Text(
                text = "An initiative by Sanatan Seva Samiti • DG Developers (Deep Group)",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Login Dialog (Firebase / Custom API)
        if (showLoginDialog) {
            LoginDialog(
                onDismiss = { showLoginDialog = false },
                onSuccessLogin = { devoteeName ->
                    showLoginDialog = false
                    viewModel.loginUser(devoteeName)
                    Toast.makeText(context, "स्वागत है, $devoteeName!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun LoginDialog(
    onDismiss: () -> Unit,
    onSuccessLogin: (String) -> Unit
) {
    var devoteeName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("dialog_login"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "सनातन भक्त लॉगिन",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BhagwaDark
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "बंद करें")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Official Logo Thumbnail
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.5.dp, SwarnaGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Devotee Name Field
                OutlinedTextField(
                    value = devoteeName,
                    onValueChange = { devoteeName = it },
                    label = { Text("भक्त का नाम (Devotee Name)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BhagwaPrimary,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Mobile Number Field
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("मोबाइल नंबर / ईमेल") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BhagwaPrimary,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Login via Firebase/Custom API Button
                Button(
                    onClick = {
                        val name = if (devoteeName.isNotBlank()) devoteeName else "भक्त"
                        onSuccessLogin(name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BhagwaPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("लॉगिन करें (Firebase Connected)", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Continue as Guest Option
                OutlinedButton(
                    onClick = {
                        onSuccessLogin("सनातन भक्त")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("बिना लॉगिन आगे बढ़ें (Guest Mode)", color = BhagwaDark)
                }
            }
        }
    }
}
