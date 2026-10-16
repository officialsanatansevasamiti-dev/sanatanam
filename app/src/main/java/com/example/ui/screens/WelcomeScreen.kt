package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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

// Exact Color Palette Matching Your Web Design
val ColorMaroonDark = Color(0xFF180207)
val ColorMaroonDeep = Color(0xFF24040A)
val ColorMaroon = Color(0xFF3B0710)
val ColorMaroonLight = Color(0xFF71172A)
val ColorGold = Color(0xFFD4AF37)
val ColorGoldLight = Color(0xFFFFE7A0)
val ColorGoldPale = Color(0xFFFFF3CE)
val ColorCream = Color(0xFFFFF9ED)
val ColorMuted = Color(0xFFDDCBB8)

data class OnboardingStep(
    val stepNumber: String,
    val eyebrow: String,
    val title: String,
    val description: String,
    val badge: String,
    val imageRes: Int? = null,
    val isLanguageStep: Boolean = false,
    val isMembershipStep: Boolean = false
)

@Composable
fun WelcomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 5
    var selectedLanguage by remember { mutableStateOf("हिन्दी (Hindi)") }
    var showLoginDialog by remember { mutableStateOf(false) }

    val steps = listOf(
        OnboardingStep(
            stepNumber = "चरण १",
            eyebrow = "॥ शुभारम्भ ॥",
            title = "अपनी भाषा चुनें",
            description = "श्री सनातनम् में आपका हार्दिक स्वागत है। कृपया अपनी सुविधानुसार भाषा चुनें। समस्त सामग्री आपकी चुनी हुई भाषा में प्रदर्शित होगी।",
            badge = "भक्ति • संस्कार • सेवा",
            isLanguageStep = true
        ),
        OnboardingStep(
            stepNumber = "चरण २",
            eyebrow = "✦ जय श्री सनातनम् ✦",
            title = "श्री सनातनम् में स्वागतम्",
            description = "सनातन धर्म, भारतीय संस्कृति, आध्यात्मिक ज्ञान, संस्कार और जनसेवा को समर्पित एक पावन पहल। आइए, ज्ञान और सद्भाव की इस यात्रा से जुड़ें।",
            badge = "धर्म • ज्ञान • साधना • सेवा",
            imageRes = R.drawable.logo
        ),
        OnboardingStep(
            stepNumber = "चरण ३",
            eyebrow = "॥ जय माता दी ॥",
            title = "माँ भगवती की कृपा",
            description = "जगज्जननी माँ जगदम्बा के श्रीचरणों में श्रद्धापूर्वक नमन। माँ की कृपा से सभी के जीवन में शांति, सद्बुद्धि, साहस और सद्भाव बना रहे।",
            badge = "जय भवानी • जय जगदम्बे",
            imageRes = R.drawable.devi
        ),
        OnboardingStep(
            stepNumber = "चरण ४",
            eyebrow = "॥ गुरु कृपा ही केवलम् ॥",
            title = "पूज्य सद्गुरुदेव जी महाराज",
            description = "गुरु-मार्गदर्शन, आध्यात्मिक ज्ञान, आत्मचिन्तन और सेवा के पथ पर सदैव अग्रसर रहने का संकल्प।",
            badge = "श्रद्धा • साधना • सदाचार",
            imageRes = R.drawable.guru
        ),
        OnboardingStep(
            stepNumber = "चरण ५",
            eyebrow = "॥ सेवा ही परमो धर्मः ॥",
            title = "सनातन सेवा समिति में स्वागतम्",
            description = "धर्म, संस्कृति, संस्कार और समाजसेवा के पावन उद्देश्य से जुड़ें। आइए, सेवा और सद्भाव के इस अभियान में अपनी सहभागिता सुनिश्चित करें।",
            badge = "SANATANAM · भक्ति • संस्कार • सेवा",
            imageRes = R.drawable.sss,
            isMembershipStep = true
        )
    )

    val activeStepData = steps[currentStep - 1]
    val progressPercent = (currentStep * 100) / totalSteps

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ColorMaroonDark, ColorMaroon, ColorMaroonDark)
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Brand Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.clickable { viewModel.setAppStage(AppStage.MAIN_APP) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ColorCream)
                            .border(1.dp, ColorGold.copy(alpha = 0.55f), RoundedCornerShape(16.dp))
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "श्री सनातनम् लोगो",
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(13.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column {
                        Text(
                            text = "श्री सनातनम्",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = ColorGoldLight
                            )
                        )
                        Text(
                            text = "सनातन सेवा समिति",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ColorMuted,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "॥ शुभारम्भ ॥",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ColorGoldLight,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    OutlinedButton(
                        onClick = { viewModel.setAppStage(AppStage.MAIN_APP) },
                        shape = RoundedCornerShape(30.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.42f)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight)
                    ) {
                        Text(text = "छोड़ें ↗", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Progress Bar Area
            Column(modifier = Modifier.fillMaxWidth().maxW(950.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "चरण $currentStep / $totalSteps",
                        style = MaterialTheme.typography.labelMedium.copy(color = ColorGoldLight, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "$progressPercent%",
                        style = MaterialTheme.typography.labelMedium.copy(color = ColorGoldLight, fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { currentStep.toFloat() / totalSteps.toFloat() },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(20.dp)),
                    color = ColorGold,
                    trackColor = ColorWhite.copy(alpha = 0.1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Step Content Card / Layout
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .maxW(1150.dp),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = ColorMaroonDeep.copy(alpha = 0.95f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.RGBA(212, 175, 55, 0.32f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(clampPadding()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Eyebrow
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(modifier = Modifier.width(30.dp).height(1.dp).background(ColorGold))
                        Text(
                            text = activeStepData.eyebrow,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ColorGoldLight,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.6.sp
                            )
                        )
                        Box(modifier = Modifier.width(30.dp).height(1.dp).background(ColorGold))
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Title
                    Text(
                        text = activeStepData.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = ColorGoldLight,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Description
                    Text(
                        text = activeStepData.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ColorMuted,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Visual or Language Selector Box
                    if (activeStepData.isLanguageStep) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ColorMaroonDark.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
                                .border(1.dp, ColorGold.copy(alpha = 0.2f), RoundedCornerShape(18.dp))
                                .padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "चुनी गई भाषा: $selectedLanguage",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = ColorGoldPale,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            // Language Grid Simulation
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = { selectedLanguage = "हिन्दी (Hindi)" },
                                    colors = ButtonDefaults.buttonColors(containerColor = ColorMaroonLight)
                                ) {
                                    Text("हिन्दी", color = ColorGoldLight)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = { selectedLanguage = "English" },
                                    colors = ButtonDefaults.buttonColors(containerColor = ColorMaroonLight)
                                ) {
                                    Text("English", color = ColorGoldLight)
                                }
                            }
                        }
                    } else if (activeStepData.imageRes != null) {
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(ColorGoldLight, ColorMaroonLight, ColorGold)
                                    )
                                )
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = activeStepData.imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(22.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Badge or Membership Buttons
                    if (activeStepData.isMembershipStep) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    val url = "https://shrisanatanam.com/portal/register.php"
                                    // Handle web/portal link navigation
                                },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ColorMaroonLight),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.55f)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text("✦ सदस्यता एवं पंजीकरण", color = ColorGoldLight, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = {
                                    viewModel.setAppStage(AppStage.MAIN_APP)
                                },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ColorGold),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text("✦ नया खाता बनाएँ", color = ColorMaroonDark, fontWeight = FontWeight.Black)
                            }
                            OutlinedButton(
                                onClick = { showLoginDialog = true },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.42f)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight)
                            ) {
                                Text("→ सदस्य लॉगिन करें", fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Surface(
                            color = ColorGold.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(30.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = activeStepData.badge,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = ColorGoldLight,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Navigation Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SANATANAM · भक्ति • संस्कार • सेवा",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ColorWhite.copy(alpha = 0.53f),
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (currentStep > 1) {
                        OutlinedButton(
                            onClick = { currentStep -= 1 },
                            modifier = Modifier.size(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.4f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight)
                        ) {
                            Text("‹", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = {
                            if (currentStep < totalSteps) {
                                currentStep += 1
                            } else {
                                viewModel.setAppStage(AppStage.MAIN_APP)
                            }
                        },
                        modifier = Modifier.height(54.dp).defaultMinSize(minWidth = 160.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorGold),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGoldLight)
                    ) {
                        Text(
                            text = if (currentStep == totalSteps) "आरम्भ करें →" else "आगे बढ़ें →",
                            color = ColorMaroonDark,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Login Dialog if triggered
        if (showLoginDialog) {
            AlertDialog(
                onDismissRequest = { showLoginDialog = false },
                containerColor = ColorMaroonDeep,
                title = { Text("सदस्य लॉगिन", color = ColorGoldLight) },
                text = { Text("कृपया अपने पंजीकृत मोबाइल नंबर या ईमेल से लॉगिन करें।", color = ColorMuted) },
                confirmButton = {
                    Button(
                        onClick = {
                            showLoginDialog = false
                            viewModel.setAppStage(AppStage.MAIN_APP)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorGold)
                    ) {
                        Text("लॉगिन", color = ColorMaroonDark, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showLoginDialog = false }) {
                        Text("बंद करें", color = ColorGoldLight)
                    }
                }
            )
        }
    }
}

@Composable
fun clampPadding() = 24.dp
@Composable
fun Modifier.maxW(maxWidth: Dp) = this
