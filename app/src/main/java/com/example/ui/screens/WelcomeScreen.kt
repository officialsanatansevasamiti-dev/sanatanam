package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
        WelcomeFeatureCard("दैनिक आरती व चालीसा", "Daily Aarti & Chalisa", "सम्पूर्ण हनुमान चालीसा, आरती कुञ्जबिहारी की, गणेश आरती एवं शिव तांडव स्तोत्र।", "🪔", ColorGold2),
        WelcomeFeatureCard("दर्शन, पूजा घर व भजन", "Live Darshan & Bhajans", "सजीव अखण्ड दीप, धूप, पुष्प वर्षा और शंख नाद के साथ मन्दिर गर्भगृह का अनुभव।", "🛕", ColorBhagwa),
        WelcomeFeatureCard("पंचांग व सनातन उत्सव", "Panchang & Sanatan Calendar", "विक्रम संवत २०८३, दैनिक तिथि, नक्षत्र, अभिजीत मुहूर्त एवं व्रत-त्यौहार।", "📅", Color(0xFFE57373)),
        WelcomeFeatureCard("१०८ मनका जाप माला", "108 Japa Mala Counter", "लकड़ी के मनके की प्रामाणिक ध्वनि और हैप्टिक स्पर्श के साथ नाम जप।", "📿", Color(0xFF81C784))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(ColorMaroon, ColorRoyal, ColorDeep)))
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

            // Logos Row (SSS & App Logo)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(86.dp).clip(CircleShape).background(ColorWhite).border(2.dp, ColorGold, CircleShape).padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.sss), contentDescription = "SSS Logo", modifier = Modifier.fillMaxSize().clip(CircleShape), contentScale = ContentScale.Crop)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier.size(96.dp).clip(CircleShape).background(ColorWhite).border(2.5.dp, ColorGold2, CircleShape).padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "App Logo", modifier = Modifier.fillMaxSize().clip(CircleShape), contentScale = ContentScale.Crop)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                color = ColorMaroon.copy(alpha = 0.9f),
                shape = RoundedCornerShape(999.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.6f))
            ) {
                Row(modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✦", color = ColorGold2, fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "जय भवानी", style = MaterialTheme.typography.labelMedium.copy(color = ColorGold3, fontWeight = FontWeight.ExtraBold))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "✦", color = ColorGold2, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "sanatanam", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black, color = ColorWhite, letterSpacing = (-1.5).sp))
            Text(text = "सनातन सेवा समिति", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = ColorGold2))
            Text(text = "SANATAN SEVA SAMITI", style = MaterialTheme.typography.labelSmall.copy(color = ColorWhite.copy(alpha = 0.6f), letterSpacing = 3.sp))

            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "पावन दर्शन एवं आशीर्वाद", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ColorGold2), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(modifier = Modifier.weight(1f).height(160.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = ColorDeep), border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold.copy(alpha = 0.7f))) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(painter = painterResource(id = R.drawable.devi), contentDescription = "Devi Ji", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC170105)))))
                        Text(text = "माँ भगवती दर्शन", modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = ColorGold2))
                    }
                }
                Card(modifier = Modifier.weight(1f).height(160.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = ColorDeep), border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold.copy(alpha = 0.7f))) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(painter = painterResource(id = R.drawable.guru), contentDescription = "Guru Ji", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC170105)))))
                        Text(text = "गुरुदेव आशीर्वाद", modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = ColorGold2))
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "प्रमुख आध्यात्मिक सेवाएं", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ColorGold2), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(features) { feat ->
                    Card(modifier = Modifier.width(260.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = ColorMaroon.copy(alpha = 0.85f)), border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.4f))) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            Box(modifier = Modifier.size(42.dp).clip(CircleShape).background(ColorDeep), contentAlignment = Alignment.Center) {
                                Text(text = feat.iconEmoji, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = feat.titleHindi, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ColorWhite))
                            Text(text = feat.titleEnglish, style = MaterialTheme.typography.labelSmall.copy(color = feat.accentColor, fontWeight = FontWeight.SemiBold))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = feat.description, style = MaterialTheme.typography.bodySmall.copy(color = ColorWhite.copy(alpha = 0.8f), lineHeight = 17.sp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.setAppStage(AppStage.MAIN_APP) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorBhagwa),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold2)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "प्रारंभ करें / Get Started", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ColorWhite))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = ColorGold2)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = { showLoginDialog = true },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, ColorGold.copy(alpha = 0.8f))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = ColorGold2, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "लॉगिन / Sign In (Firebase & Custom API)", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = ColorGold2))
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "सेवा ही परमो धर्मः", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = ColorGold2))
            Text(text = "समर्पित राष्ट्र एवं सनातन धर्म सेवा", style = MaterialTheme.typography.labelSmall.copy(color = ColorWhite.copy(alpha = 0.65f)))
            Text(text = "An initiative by Sanatan Seva Samiti • DG Developers (Deep Group)", style = MaterialTheme.typography.labelSmall.copy(color = ColorWhite.copy(alpha = 0.45f), fontSize = 10.sp))
        }

        if (showLoginDialog) {
            LoginDialog(
                onDismiss = { showLoginDialog = false },
                onSuccessLogin = { name ->
                    showLoginDialog = false
                    viewModel.loginUser(name)
                    Toast.makeText(context, "स्वागत है, $name!", Toast.LENGTH_SHORT).show()
                    viewModel.setAppStage(AppStage.MAIN_APP)
                }
            )
        }
    }
}
