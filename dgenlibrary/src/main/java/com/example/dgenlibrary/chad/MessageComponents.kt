package com.example.dgenlibrary.chad

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// ==================== PREVIEWS ====================

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
/**
 * Role of a message in a chat conversation
 */
enum class MessageRole {
    User,
    Assistant,
    System,
    Tool
}

/**
 * Data class representing a chat message
 */
data class ChatMessage(
    val id: String,
    val role: MessageRole,
    val content: String,
    val toolCallsJson: String? = null
)

/**
 * A chat message item component styled for terminal/retro aesthetic.
 * Handles both user and assistant messages with appropriate styling.
 *
 * @param message The message data to display
 * @param modifier Modifier to apply
 * @param userTextColor Color for user messages
 * @param assistantTextColor Color for assistant messages (primary by default)
 * @param glowIntensity Intensity of the glow effect (0.0 - 1.0)
 * @param glowRadius Blur radius for the glow
 * @param fontSize Font size for message text
 * @param fontFamily Font family (defaults to Monospace)
 * @param phosphorGlow Animation value for phosphor glow effect (0.0 - 1.0)
 * @param userPrompt Prompt prefix for user messages
 * @param isToolResult Whether this message represents a tool result
 * @param isExpanded Whether tool result details are expanded
 * @param onToggleExpand Callback when tool result expand/collapse is toggled
 * @param parseMarkdown Function to parse markdown in assistant messages
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatMessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier,
    userTextColor: Color = MaterialTheme.colorScheme.onBackground,
    assistantTextColor: Color = MaterialTheme.colorScheme.primary,
    glowIntensity: Float = 0.6f,
    glowRadius: Float = 16f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    phosphorGlow: Float = 1f,
    userPrompt: String = "> ",
    isToolResult: Boolean = false,
    isExpanded: Boolean = false,
    onToggleExpand: () -> Unit = {},
    parseMarkdown: ((String, Color) -> AnnotatedString)? = null
) {
    // Skip messages that are internal (assistant tool_calls with empty content or empty tool responses)
    val shouldSkip = (message.role == MessageRole.Assistant && message.toolCallsJson != null && message.content.isBlank()) ||
            (message.role == MessageRole.Tool && message.content.isBlank())
    if (shouldSkip) return
    
    Column(modifier = modifier.fillMaxWidth()) {
        when (message.role) {
            MessageRole.User -> {
                UserMessageItem(
                    content = message.content,
                    textColor = userTextColor,
                    glowIntensity = glowIntensity * phosphorGlow,
                    glowRadius = glowRadius,
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    prompt = userPrompt
                )
            }
            MessageRole.Assistant, MessageRole.System -> {
                // Check if this is a tool result message
                val content = message.content
                val isToolResultMessage = content.startsWith("[") && content.contains("] ")
                
                if (isToolResultMessage || isToolResult) {
                    ToolResultItem(
                        content = content,
                        primaryColor = assistantTextColor,
                        fontSize = fontSize,
                        fontFamily = fontFamily,
                        isExpanded = isExpanded,
                        onToggle = onToggleExpand
                    )
                } else {
                    AssistantMessageItem(
                        content = content,
                        primaryColor = assistantTextColor,
                        glowIntensity = glowIntensity * phosphorGlow,
                        glowRadius = glowRadius,
                        fontSize = fontSize,
                        fontFamily = fontFamily,
                        parseMarkdown = parseMarkdown
                    )
                }
            }
            MessageRole.Tool -> {
                ToolResultItem(
                    content = message.content,
                    primaryColor = assistantTextColor,
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    isExpanded = isExpanded,
                    onToggle = onToggleExpand
                )
            }
        }
    }
}

/**
 * User message display with terminal prompt styling
 */
@Composable
fun UserMessageItem(
    content: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    glowIntensity: Float = 0.6f,
    glowRadius: Float = 16f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    prompt: String = "> "
) {
    SelectionContainer {
        Text(
            text = "$prompt$content",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
                shadow = Shadow(
                    color = textColor.copy(alpha = glowIntensity),
                    offset = Offset(0f, 0f),
                    blurRadius = glowRadius
                )
            ),
            color = textColor,
            modifier = modifier.padding(vertical = 2.dp)
        )
    }
}

/**
 * Assistant message display with glow effect and optional markdown support
 */
