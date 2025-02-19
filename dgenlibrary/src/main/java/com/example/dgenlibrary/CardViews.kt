package com.example.dgenlibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenWhite
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale



@Composable
fun SendCardView(
    amount: Double,
    tokenName: String,
    chainList: List<Int>,
){

    val decimalFormat = DecimalFormat("0.00").apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale.US) // Forces the decimal point
    }
    val cursorVisible = remember { mutableStateOf(true) }

    // TextMeasurer to calculate text width dynamically
    val textMeasurer = rememberTextMeasurer()

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    var value by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }

    // Create a blinking effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(500) // Toggle every 500ms
            cursorVisible.value = !cursorVisible.value
        }
    }

    Box(
        modifier = Modifier.alpha(0.25f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(R.drawable.usdc),
            contentDescription = "Ethereum"
        )
    }
    Column (
        verticalArrangement =  Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize().padding(vertical = 12.dp, horizontal = 16.dp),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {


                Text(
                    text = "SEND $tokenName",
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,

                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    )
                )
            }

            SelectableIconRow(
                chainList,
                {}
            )

        }


        Row {
            Box(
                modifier = Modifier.weight(1f)
            ){
                if (value.isEmpty()){
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "0.0",
                        style = TextStyle(
                            fontFamily = PitagonsSans,
                            color = dgenWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 48.sp,

                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None

                        )
                    )
                }
                BasicTextField(
                    maxLines= 1,
                    modifier = Modifier.fillMaxWidth().drawBehind {
                        textLayoutResult?.let { layoutResult ->
                            if (cursorVisible.value) {
                                // Get last character position (if text is not empty)
                                val cursorOffset = if (value.isNotEmpty()) {
                                    val lastCharIndex = value.length - 1
                                    val cursorRect = layoutResult.getBoundingBox(lastCharIndex)
                                    cursorRect
                                } else {
                                    Rect(0f, 0f, 2.dp.toPx(), layoutResult.size.height.toFloat())
                                }

                                // Draw the blinking cursor at the correct position
                                drawLine(
                                    color = Color.White,
                                    start = Offset(cursorOffset.right + 2.dp.toPx(), cursorOffset.top),
                                    end = Offset(cursorOffset.right + 2.dp.toPx(), cursorOffset.bottom),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    },
                    value = value,
                    onValueChange = { value = it },
                    cursorBrush = SolidColor(Color.Transparent), // Hide default cursor
                    textStyle = TextStyle(
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 48.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    decorationBox = { innerTextField ->


                            innerTextField()

                    }
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            Box(
                modifier = Modifier.weight(1f)
            ){
                if (address.isEmpty()){
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "Address",
                        style = TextStyle(
                            fontFamily = PitagonsSans,
                            color = dgenWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,

                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None

                        )
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth()
                    ,
                    value = address,
                    onValueChange = { address = it },
                    cursorBrush = SolidColor(Color.Transparent), // Hide default cursor
                    textStyle = TextStyle(
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,

                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    maxLines= 2,
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier =Modifier.background(Color.Transparent, RoundedCornerShape(percent = 30))
                            //.padding(16.dp)
                        ) {


                            innerTextField()
                        }
                    }
                )
            }


            Column (
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(0.25f),

                ){
                Text(
                    text= "MAX",
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    )
                )
                Text(
                    text= formatSmart(amount),
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    )
                )
            }

        }
    }
}


@Composable
fun SelectableIconRow(
    icons: List<Int>,  // Liste der Icons
    onValueChange: (Int) -> Unit // Callback für den ausgewählten Wert
) {
    var selectedIndex by remember { mutableStateOf(-1) }  // Index des ausgewählten Buttons

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        icons.forEachIndexed { index, icon ->
            IconButton(
                onClick = {
                    if (selectedIndex != index) { // Verhindert das Deselektieren
                        selectedIndex = index
                        onValueChange(index)
                    }

                    //onValueChange(index) // Gibt den ausgewählten Wert zurück
                },
                modifier = Modifier
                    .border(
                        width = if (index == selectedIndex) 2.dp else 0.dp, // Weißer Rand für Selektion
                        color = if (index == selectedIndex) Color.White else Color.Transparent,
                        shape = CircleShape
                    ).size(32.dp)

            ) {

                    when(icon){
                        1 -> {

                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.mainnet),
                                contentDescription = "Ethereum"
                            )
                        }
                        10 -> {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.optimism),
                                contentDescription = "Ethereum"
                            )
                        }
                        8453 -> {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.base),
                                contentDescription = "Ethereum"
                            )
                        }
                        42161 -> {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.arbitrum),
                                contentDescription = "Ethereum"
                            )
                        }
                    }

//                Icon(
//                    painter = painterResource(icon),
//                    contentDescription = "Icon $index",
//                    tint = if (index == selectedIndex) Color.White else Color.Gray
//                )
            }
        }
    }
}



@Preview(
//    showBackground = true,
    widthDp = 447,
    heightDp = 447,
)
@Composable
fun SendCardPreviewView(){
    SendCardView(
        amount = 120.00,
        tokenName = "USDC",
        chainList = listOf(1,10,8453,42161)
    )
}