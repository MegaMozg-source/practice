package ci.nsu.moble.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ci.nsu.moble.main.ui.theme.PracticeTheme
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.sp

// TODO: crate sealed class with 3 routes
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ScreenOne : Screen("screen_one")
    object ScreenTwo : Screen("screen_two")
}
class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                SecondActivityScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondActivityScreen() {

    val navController = rememberNavController() // 🔹 создаём контроллер
    val context = LocalContext.current

    var receivedText by remember { mutableStateOf("") }

    if (context is Activity) {
        receivedText = context.intent.getStringExtra("text_data") ?: "No text received"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        // 🔹 TOP BAR
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = receivedText,
                        maxLines = Int.MAX_VALUE, // разрешаем много строк
                        softWrap = true
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (context is Activity) {
                            context.finish() // возврат в MainActivity
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },

        // 🔹 BOTTOM NAVIGATION
        bottomBar = {

            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            NavigationBar {

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, null) },
                    label = { Text("Home") },
                    selected = currentRoute == Screen.Home.route,
                    onClick = {
                        navController.navigate(Screen.Home.route)
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, null) },
                    label = { Text("Screen One") },
                    selected = currentRoute == Screen.ScreenOne.route,
                    onClick = {
                        navController.navigate(Screen.ScreenOne.route)
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, null) },
                    label = { Text("Screen Two") },
                    selected = currentRoute == Screen.ScreenTwo.route,
                    onClick = {
                        navController.navigate(Screen.ScreenTwo.route)
                    }
                )
            }
        }

    ) { innerPadding ->

        // 🔹 NAV HOST
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.ScreenOne.route) {
                ScreenOne()
            }

            composable(Screen.ScreenTwo.route) {
                ScreenTwo()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PracticeTheme {
        SecondActivityScreen()
    }
}

@Composable
fun HomeScreen() {
    Text(text = "This is Home Screen",
    fontSize = 24.sp)

}

@Composable
fun ScreenOne() {
    Text(text = "This is Screen One", fontSize = 24.sp)
}

@Composable
fun ScreenTwo() {
    Text(text = "This is Screen Two", fontSize = 24.sp)
}