@Composable
fun AssistantMessageItem(
    content: String,
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    glowIntensity: Float = 0.7f,
    glowRadius: Float = 20f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    parseMarkdown: ((String, Color) -> AnnotatedString)? = null
) {
    val displayText = parseMarkdown?.invoke(content, primaryColor) 
        ?: AnnotatedString(content)
    
    SelectionContainer {
        Text(
            text = displayText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
                shadow = Shadow(
                    color = primaryColor.copy(alpha = glowIntensity),
                    offset = Offset(0f, 0f),
                    blurRadius = glowRadius
                )
            ),
            color = primaryColor,
            modifier = modifier.padding(vertical = 2.dp, horizontal = 0.dp)
        )
    }
}

/**
 * Tool result message with collapsible details
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolResultItem(
    content: String,
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    isExpanded: Boolean = false,
    onToggle: () -> Unit = {}
) {
    // Parse tool name and result
    val endBracket = content.indexOf("] ")
    if (endBracket == -1) {
        // Not a valid tool result format, just display as text
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = fontFamily,
                fontSize = fontSize
            ),
            color = primaryColor.copy(alpha = 0.7f),
            modifier = modifier.padding(vertical = 2.dp)
        )
        return
    }
    
    val bracketContent = content.substring(1, endBracket)
    val toolResult = content.substring(endBracket + 2)
    
    // Parse tool name and parameters
    val (toolName, parameters) = if (bracketContent.contains("|")) {
        val parts = bracketContent.split("|", limit = 2)
        parts[0] to parts.getOrNull(1)
    } else {
        bracketContent to null
    }
    
    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = primaryColor.copy(alpha = 0.7f),
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("[$toolName]")
        }
        
        if (isExpanded) {
            if (parameters != null && parameters.isNotEmpty() && parameters != "{}") {
                withStyle(SpanStyle(color = primaryColor.copy(alpha = 0.6f))) {
                    append("\n  Parameters: $parameters")
                }
            }
            
            withStyle(SpanStyle(color = primaryColor.copy(alpha = 0.6f))) {
                append("\n  Result: $toolResult")
            }
        }
    }
    
    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = fontFamily,
            fontSize = fontSize
        ),
        modifier = modifier
            .padding(vertical = 2.dp)
            .combinedClickable(
                onClick = onToggle
            )
    )
}

/**
 * Streaming message component for displaying in-progress responses
 * with animated cursor indicating ongoing generation.
 *
 * @param message The current streaming message content
 * @param modifier Modifier to apply
 * @param primaryColor Color for the text
 * @param glowIntensity Intensity of the glow effect
 * @param glowRadius Blur radius for the glow
 * @param fontSize Font size for message text
 * @param fontFamily Font family (defaults to Monospace)
 * @param phosphorGlow Animation value for phosphor glow effect
 * @param processingText Text to show when message is empty
 * @param showCursor Whether to show the streaming cursor
 * @param parseMarkdown Function to parse markdown content
 */
@Composable
fun StreamingMessage(
    message: String,
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    glowIntensity: Float = 0.7f,
    glowRadius: Float = 20f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    phosphorGlow: Float = 1f,
    processingText: String = "Processing...",
    showCursor: Boolean = true,
    parseMarkdown: ((String, Color) -> AnnotatedString)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (message.isNotEmpty()) {
            val displayContent = if (showCursor) "$message█" else message
            val displayText = parseMarkdown?.invoke(displayContent, primaryColor)
                ?: AnnotatedString(displayContent)
            
            SelectionContainer {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                        shadow = Shadow(
                            color = primaryColor.copy(alpha = glowIntensity * phosphorGlow),
                            offset = Offset(0f, 0f),
                            blurRadius = glowRadius
                        )
                    ),
                    color = primaryColor,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        } else {
            Text(
                text = processingText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = fontFamily,
                    fontSize = fontSize,
                    shadow = Shadow(
                        color = primaryColor.copy(alpha = glowIntensity * phosphorGlow),
                        offset = Offset(0f, 0f),
                        blurRadius = glowRadius
                    )
                ),
                color = primaryColor,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

/**
 * Object containing default values for message components
 */
object MessageComponentDefaults {
    val fontSize = 17.sp
    val glowIntensity = 0.6f
    val glowRadius = 16f
    val assistantGlowIntensity = 0.7f
    val assistantGlowRadius = 20f
    val userPrompt = "> "
    val processingText = "Processing..."
}

@Preview(
    name = "User Message Item",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 80
)
@Composable
private fun UserMessageItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        UserMessageItem(
            content = "What is the current ETH price?",
            textColor = Color(0xFFCCCCCC)
        )
    }
}

