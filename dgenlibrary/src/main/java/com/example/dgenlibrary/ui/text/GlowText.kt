package com.example.dgenlibrary.ui.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// ==================== PREVIEWS ====================
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview

/**
 * A Text composable with a glowing effect, inspired by retro terminal displays.
 * 
 * @param text The text content to display
 * @param modifier Modifier to apply to the text
 * @param color The text color (defaults to theme primary)
 * @param glowColor The glow/shadow color (defaults to text color if not specified)
 * @param glowIntensity Intensity of the glow effect (0.0 - 1.0, affects alpha)
 * @param glowRadius The blur radius of the glow effect
 * @param fontSize The font size
 * @param fontWeight The font weight
 * @param fontFamily The font family (defaults to Monospace for terminal look)
 * @param textAlign Text alignment
 * @param maxLines Maximum number of lines
 * @param overflow Text overflow behavior
 * @param style Base text style to merge with
 */
@Composable
fun GlowText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    glowColor: Color = Color.Unspecified,
    glowIntensity: Float = 0.7f,
    glowRadius: Float = 20f,
    fontSize: TextUnit = 17.sp,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily = FontFamily.Monospace,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = TextStyle.Default
) {
    val effectiveGlowColor = if (glowColor == Color.Unspecified) color else glowColor
    
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        style = style.merge(
            TextStyle(
                shadow = Shadow(
                    color = effectiveGlowColor.copy(alpha = glowIntensity),
                    offset = Offset(0f, 0f),
                    blurRadius = glowRadius
                )
            )
        )
    )
}

/**
 * A Text composable with a glowing effect that accepts an AnnotatedString.
 * 
 * @param text The annotated text content to display
 * @param modifier Modifier to apply to the text
 * @param color The text color (defaults to theme primary)
 * @param glowColor The glow/shadow color (defaults to text color if not specified)
 * @param glowIntensity Intensity of the glow effect (0.0 - 1.0, affects alpha)
 * @param glowRadius The blur radius of the glow effect
 * @param fontSize The font size
 * @param fontWeight The font weight
 * @param fontFamily The font family (defaults to Monospace for terminal look)
 * @param textAlign Text alignment
 * @param maxLines Maximum number of lines
 * @param overflow Text overflow behavior
 * @param style Base text style to merge with
 */
@Composable
fun GlowText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    glowColor: Color = Color.Unspecified,
    glowIntensity: Float = 0.7f,
    glowRadius: Float = 20f,
    fontSize: TextUnit = 17.sp,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily = FontFamily.Monospace,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = TextStyle.Default
) {
    val effectiveGlowColor = if (glowColor == Color.Unspecified) color else glowColor
    
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        style = style.merge(
            TextStyle(
                shadow = Shadow(
                    color = effectiveGlowColor.copy(alpha = glowIntensity),
                    offset = Offset(0f, 0f),
                    blurRadius = glowRadius
                )
            )
        )
    )
}

/**
 * Object containing preset glow configurations for common use cases
 */
object GlowTextDefaults {
    /** Subtle glow for body text */
    val subtleGlow = GlowConfig(
        intensity = 0.4f,
        radius = 12f
    )
    
    /** Standard glow for normal terminal text */
    val standardGlow = GlowConfig(
        intensity = 0.6f,
        radius = 16f
    )
    
    /** Strong glow for emphasized text */
    val strongGlow = GlowConfig(
        intensity = 0.8f,
        radius = 24f
    )
    
    /** Intense glow for headers and important elements */
    val intenseGlow = GlowConfig(
        intensity = 1.0f,
        radius = 32f
    )
}

/**
 * Configuration class for glow effects
 */
data class GlowConfig(
    val intensity: Float,
    val radius: Float
)

@Preview(
    name = "GlowText - Green Terminal",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 200
)
@Composable
private fun GlowTextPreviewGreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlowText(
                text = "SYSTEM INITIALIZED",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.intenseGlow.intensity,
                glowRadius = GlowTextDefaults.intenseGlow.radius,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            GlowText(
                text = "> Connecting to network...",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.standardGlow.intensity,
                glowRadius = GlowTextDefaults.standardGlow.radius
            )
            
            GlowText(
                text = "> Status: ONLINE",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.subtleGlow.intensity,
                glowRadius = GlowTextDefaults.subtleGlow.radius
            )
        }
    }
}

@Preview(
    name = "GlowText - Amber Terminal",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 200
)
@Composable
private fun GlowTextPreviewAmber() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlowText(
                text = "WARNING: LOW BATTERY",
                color = Color(0xFFFFB000),
                glowIntensity = GlowTextDefaults.strongGlow.intensity,
                glowRadius = GlowTextDefaults.strongGlow.radius,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            GlowText(
                text = "Estimated time remaining: 15 minutes",
                color = Color(0xFFFFB000),
                glowIntensity = GlowTextDefaults.standardGlow.intensity,
                glowRadius = GlowTextDefaults.standardGlow.radius,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(
    name = "GlowText - Cyan Neon",
    showBackground = true,
    backgroundColor = 0xFF0A0A0A,
    widthDp = 320,
    heightDp = 150
)
@Composable
private fun GlowTextPreviewCyan() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlowText(
                text = "NEON GLOW",
                color = Color(0xFF00FFFF),
                glowIntensity = 1.0f,
                glowRadius = 40f,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            GlowText(
                text = "Retro Terminal Style",
                color = Color(0xFFFF00FF),
                glowIntensity = 0.8f,
                glowRadius = 24f,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    name = "GlowText - All Presets",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 300
)
@Composable
private fun GlowTextPreviewAllPresets() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlowText(
                text = "Subtle Glow",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.subtleGlow.intensity,
                glowRadius = GlowTextDefaults.subtleGlow.radius
            )
            
            GlowText(
                text = "Standard Glow",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.standardGlow.intensity,
                glowRadius = GlowTextDefaults.standardGlow.radius
            )
            
            GlowText(
                text = "Strong Glow",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.strongGlow.intensity,
                glowRadius = GlowTextDefaults.strongGlow.radius
            )
            
            GlowText(
                text = "Intense Glow",
                color = Color(0xFF00FF00),
                glowIntensity = GlowTextDefaults.intenseGlow.intensity,
                glowRadius = GlowTextDefaults.intenseGlow.radius
            )
        }
    }
}

