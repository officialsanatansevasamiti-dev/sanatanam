package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.viewmodel.AppStage
import com.example.viewmodel.MainViewModel

@Composable
fun AppRegisterScreen(
    viewModel: MainViewModel,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var phoneOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    BackHandler {
        viewModel.setAppStage(AppStage.WELCOME)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(ColorMaroon, ColorRoyal, ColorDeep)))
    ) {
        IconButton(
            onClick = { viewModel.setAppStage(AppStage.WELCOME) },
            modifier = Modifier
                .statusBarsPadding()
                .padding(12.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = ColorGold2
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                    painter = painterResource(id = R.drawable.sss),
                    contentDescription = "SSS Logo",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "भक्त पंजीकरण (Register)",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorGold2
                )
            )
            Text(
                text = "Sanatan Seva Samiti • DG Developers",
                style = MaterialTheme.typography.bodySmall.copy(color = ColorWhite.copy(alpha = 0.7f))
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("पूरा नाम (Full Name)", color = ColorGold3) },
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

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phoneOrEmail,
                onValueChange = { phoneOrEmail = it },
                label = { Text("मोबाइल नंबर / ईमेल", color = ColorGold3) },
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

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("नया पासवर्ड (Password)", color = ColorGold3) },
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
                    if (name.isNotBlank() && phoneOrEmail.isNotBlank()) {
                        viewModel.loginUser(name)
                        Toast.makeText(context, "पंजीकरण सफल रहा, स्वागत है!", Toast.LENGTH_SHORT).show()
                        viewModel.setAppStage(AppStage.MAIN_APP)
                    } else {
                        Toast.makeText(context, "कृपया सभी जानकारी भरें", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorBhagwa),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorGold2)
            ) {
                Text("खाता बनाएं & रजिस्टर करें (Firebase)", fontWeight = FontWeight.Bold, color = ColorWhite)
            }

            Spacer(modifier = Modifier.height(14.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = "पहले से खाता है? लॉगिन करें",
                    color = ColorGold2,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
