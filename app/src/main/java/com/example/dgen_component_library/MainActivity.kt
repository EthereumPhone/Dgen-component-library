package com.example.dgen_component_library

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.PrimaryButton
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.dgenRed
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.dgenlibrary.DgenBasicTextfield
import com.example.dgenlibrary.ui.theme.Exo2Family
import com.example.dgenlibrary.ui.theme.MonomaniacOneFamily
import com.example.dgenlibrary.ui.theme.body1_fontSize
import com.example.dgenlibrary.ui.theme.dgenGray
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.label_fontSize
import kotlin.math.abs
import kotlin.math.min


class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val scrollState = rememberScrollState()
            var scrollVelocity by remember { mutableStateOf(0f) }
            var searchValue by remember { mutableStateOf(TextFieldValue("")) }
//            val filteredItems = emptyList().filter { pair ->
//                pair.first.name.contains(searchValue.text, ignoreCase = true)
//            }

            val focusManager = LocalFocusManager.current
            var isAnyFieldFocused = remember { mutableStateOf(false) }
            var focusedSearch by remember { mutableStateOf(false) }
            val backgroundColor by animateColorAsState(
                targetValue = if (focusedSearch) dgenOcean else Color.Transparent,
                animationSpec = tween(durationMillis = 500),
                label = "backgroundColor" // Dauer der Animation in Millisekunden
            )

            // Calculate scroll velocity using nestedScroll as shown earlier
            val nestedScrollConnection = remember {
                object : androidx.compose.ui.input.nestedscroll.NestedScrollConnection {
                    var lastScrollTimeMillis = System.currentTimeMillis()

                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        val currentTimeMillis = System.currentTimeMillis()
                        val deltaY = available.y.toFloat()

                        if (currentTimeMillis > lastScrollTimeMillis) {
                            val timeElapsed = currentTimeMillis - lastScrollTimeMillis
                            scrollVelocity = abs(deltaY / timeElapsed)
                            lastScrollTimeMillis = currentTimeMillis

                        }

                        return super.onPreScroll(available, source)
                    }
                }
            }

            val context = LocalContext.current
            val dynamicHeight by derivedStateOf {
                lerp(86.dp, 56.dp, min(1f, scrollState.value / 300f))
            }

            DgenTheme {
                Column (modifier = Modifier.fillMaxSize().background(Color.Black)){
                    AppDetailHeader(
                        painter = painterResource(R.drawable.coinbase2),
                        category = "MARKETPLACE",
                        appName = "Coinbase",
                        appDesc = "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    )
                }

//                    Box ( modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp).background(Color.Red)){
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.zIndex(3f)
//
//                                .drawBehind {
//                                    drawRoundRect(
//                                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
//                                        color = backgroundColor,
//                                        alpha = 0.35f
//                                    )
//                                }
//                                .padding(horizontal = 16.dp, vertical = 8.dp)
//
//                            ,
//                            horizontalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.searchicon),
//                                "Search",
//                                tint = DgenTheme.colors.dgenGray,
//                                modifier = Modifier.size(22.dp)
//                            )
//                            DgenBasicTextfield(
//                                value = searchValue,
//                                onValueChange = { new -> searchValue = new},
//                                minLines = 1,
//                                maxLines = 1,
//                                cursorWidth = 14.dp,
//                                cursorHeight = 24.dp,
//                                textStyle = TextStyle(
//                                    textAlign = TextAlign.Start,
//                                    fontFamily = Exo2Family,
//                                    color = dgenWhite,
//                                    fontWeight = FontWeight.SemiBold,
//                                    fontSize = body1_fontSize,
//                                    lineHeight = body1_fontSize,
//                                    letterSpacing = 0.sp,
//                                    textDecoration = TextDecoration.None,
//                                ),
//                                modifier = Modifier.padding(end =14.dp).weight(1f).onFocusChanged { focusState ->
////                                    if (focusState.isFocused) {
//                                    // Send Toast when losing focus
////                                        Toast.makeText(context, "Edit to ${nameValue.text}", Toast.LENGTH_SHORT).show()
//                                    focusedSearch = focusState.isFocused
//
//                                },
//                                placeholder = {
//                                    Text(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        text = "Search".uppercase(),
//                                        style = TextStyle(
//                                            textAlign = TextAlign.Start,
//                                            fontFamily = MonomaniacOneFamily,
//                                            color = dgenGray,
//                                            fontSize = label_fontSize,
//                                            lineHeight = label_fontSize,
//                                            letterSpacing = 2.sp,
//                                            textDecoration = TextDecoration.None
//                                        )
//                                    )
//                                },
//                                isAnyFieldFocused = isAnyFieldFocused
//                            )
//
//                        }
//                    }
//
//
//
//
//
//                            LazyVerticalGrid(
//                                modifier = Modifier.padding(horizontal = 24.dp),
//                                columns = GridCells.Fixed(3), // Exakt 3 Spalten
//                                //columns = GridCells.Adaptive(minSize = 120.dp), // Minimum size of each card
//                                contentPadding = PaddingValues(20.dp), // Padding around the grid
//                                horizontalArrangement = Arrangement.spacedBy(20.dp), // Horizontal spacing between cards
//                                verticalArrangement = Arrangement.spacedBy(20.dp)
//                            ) {
//                                //items(filteredItems) { (contact, showHeader) ->
//                                items(25){
//                                    AppCard(
//                                        context = context,
//                                        painter = painterResource(R.drawable.coinbase2),
//                                        category = "MARKETPLACE",
//                                        appName = "Coinbase",
//                                    )
//                                }
//                            }
//                        }







            }
        }
    }
}
