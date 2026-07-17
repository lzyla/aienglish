package com.lzyla.aienglish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HomeScreen()
            }
        }
    }
}

private data class HomeAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen() {
    val actions = listOf(
        HomeAction("Rozpocznij rozmowę", "Ćwicz swobodną rozmowę po angielsku", Icons.Default.Chat),
        HomeAction("Słówka", "Zapisuj nowe słowa i zwroty", Icons.Default.School),
        HomeAction("Powtórki", "Wracaj do błędów i trudnych zwrotów", Icons.Default.Refresh),
        HomeAction("Postępy", "Sprawdzaj czas nauki i regularność", Icons.Default.BarChart),
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("AI English Coach") }) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Dzień dobry", style = MaterialTheme.typography.headlineMedium)
                    Text(
                        "Wybierz krótką sesję i zacznij mówić po angielsku.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            items(actions) { action ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(action.icon, contentDescription = null)
                        Text(action.title, style = MaterialTheme.typography.titleLarge)
                        Text(action.subtitle, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
