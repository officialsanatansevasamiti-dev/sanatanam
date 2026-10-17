package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.R
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

// ======================================================================
// Exact Royal Sanatan Color Palette matching Web/HTML (#3b0710, #180207, #d4af37)
// ======================================================================
val ColorMaroonDark = Color(0xFF180207)
val ColorMaroonDeep = Color(0xFF24040A)
val ColorMaroon = Color(0xFF3B0710)
val ColorMaroonLight = Color(0xFF71172A)
val ColorGold = Color(0xFFD4AF37)
val ColorGoldLight = Color(0xFFFFE7A0)
val ColorGoldPale = Color(0xFFFFF3CE)
val ColorCream = Color(0xFFFFF9ED)
val ColorMuted = Color(0xFFDDCBB8)
val ColorWhite = Color(0xFFFFFFFF)

// Language Data Model matching Web Master List
data class LangModel(val code: String, val name: String, val native: String, val nums: List<String>)

val LanguageMaster = listOf(
    LangModel("hi", "Hindi", "हिन्दी", listOf("१", "२", "३", "४", "५")),
    LangModel("sa", "Sanskrit", "संस्कृतम्", listOf("१", "२", "३", "४", "५")),
    LangModel("en", "English", "English", listOf("1", "2", "3", "4", "5")),
    LangModel("bn", "Bengali", "বাংলা", listOf("১", "২", "৩", "৪", "৫")),
    LangModel("mr", "Marathi", "मराठी", listOf("१", "२", "३", "४", "५")),
    LangModel("te", "Telugu", "తెలుగు", listOf("౧", "౨", "౩", "౪", "౫")),
    LangModel("ta", "Tamil", "தமிழ்", listOf("௧", "௨", "௩", "௪", "௫")),
    LangModel("gu", "Gujarati", "ગુજરાતી", listOf("૧", "૨", "૩", "૪", "૫")),
    LangModel("ur", "Urdu", "اردو", listOf("۱", "۲", "۳", "۴", "۵")),
    LangModel("kn", "Kannada", "ಕನ್ನಡ", listOf("೧", "೨", "೩", "೪", "೫")),
    LangModel("or", "Odia", "ଓଡ଼ିଆ", listOf("୧", "୨", "୩", "୪", "୫")),
    LangModel("ml", "Malayalam", "മലയാളം", listOf("൧", "൨", "൩", "൪", "൫")),
    LangModel("pa", "Punjabi", "ਪੰਜਾਬੀ", listOf("੧", "੨", "੩", "੪", "੫")),
    LangModel("as", "Assamese", "অসমীয়া", listOf("১", "২", "৩", "৪", "৫")),
    LangModel("mai", "Maithili", "मैथिली", listOf("१", "२", "३", "४", "५")),
    LangModel("doi", "Dogri", "डोगरी", listOf("१", "२", "३", "४", "५")),
    LangModel("kok", "Konkani", "कोंकणी", listOf("१", "२", "३", "४", "५")),
    LangModel("sd", "Sindhi", "सिंधी / سنڌي", listOf("१", "२", "३", "४", "५")),
    LangModel("ne", "Nepali", "नेपाली", listOf("१", "२", "३", "४", "५")),
    LangModel("ks", "Kashmiri", "कॉशुर / کٲشُر", listOf("१", "२", "३", "४", "५")),
    LangModel("brx", "Bodo", "बर'/बड़ो", listOf("1", "2", "3", "4", "5")),
    LangModel("mni", "Manipuri", "মৈতৈলোন্", listOf("১", "২", "৩", "৪", "৫")),
    LangModel("sat", "Santali", "ᱥᱟᱱᱛᱟᱲᱤ", listOf("᱑", "᱒", "᱓", "᱔", "᱕")),
    LangModel("tcy", "Tulu", "ತುಳು", listOf("೧", "೨", "೩", "೪", "೫"))
)

