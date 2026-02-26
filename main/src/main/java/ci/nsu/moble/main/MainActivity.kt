package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Тема (можно создать свою или использовать пустую)
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TemperatureConverterScreen()
                }
            }
        }
    }
}

@Composable
fun TemperatureConverterScreen(
    viewModel: TemperatureViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Поле ввода Цельсия
        OutlinedTextField(
            value = uiState.celsius,
            onValueChange = viewModel::onCelsiusChanged,
            label = { Text("Цельсий") },
            isError = uiState.celsius.isNotBlank() && !uiState.isCelsiusValid,
            supportingText = {
                if (uiState.celsius.isNotBlank() && !uiState.isCelsiusValid) {
                    Text("Введите число")
                }
            },
            singleLine = true
        )

        // Поле ввода Фаренгейта
        OutlinedTextField(
            value = uiState.fahrenheit,
            onValueChange = viewModel::onFahrenheitChanged,
            label = { Text("Фаренгейт") },
            isError = uiState.fahrenheit.isNotBlank() && !uiState.isFahrenheitValid,
            supportingText = {
                if (uiState.fahrenheit.isNotBlank() && !uiState.isFahrenheitValid) {
                    Text("Введите число")
                }
            },
            modifier = Modifier.padding(top = 16.dp),
            singleLine = true
        )
    }
}