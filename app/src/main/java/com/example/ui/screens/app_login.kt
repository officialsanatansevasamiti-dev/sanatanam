package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

@Composable
fun AppLoginScreen(
    viewModel: MainViewModel,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(ColorMaroon, ColorRoyal, ColorDeep)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo Banner
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(ColorWhite)
                    .border(2.dp, ColorGold2, CircleShape)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "भक्त लॉगिन (Login)",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorGold2
                )
            )
            Text(
                text = "SHRI SANATANAM - Sanatan Seva Samiti",
                style = MaterialTheme.typography.bodySmall.copy(color = ColorWhite.copy(alpha = 0.7f))
            )

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                label = { Text("मोबाइल नंबर या ईमेल (Firebase Auth)", color = ColorGold3) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ColorGold2,
                    unfocusedBorderColor = ColorGold.copy(alpha = 0.5f),
                    focusedTextColor = ColorWhite,
                    unfocusedTextColor = ColorWhite
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("पासवर्ड (Password)", color = ColorGold3) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ColorGold2,
                    unfocusedBorderColor = ColorGold.copy(alpha = 0.5f),
                    focusedTextColor = ColorWhite,
                    unfocusedTextColor = ColorWhite
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (emailOrPhone.isNotBlank()) {
                        viewModel.loginUser(emailOrPhone)
                        Toast.makeText(context, "सफलतापूर्वक लॉगिन हो गए!", Toast.LENGTH_SHORT).show()
                        viewModel.setAppStage(AppStage.MAIN_APP)
                    } else {
                        Toast.makeText(context, "कृपया विवरण दर्ज करें", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorBhagwa),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold2)
            ) {
                Text("لogin करें (Firebase Connected)", fontWeight = FontWeight.Bold, color = ColorWhite)
            }

            Spacer(modifier = Modifier.height(14.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "नया खाता बनाएं? रजिस्टर करें (Register)",
                    color = ColorGold2,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