// Multilingual Dictionary map for 5 Onboarding Steps
val AppTranslations = mapOf(
    "hi" to mapOf(
        "brand_name" to "श्री सनातनम्", "brand_sub" to "सनातन सेवा समिति", "header_tag" to "॥ शुभारम्भ ॥", "skip_btn" to "छोड़ें ↗",
        "btn_next" to "आगे बढ़ें →", "btn_start" to "आरम्भ करें →",
        "step1_title" to "अपनी भाषा चुनें", "step1_desc" to "श्री सनातनम् में आपका हार्दिक स्वागत है। कृपया अपनी सुविधानुसार भाषा चुनें।",
        "step1_note" to "समस्त सामग्री आपकी चुनी हुई भाषा में प्रदर्शित होगी।", "step1_eyebrow" to "॥ शुभारम्भ ॥",
        "step1_subheading" to "भक्ति • संस्कार • सेवा", "step1_subdesc" to "सनातन संस्कृति, आध्यात्मिक ज्ञान और जनसेवा से जुड़ने की आपकी यात्रा यहीं से आरम्भ होती है।",
        "step2_eyebrow" to "✦ जय श्री सनातनम् ✦", "step2_title" to "श्री सनातनम् में स्वागतम्", "step2_desc" to "सनातन धर्म, भारतीय संस्कृति, आध्यात्मिक ज्ञान, संस्कार और जनसेवा को समर्पित एक पावन पहल। आइए, ज्ञान और सद्भाव की इस यात्रा से जुड़ें।", "step2_badge" to "धर्म • ज्ञान • साधना • सेवा",
        "step3_eyebrow" to "॥ जय माता दी ॥", "step3_title" to "माँ भगवती की कृपा", "step3_desc" to "जगज्जननी माँ जगदम्बा के श्रीचरणों में श्रद्धापूर्वक नमन। माँ की कृपा से सभी के जीवन में शांति, सद्बुद्धि, साहस और सद्भाव बना रहे।", "step3_badge" to "जय भवानी • जय जगदम्बे",
        "step4_eyebrow" to "॥ गुरु कृपा ही केवलम् ॥", "step4_title" to "पूज्य सद्गुरुदेव जी महाराज", "step4_desc" to "गुरु-मार्गदर्शन, आध्यात्मिक ज्ञान, आत्मचिन्तन और सेवा के पथ पर सदैव अग्रसर रहने का संकल्प।", "step4_badge" to "श्रद्धा • साधना • सदाचार",
        "step5_eyebrow" to "॥ सेवा ही परमो धर्मः ॥", "step5_title" to "सनातन सेवा समिति में स्वागतम्", "step5_desc" to "धर्म, संस्कृति, संस्कार और समाजसेवा के पावन उद्देश्य से जुड़ें। आइए, सेवा और सद्भाव के इस अभियान में अपनी सहभागिता सुनिश्चित करें।",
        "step5_btn_contact" to "✦ सदस्यता एवं पंजीकरण", "step5_btn_register" to "✦ नया खाता बनाएँ", "step5_btn_login" to "→ सदस्य लॉगिन करें"
    ),
    "en" to mapOf(
        "brand_name" to "Shri Sanatanam", "brand_sub" to "Sanatan Seva Samiti", "header_tag" to "|| INITIATION ||", "skip_btn" to "Skip ↗",
        "btn_next" to "Next →", "btn_start" to "Get Started →",
        "step1_title" to "Choose Your Language", "step1_desc" to "Welcome to Shri Sanatanam. Please choose your preferred language for onboarding.",
        "step1_note" to "All content will be displayed in your chosen language.", "step1_eyebrow" to "|| AUSPICIOUS BEGINNING ||",
        "step1_subheading" to "Devotion • Values • Service", "step1_subdesc" to "Your journey towards Sanatan culture, spiritual wisdom, and selfless service begins here.",
        "step2_eyebrow" to "✦ Jai Shri Sanatanam ✦", "step2_title" to "Welcome to Shri Sanatanam", "step2_desc" to "A noble initiative dedicated to eternal dharma, Indian heritage, spiritual wisdom, and community welfare.", "step2_badge" to "Dharma • Wisdom • Sadhana • Service",
        "step3_eyebrow" to "|| Jai Mata Di ||", "step3_title" to "Grace of Maa Bhagwati", "step3_desc" to "Humble salutations at the lotus feet of the Divine Mother. May Her grace bestow peace, wisdom, and harmony.", "step3_badge" to "Jai Bhavani • Jai Jagadambe",
        "step4_eyebrow" to "|| Guru Kripa Hi Kevalam ||", "step4_title" to "Pujya Sadgurudev Ji Maharaj", "step4_desc" to "Pledging to walk steadily upon the pathway of spiritual guidance, contemplation, and righteous service.", "step4_badge" to "Faith • Devotion • Virtue",
        "step5_eyebrow" to "|| Service is Supreme Duty ||", "step5_title" to "Welcome to Sanatan Seva Samiti", "step5_desc" to "Unite with the sacred mission of dharma, cultural values, and service to society.",
        "step5_btn_contact" to "✦ Membership & Registration", "step5_btn_register" to "✦ Create New Account", "step5_btn_login" to "→ Member Sign In"
    )
    // Baaki bhashaon ke fallback ke liye 'hi' aur 'en' default use honge.
)

