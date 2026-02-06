package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ci.nsu.moble.main.ui.theme.PracticeTheme
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import android.util.Log
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorSearchScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


data class ColorItem(
    val name: String,
    val color: Color
)

object ColorRepository {
    private val colors = mapOf(
        "red" to Color.Red,
        "blue" to Color.Blue,
        "green" to Color.Green,
        "yellow" to Color.Yellow,
        "cyan" to Color.Cyan,
        "magenta" to Color.Magenta,
        "black" to Color.Black,
        "white" to Color.White,
        "gray" to Color.Gray,
        "lightblue" to Color(0xFFADD8E6),
        "darkgreen" to Color(0xFF006400),
        "orange" to Color(0xFFFFA500),
        "purple" to Color(0xFF800080),
        "pink" to Color(0xFFFFC0CB),
        "brown" to Color(0xFFA52A2A)
    )
    fun getAllColors(): List<ColorItem> {
        return colors.map { ColorItem(it.key, it.value) }
    }

    fun findColorByName(name: String): Color? {
        return colors[name.lowercase()]
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSearchScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Blue) }
    val allColors = remember { ColorRepository.getAllColors() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Поиск цвета",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Введите название цвета") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val colorName = searchText.trim()
                if (colorName.isNotEmpty()) {
                    val foundColor = ColorRepository.findColorByName(colorName)
                    if (foundColor != null) {
                        buttonColor = foundColor
                        Log.i("ColorSearch", "Цвет '$colorName' найден и применен")
                    } else {
                        Log.w("ColorSearch", "Пользовательский цвет '$colorName' не найден")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = if (buttonColor == Color.Yellow || buttonColor == Color.White)
                    Color.Black else Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Найти и применить цвет",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Доступные цвета:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.Start)
        )

        // Отображение палитры цветов
        ColorPalette(colors = allColors)
    }
}

@Composable
fun ColorPalette(colors: List<ColorItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colors) { colorItem ->
            ColorPaletteItem(colorItem = colorItem)
        }
    }
}

@Composable
fun ColorPaletteItem(colorItem: ColorItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = colorItem.color,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Column {
            Text(
                text = colorItem.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = String.format(
                    "#%08X",
                    colorItem.color.value.toLong() and 0xFFFFFFFF
                ),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

    }
}

@Preview
@Composable
fun ColorSearchScreenPreview() {
    PracticeTheme {
        ColorSearchScreen()
    }
}