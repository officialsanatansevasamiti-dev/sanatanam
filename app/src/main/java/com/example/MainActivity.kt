package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.ContentReaderSheet
import com.example.ui.components.SacredTopBar
import com.example.ui.screens.AboutSamitiScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.JapaScreen
import com.example.ui.screens.MandirScreen
import com.example.ui.screens.PanchangScreen
import com.example.ui.screens.SangrahScreen
import com.example.ui.theme.BhagwaDark
import com.example.ui.theme.BhagwaPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SwarnaGold
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenDestination

data class NavItem(
    val destination: ScreenDestination,
    val icon: ImageVector,
    val label: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MainViewModel = viewModel()
                ShriSanatanamApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ShriSanatanamApp(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val activeReaderItem by viewModel.activeReaderItem.collectAsState()

    // Handle back button on secondary screens
    if (currentScreen != ScreenDestination.HOME && activeReaderItem == null) {
        BackHandler {
            viewModel.navigateTo(ScreenDestination.HOME)
        }
    }

    val navItems = listOf(
        NavItem(ScreenDestination.HOME, Icons.Default.Home, "दैनिक"),
        NavItem(ScreenDestination.SANGRAH, Icons.Default.MenuBook, "संग्रह"),
        NavItem(ScreenDestination.MANDIR, Icons.Default.AutoAwesome, "पूजा घर"),
        NavItem(ScreenDestination.JAPA, Icons.Default.SelfImprovement, "जाप माला"),
        NavItem(ScreenDestination.PANCHANG, Icons.Default.CalendarMonth, "पंचांग")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SacredTopBar(viewModel = viewModel)
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    navItems.forEach { item ->
                        val selected = currentScreen == item.destination
                        NavigationBarItem(
                            selected = selected,
                            onClick = { viewModel.navigateTo(item.destination) },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = BhagwaDark,
                                indicatorColor = BhagwaPrimary,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            ),
                            modifier = Modifier.testTag("nav_item_${item.destination.name}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "screen_transition"
                ) { target ->
                    when (target) {
                        ScreenDestination.HOME -> HomeScreen(viewModel = viewModel)
                        ScreenDestination.SANGRAH -> SangrahScreen(viewModel = viewModel)
                        ScreenDestination.MANDIR -> MandirScreen(viewModel = viewModel)
                        ScreenDestination.JAPA -> JapaScreen(viewModel = viewModel)
                        ScreenDestination.PANCHANG -> PanchangScreen(viewModel = viewModel)
                        ScreenDestination.ABOUT -> AboutSamitiScreen(viewModel = viewModel)
                    }
                }
            }
        }

        // Overlaid Fullscreen / Reader Sheet
        activeReaderItem?.let { item ->
            ContentReaderSheet(
                item = item,
                viewModel = viewModel
            )
        }
    }
}