@Composable
fun WelcomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 5
    var currentLang by remember { mutableStateOf("hi") }
    var showLoginDialog by remember { mutableStateOf(false) }

    val activeDict = AppTranslations[currentLang] ?: AppTranslations["hi"]!
    val activeLangMeta = LanguageMaster.find { it.code == currentLang } ?: LanguageMaster[0]

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ==========================================
            // Top Brand Header (Matches Web .top-brand)
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorMaroonDeep.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
                    .border(1.dp, ColorGold.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
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
                            .size(50.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(ColorCream)
                            .border(1.dp, ColorGold, RoundedCornerShape(14.dp))
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column {
                        Text(
                            text = activeDict["brand_name"] ?: "श्री सनातनम्",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = ColorGoldLight,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = activeDict["brand_sub"] ?: "सनातन सेवा समिति",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ColorMuted,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = activeDict["header_tag"] ?: "॥ शुभारम्भ ॥",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ColorGoldLight,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    OutlinedButton(
                        onClick = { viewModel.setAppStage(AppStage.MAIN_APP) },
                        shape = RoundedCornerShape(30.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight)
                    ) {
                        Text(text = activeDict["skip_btn"] ?: "छोड़ें ↗", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==========================================
            // Progress Bar Area (Matches Web Progress)
            // ==========================================
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val stepNumText = activeLangMeta.nums.getOrNull(currentStep - 1) ?: "$currentStep"
                    val totalNumText = activeLangMeta.nums.getOrNull(totalSteps - 1) ?: "$totalSteps"
                    Text(
                        text = "चरण $stepNumText / $totalNumText",
                        style = MaterialTheme.typography.labelMedium.copy(color = ColorGoldLight, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "${(currentStep * 100) / totalSteps}%",
                        style = MaterialTheme.typography.labelMedium.copy(color = ColorGoldLight, fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { currentStep.toFloat() / totalSteps.toFloat() },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(20.dp)),
                    color = ColorGold,
                    trackColor = ColorWhite.copy(alpha = 0.1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==========================================
            // Main Step Content Card
            // ==========================================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = ColorMaroonDeep.copy(alpha = 0.96f)),
                border = androidx.compose.foundation.BorderStroke(1.5px, ColorGold.copy(alpha = 0.35f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (currentStep) {
                        1 -> {
                            // ==========================================
                            // STEP 1: 24 Languages Master Grid Selection
                            // ==========================================
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(modifier = Modifier.width(25.dp).height(1.dp).background(ColorGold))
                                Text(
                                    text = activeDict["step1_eyebrow"] ?: "॥ शुभारम्भ ॥",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = ColorGoldLight,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                                Box(modifier = Modifier.width(25.dp).height(1.dp).background(ColorGold))
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = activeDict["step1_title"] ?: "अपनी भाषा चुनें",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = ColorGoldLight,
                                    textAlign = TextAlign.Center,
                                    fontSize = 26.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = activeDict["step1_desc"] ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ColorMuted,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 20.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // 24 Languages Scrollable Grid
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 240.dp)
                                    .background(ColorMaroonDark.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                                    .border(1.dp, ColorGold.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                                    .verticalScroll(rememberScrollState())
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                LanguageMaster.chunked(3).forEach { rowItems ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        rowItems.forEach { lang ->
                                            val isSelected = currentLang == lang.code
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(if (isSelected) ColorMaroonLight else ColorMaroon.copy(alpha = 0.4f))
                                                    .border(1.dp, if (isSelected) ColorGoldLight else ColorGold.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
                                                    .clickable { currentLang = lang.code }
                                                    .padding(vertical = 10.dp, horizontal = 4.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = lang.native,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        color = if (isSelected) ColorGoldPale else ColorCream,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp
                                                    ),
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Selected Language Display Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(linearGradientBrush(ColorMaroonLight, ColorMaroonDeep))
                                    .border(1.dp, ColorGold, RoundedCornerShape(12.dp))
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✓ ${activeLangMeta.native} (${activeLangMeta.name})",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ColorGoldPale,
                                        fontWeight = FontWeight.Black
                                    )
                                )
                            }
                        }
                        2 -> StepCardContent(
                            eyebrow = activeDict["step2_eyebrow"] ?: "",
                            title = activeDict["step2_title"] ?: "",
                            desc = activeDict["step2_desc"] ?: "",
                            badge = activeDict["step2_badge"] ?: "",
                            imageRes = R.drawable.logo
                        )
                        3 -> StepCardContent(
                            eyebrow = activeDict["step3_eyebrow"] ?: "",
                            title = activeDict["step3_title"] ?: "",
                            desc = activeDict["step3_desc"] ?: "",
                            badge = activeDict["step3_badge"] ?: "",
                            imageRes = R.drawable.devi
                        )
                        4 -> StepCardContent(
                            eyebrow = activeDict["step4_eyebrow"] ?: "",
                            title = activeDict["step4_title"] ?: "",
                            desc = activeDict["step4_desc"] ?: "",
                            badge = activeDict["step4_badge"] ?: "",
                            imageRes = R.drawable.guru
                        )
                        5 -> {
                            // ==========================================
                            // STEP 5: Membership & Action Buttons
                            // ==========================================
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(modifier = Modifier.width(25.dp).height(1.dp).background(ColorGold))
                                    Text(
                                        text = activeDict["step5_eyebrow"] ?: "",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = ColorGoldLight,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                    Box(modifier = Modifier.width(25.dp).height(1.dp).background(ColorGold))
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = activeDict["step5_title"] ?: "",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = ColorGoldLight,
                                        textAlign = TextAlign.Center,
                                        fontSize = 24.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = activeDict["step5_desc"] ?: "",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ColorMuted,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 22.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Box(
                                    modifier = Modifier
                                        .size(180.dp)
                                        .clip(RoundedCornerShape(24.dp))
                                        .background(linearGradientBrush(ColorGoldLight, ColorGold, ColorMaroonLight))
                                        .padding(3.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.sss),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(22.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.setAppStage(AppStage.REGISTER) },
                                        modifier = Modifier.fillMaxWidth().height(52.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = ColorMaroonLight),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.6f)),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Text(activeDict["step5_btn_contact"] ?: "✦ सदस्यता एवं पंजीकरण", color = ColorGoldLight, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    }
                                    Button(
                                        onClick = { viewModel.setAppStage(AppStage.MAIN_APP) },
                                        modifier = Modifier.fillMaxWidth().height(52.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = ColorGold),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Text(activeDict["step5_btn_register"] ?: "✦ नया खाता बनाएँ", color = ColorMaroonDark, fontWeight = FontWeight.Black, fontSize = 14.sp)
                                    }
                                    OutlinedButton(
                                        onClick = { showLoginDialog = true },
                                        modifier = Modifier.fillMaxWidth().height(52.dp),
                                        shape = RoundedCornerShape(14.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.45f)),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight)
                                    ) {
                                        Text(activeDict["step5_btn_login"] ?: "→ सदस्य लॉगिन करें", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==========================================
            // Bottom Navigation Footer
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorMaroonDeep.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .border(1.dp, ColorGold.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SANATANAM · भक्ति • संस्कार • सेवा",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ColorWhite.copy(alpha = 0.6f),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (currentStep > 1) {
                        OutlinedButton(
                            onClick = { currentStep -= 1 },
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.4f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoldLight)
                        ) {
                            Text("‹", fontSize = 22.sp, fontWeight = FontWeight.Bold)
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
                        modifier = Modifier.height(48.dp).defaultMinSize(minWidth = 150.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorGold),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorGoldLight)
                    ) {
                        Text(
                            text = if (currentStep == totalSteps) (activeDict["btn_start"] ?: "आरम्भ करें →") else (activeDict["btn_next"] ?: "आगे बढ़ें →"),
                            color = ColorMaroonDark,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Login Dialog Popup
        if (showLoginDialog) {
            AlertDialog(
                onDismissRequest = { showLoginDialog = false },
                containerColor = ColorMaroonDeep,
                title = { Text("सदस्य लॉगिन", color = ColorGoldLight, fontWeight = FontWeight.Bold) },
                text = { Text("कृपया अपने पंजीकृत मोबाइल नंबर या ईमेल से लॉगिन करें।", color = ColorMuted) },
                confirmButton = {
                    Button(
                        onClick = {
                            showLoginDialog = false
                            viewModel.setAppStage(AppStage.LOGIN)
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
fun StepCardContent(eyebrow: String, title: String, desc: String, badge: String, imageRes: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.width(25.dp).height(1.dp).background(ColorGold))
            Text(
                text = eyebrow,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ColorGoldLight,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Box(modifier = Modifier.width(25.dp).height(1.dp).background(ColorGold))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                color = ColorGoldLight,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = ColorMuted,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(linearGradientBrush(ColorGoldLight, ColorGold, ColorMaroonLight))
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(22.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Surface(
            color = ColorGold.copy(alpha = 0.08f),
            shape = RoundedCornerShape(30.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold.copy(alpha = 0.4f))
        ) {
            Text(
                text = badge,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ColorGoldLight,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun linearGradientBrush(vararg colors: Color): Brush {
    return Brush.linearGradient(colors = colors.asList())
}
