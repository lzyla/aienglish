package com.lzyla.aienglish.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

data class ConversationMessage(
    val text: String,
    val isUser: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(onBack: () -> Unit) {
    val messages = remember {
        mutableStateListOf(
            ConversationMessage(
                text = "Hi! Tell me how your day is going. I will help you practise natural English.",
                isUser = false,
            ),
        )
    }
    var draft by rememberSaveable { mutableStateOf("") }

    fun sendMessage() {
        val message = draft.trim()
        if (message.isEmpty()) return

        messages += ConversationMessage(text = message, isUser = true)
        draft = ""
        messages += ConversationMessage(
            text = createLocalCoachReply(message),
            isUser = false,
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rozmowa") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Wróć",
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Napisz po angielsku…") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { sendMessage() }),
                    maxLines = 4,
                )
                Spacer(modifier = Modifier.size(8.dp))
                FilledIconButton(
                    onClick = { sendMessage() },
                    enabled = draft.isNotBlank(),
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Wyślij",
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(message: ConversationMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Surface(
            color = if (message.isUser) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

private fun createLocalCoachReply(message: String): String {
    val normalized = message.lowercase()
    return when {
        normalized.contains("hello") || normalized.contains("hi") ->
            "Hello! Nice to meet you. What would you like to talk about today?"

        normalized.contains("because") ->
            "Good explanation. Can you add one concrete example?"

        normalized.endsWith("?") ->
            "That is a good question. First, tell me what you think the answer might be."

        else ->
            "Good. Try expanding that thought with one more detail and a reason."
    }
}
