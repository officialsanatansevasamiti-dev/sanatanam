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
import androidx.compose.material.icons.filled.Person
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
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

// Exact Color Scheme from specification
val ColorDeep = Color(0xFF170105)
val ColorMaroon = Color(0xFF39040B)
val ColorRoyal = Color(0xFF650711)
val ColorBhagwa = Color(0xFFA92808)
val ColorGold = Color(0xFFDCA72F)
val ColorGold2 = Color(0xFFFFD866)
val ColorGold3 = Color(0xFFFFF0A8)
val ColorWhite = Color(0xFFFFFFFF)

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
            accentColor = ColorGold2
        ),
        WelcomeFeatureCard(
            titleHindi = "दर्शन, पूजा घर व भजन",
            titleEnglish = "Live Darshan & Bhajans",
            description = "सजीव अखण्ड दीप, धूप, पुष्प वर्षा और शंख नाद के साथ अपने मोबाइल में मन्दिर गर्भगृह का अनुभव।",
            iconEmoji = "🛕",
            accentColor = ColorBhagwa
        ),
        WelcomeFeatureCard(
            titleHindi = "पंचांग व सनातन उत्सव",
            titleEnglish = "Panchang & Sanatan Calendar",
            description = "विक्रम संवत २०८३, दैनिक तिथि, नक्षत्र, अभिजीत मुहूर्त, राहुकाल एवं समस्त सनातन व्रतों की सम्पूर्ण विधि।",
            iconEmoji = "📅",
            accentColor = Color(0xFFE57373)
        ),
        WelcomeFeatureCard(
            titleHindi = "१०८ मनका जाप माला",
            titleEnglish = "108 Japa Mala Counter",
            description = "लकड़ी के मनके की प्रामाणिक ध्वनि, हैप्टिक स्पर्श और लक्ष्य निर्धारण के साथ दिव्य नाम जप।",
            iconEmoji = "📿",
            accentColor = Color(0xFF81C784)
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorMaroon,
                        ColorRoyal,
                        ColorDeep
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

            // Dual Brand Emblems: App Logo & Sanatan Seva Samiti (SSS) Logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Official Sanatan Seva Samiti Logo (assets/sss.png)
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                        .background(ColorWhite)
                        .border(2.dp, ColorGold, CircleShape)
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sss),
                        contentDescription = "Sanatan Seva Samiti Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Official App Logo (assets/logo.png)
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(ColorWhite)
                        .border(2.5.dp, ColorGold2, CircleShape)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "SHRI SANATANAM Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Motto Badge: ✦ जय भवानी ✦
            Surface(
                color = ColorMaroon.copy(alpha = 0.9f),
                shape = RoundedCornerShape(999.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "✦", color = ColorGold2, fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "जय भवानी",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ColorGold3,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.7.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "✦", color = ColorGold2, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Brand Titles
            Text(
                text = "sanatanam",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = ColorWhite,
                    letterSpacing = (-1.5).sp,
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = "सनातन सेवा समिति",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ColorGold2,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = "SANATAN SEVA SAMITI",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = ColorWhite.copy(alpha = 0.6f),
                    letterSpacing = 3.sp
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Sacred Darshan Cards: Devi Ji Asset & Guru Ji Asset
            Text(
                text = "पावन दर्शन एवं आशीर्वाद",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorGold2
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Devi Ji Asset Card (assets/devi.png)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(160.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorDeep),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold.copy(alpha = 0.7f))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.devi),
                            contentDescription = "Devi Ji Asset",
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
                                            Color(0xCC170105)
                                        )
                                    )
                                )
                        )
                        Text(
                            text = "माँ भगवती दर्शन",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(8.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ColorGold2
                            )
                        )
                    }
                }

                // Guru Ji Asset Card (assets/guru.png)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(160.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorDeep),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold.copy(alpha = 0.7f))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.guru),
                            contentDescription = "Guru Ji Asset",
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
                                            Color(0xCC170105)
                                        )
                                    )
                                )
                        )
                        Text(
                            text = "गुरुदेव आशीर्वाद",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(8.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ColorGold2
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Feature Highlights Slider / Cards
            Text(
                text = "प्रमुख आध्यात्मिक सेवाएं",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorGold2
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
                        colors = CardDefaults.cardColors(containerColor = ColorMaroon.copy(alpha = 0.85f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(ColorDeep),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = feat.iconEmoji, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = feat.titleHindi,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ColorWhite
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
                                    color = ColorWhite.copy(alpha = 0.8f),
                                    lineHeight = 17.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                colors = ButtonDefaults.buttonColors(containerColor = ColorBhagwa),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold2)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "प्रारंभ करें / Get Started",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ColorWhite
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "आगे बढ़ें",
                        tint = ColorGold2
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
                border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold.copy(alpha = 0.8f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Login",
                        tint = ColorGold2,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "लॉगिन / Sign In (Firebase & Custom API)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = ColorGold2
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Mandatory Footer
            Text(
                text = "सेवा ही परमो धर्मः",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorGold2
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "समर्पित राष्ट्र एवं सनातन धर्म सेवा",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ColorWhite.copy(alpha = 0.65f)
                )
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "An initiative by Sanatan Seva Samiti • DG Developers (Deep Group)",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ColorWhite.copy(alpha = 0.45f),
                    fontSize = 10.sp
                )
            )

            Spacer(modifier = Modifier.height(14.dp))
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
            colors = CardDefaults.cardColors(containerColor = ColorDeep),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold)
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
                            color = ColorGold2
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "बंद करें", tint = ColorWhite)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Official Logo Thumbnail
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(ColorWhite)
                        .border(1.5.dp, ColorGold, CircleShape),
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
                    label = { Text("भक्त का नाम (Devotee Name)", color = ColorGold3) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorGold2,
                        unfocusedBorderColor = ColorGold.copy(alpha = 0.5f),
                        focusedTextColor = ColorWhite,
                        unfocusedTextColor = ColorWhite
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Mobile Number Field
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("मोबाइल नंबर / ईमेल (Firebase / Custom API)", color = ColorGold3) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorGold2,
                        unfocusedBorderColor = ColorGold.copy(alpha = 0.5f),
                        focusedTextColor = ColorWhite,
                        unfocusedTextColor = ColorWhite
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Login via Firebase/Custom API Button
                Button(
                    onClick = {
                        val name = if (devoteeName.isNotBlank()) devoteeName else "सनातन भक्त"
                        onSuccessLogin(name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorBhagwa),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold2)
                ) {
                    Text("लॉगिन करें (Firebase Connected)", fontWeight = FontWeight.Bold, color = ColorWhite)
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
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.7f))
                ) {
                    Text("बिना लॉगिन आगे बढ़ें (Guest Mode)", color = ColorGold2)
                }
            }
        }
    }
}
