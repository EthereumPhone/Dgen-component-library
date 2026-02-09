package com.example.dgenlibrary.ui.backgrounds

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.dgenlibrary.ui.headers.HeaderBar
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.pulseOpacity
import com.example.dgenlibrary.R

@Composable
fun DgenHeaderBackground(
    modifier: Modifier = Modifier,
    title: String = "",
    primaryColor: Color = dgenOcean,
    focusManager: FocusManager? = null,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(dgenBlack)
            .statusBarsPadding()
            .padding(start = 12.dp, end = 12.dp, bottom = 24.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager?.clearFocus()
                    }
                )
            },
    ) {
        HeaderBar(
            text = title,
            onClick = onBackClick,
            modifier = modifier.padding(horizontal = 12.dp),
            primaryColor = primaryColor
        )

        content()
    }
}

@Composable
fun DgenHeaderGlobeBackground(
    modifier: Modifier = Modifier,
    title: String = "",
    primaryColor: Color = dgenOcean,
    focusManager: FocusManager? = null,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
){
    val context = LocalContext.current
    val loader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()



    LargeGlobeBackground(
        primaryColor = primaryColor,
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(dgenBlack)
                .statusBarsPadding()
                .padding(start = 12.dp, end = 12.dp, bottom = 24.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager?.clearFocus()
                        }
                    )
                },
        ) {
            HeaderBar(
                text = title,
                onClick = onBackClick,
                modifier = modifier.padding(horizontal = 12.dp),
                primaryColor = primaryColor
            )

            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DgenHeaderBackgroundPreview() {
    DgenHeaderBackground(
        title = "Header",
        onBackClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Content goes here")
            Text(text = "More content below the header")
        }
    }
}