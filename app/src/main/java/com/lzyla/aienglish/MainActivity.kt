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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lzyla.aienglish.conversation.ConversationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var destination by rememberSaveable { mutableStateOf(AppDestination.HOME.name) }

            MaterialTheme {
                when (destination) {
                    AppDestination.CONVERSATION.name -> ConversationScreen(
                        onBack = { destination = AppDestination.HOME.name },
                    )

                    else -> HomeScreen(
                        onStartConversation = {
                            destination = AppDestination.CONVERSATION.name
                        },
                    )
                }
            }
        }
    }
}

private enum class AppDestination {
    HOME,
    CONVERSATION,
}

private data class HomeAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val isAvailable: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(onStartConversation: () -> Unit) {
    val actions = listOf(
        HomeAction(
            title = "Rozpocznij rozmowę",
            subtitle = "Ćwicz swobodną rozmowę po angielsku",
            icon = Icons.Default.Chat,
            isAvailable = true,
        ),
        HomeAction(
            title = "Słówka",
            subtitle = "Zapisuj nowe słowa i zwroty",
            icon = Icons.Default.School,
            isAvailable = false,
        ),
        HomeAction(
            title = "Powtórki",
            subtitle = "Wracaj do błędów i trudnych zwrotów",
            icon = Icons.Default.Refresh,
            isAvailable = false,
        ),
        HomeAction(
            title = "Postępy",
            subtitle = "Sprawdzaj czas nauki i regularność",
            icon = Icons.Default.BarChart,
            isAvailable = false,
        ),
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
                Card(
                    onClick = {
                        if (action.isAvailable) onStartConversation()
                    },
                    enabled = action.isAvailable,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(action.icon, contentDescription = null)
                        Text(action.title, style = MaterialTheme.typography.titleLarge)
                        Text(action.subtitle, style = MaterialTheme.typography.bodyMedium)
                        if (!action.isAvailable) {
                            Text(
                                text = "Wkrótce",
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}
