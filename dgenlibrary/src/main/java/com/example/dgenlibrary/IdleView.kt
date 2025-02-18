package com.example.dgenlibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenWhite
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun IdleView(
    modifier: Modifier = Modifier,
    amount: Double,
    tokenName: String,
    fiatAmount: Double,
    chainList: List<Int>,
    icon: Int,
) {

    val decimalFormat = DecimalFormat("0.00").apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale.US) // Forces the decimal point
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
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ){

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                        .size(28.dp),
                    painter = painterResource(icon),
                    contentDescription = "Ethereum"
                )
                Text(
                    text = tokenName,
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


            Text(
                text="$" + decimalFormat.format(fiatAmount),
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = dgenWhite,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,

                    textDecoration = TextDecoration.None
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            LazyRow (
                modifier = Modifier.padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ){
                items(chainList){ item ->
                    when(item){
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
                }
            }

            Text(
                text= formatSmart(amount),
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = dgenWhite,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                )
            )
        }
    }
}