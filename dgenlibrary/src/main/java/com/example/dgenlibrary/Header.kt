package com.example.dgenlibrary

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.neonOpacity

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    minHeight: Dp,
    maxHeight: Dp,
    progress: Float,
    progressDp: Dp,
    onNavigateBack: () -> Unit,
    appUrl: String,
    packageId: String,
    category: String,
    name: String,
    imageUrl: String,
    description: String,
    isInstalled: Boolean,
    isInstalling: Boolean = false,
    primaryColor: Color,
    secondaryColor: Color,
    onInstallClicked: () -> Unit
) {
    val animatedHeight by animateDpAsState(
        targetValue = if (progress <= COLLAPSE_START) minHeight else progressDp,
        animationSpec = tween(durationMillis = 200)
    )
    val context = LocalContext.current

    // Ensure the onClick lambda always has the current value.
    val currentIsInstalled by rememberUpdatedState(isInstalled)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedHeight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 16.dp,
                    bottom = 0.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
            ) {
                BackButton(
                    onClick = onNavigateBack,
                    color = primaryColor
                )
            }

            when (imageUrl.isNotEmpty()) {
                true -> {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(48.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .border(1.dp, primaryColor, CircleShape)
                    )
                }
                false -> {
                    // TODO: change the placeholder image
                    Image(
                        painter = painterResource(R.drawable.dgen_image_placeholder),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(48.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .border(1.dp, dgenWhite, CircleShape)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f)
            ) {
                DgenInstallButton(
                    text = if (isInstalled) { "Open" } else { "Install" },
                    backgroundColor = primaryColor,
                    containerColor = secondaryColor,
                    isOpen = isInstalled,
                    isInstalling = isInstalling,
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    onClick = {
                        if (currentIsInstalled) {
                            if (!packageId.startsWith("app.")) {
                                try {
                                    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageId)
                                    if (launchIntent != null) {
                                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        context.startActivity(launchIntent)
                                        Log.d(TAG, "Launched app: $packageId")
                                        return@DgenInstallButton
                                    }
                                } catch (e: Exception) {
                                    Log.e(TAG, "Failed to launch app: $packageId", e)
                                }
                            } else {
                                try {
                                    val uri = Uri.parse(appUrl)
                                    val intent = Intent().apply {
                                        setClassName(WEB_APP_PACKAGE, "org.ethosmobile.webpwaemul.MainActivity")
                                        data = uri
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)
                                    Log.d(TAG, "Opening URL in WebPWA emulator: $appUrl")
                                } catch (e: Exception) {
                                    Log.e(TAG, "Failed to open fake app URL: $appUrl", e)
                                }
                            }
                        } else {
                            onInstallClicked()
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 0.dp,
                    bottom = 0.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = name,
                textAlign = TextAlign.Center,
                style = DgenTheme.typography.body2,
                color = primaryColor,
                modifier = modifier.width(350.dp)
            )
            Text(
                modifier = modifier.width(200.dp),
                text = description,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor.copy(neonOpacity),
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center
                ),
                color = primaryColor.copy(neonOpacity)
            )
            Surface(
                color = dgenRed,
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category.uppercase(),
                        style = TextStyle(
                            fontFamily = SpaceMono,
                            color = dgenWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            lineHeight = 13.sp,
                            letterSpacing = 0.5.sp,
                            textDecoration = TextDecoration.None
                        ),
                        color = dgenWhite
                    )
                }
            }
        }
    }
}

private const val WEB_APP_PACKAGE = "org.ethosmobile.webpwaemul"
private const val TAG = "FakeAppManager"