@Preview(
    name = "Assistant Message Item",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 120
)
@Composable
private fun AssistantMessageItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        AssistantMessageItem(
            content = "The current ETH price is \$3,456.78 USD. The price has increased by 2.5% in the last 24 hours.",
            primaryColor = Color(0xFF00FF00)
        )
    }
}

@Preview(
    name = "Tool Result Item - Collapsed",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 80
)
@Composable
private fun ToolResultItemPreviewCollapsed() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        ToolResultItem(
            content = "[getEthPrice|{\"currency\":\"USD\"}] 3456.78",
            primaryColor = Color(0xFF00FF00),
            isExpanded = false
        )
    }
}

@Preview(
    name = "Tool Result Item - Expanded",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 150
)
@Composable
private fun ToolResultItemPreviewExpanded() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        ToolResultItem(
            content = "[getEthPrice|{\"currency\":\"USD\"}] 3456.78",
            primaryColor = Color(0xFF00FF00),
            isExpanded = true
        )
    }
}

@Preview(
    name = "Streaming Message - With Content",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 100
)
@Composable
private fun StreamingMessagePreviewWithContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        StreamingMessage(
            message = "I'm currently fetching the latest blockchain data",
            primaryColor = Color(0xFF00FF00)
        )
    }
}

@Preview(
    name = "Streaming Message - Processing",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 80
)
@Composable
private fun StreamingMessagePreviewProcessing() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        StreamingMessage(
            message = "",
            primaryColor = Color(0xFF00FF00),
            processingText = "Thinking..."
        )
    }
}

@Preview(
    name = "Chat Message Item - User",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 80
)
@Composable
private fun ChatMessageItemPreviewUser() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        ChatMessageItem(
            message = ChatMessage(
                id = "1",
                role = MessageRole.User,
                content = "Check my wallet balance"
            ),
            userTextColor = Color(0xFFCCCCCC),
            assistantTextColor = Color(0xFF00FF00)
        )
    }
}

@Preview(
    name = "Chat Message Item - Assistant",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 100
)
@Composable
private fun ChatMessageItemPreviewAssistant() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        ChatMessageItem(
            message = ChatMessage(
                id = "2",
                role = MessageRole.Assistant,
                content = "Your wallet balance is 2.5 ETH (~\$8,642.00 USD)"
            ),
            userTextColor = Color(0xFFCCCCCC),
            assistantTextColor = Color(0xFF00FF00)
        )
    }
}

@Preview(
    name = "Full Chat Conversation",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360,
    heightDp = 500
)
@Composable
private fun FullChatConversationPreview() {
    val messages = listOf(
        ChatMessage("1", MessageRole.User, "What's the gas price right now?"),
        ChatMessage("2", MessageRole.Assistant, "[getGasPrice] 25 gwei"),
        ChatMessage("3", MessageRole.Assistant, "The current gas price is 25 gwei. This is relatively low, making it a good time for transactions."),
        ChatMessage("4", MessageRole.User, "Send 0.1 ETH to vitalik.eth"),
        ChatMessage("5", MessageRole.Assistant, "I'll prepare that transaction for you...")
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            messages.forEach { message ->
                ChatMessageItem(
                    message = message,
                    userTextColor = Color(0xFFCCCCCC),
                    assistantTextColor = Color(0xFF00FF00),
                    isExpanded = false
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Streaming response
            StreamingMessage(
                message = "Preparing transaction: 0.1 ETH to vitalik.eth",
                primaryColor = Color(0xFF00FF00)
            )
        }
    }
}

@Preview(
    name = "Messages - Amber Theme",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360,
    heightDp = 300
)
@Composable
private fun MessagesAmberThemePreview() {
    val amberColor = Color(0xFFFFB000)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UserMessageItem(
                content = "Initialize system diagnostics",
                textColor = Color(0xFFCCCCCC)
            )
            
            AssistantMessageItem(
                content = "Running diagnostics...\n\n• CPU: OK\n• Memory: OK\n• Network: OK\n\nAll systems operational.",
                primaryColor = amberColor
            )
            
            StreamingMessage(
                message = "Generating report",
                primaryColor = amberColor
            )
        }
    }
}

