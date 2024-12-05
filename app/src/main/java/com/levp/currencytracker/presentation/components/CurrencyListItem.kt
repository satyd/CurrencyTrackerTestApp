package com.levp.currencytracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levp.currencytracker.R
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clListItemBackground

//hehe fix font and favorite icon, add onclick
//prob fix text color
@Composable
fun CurrencyListItem(
    currencyName: String,
    exchangeRate: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(color = clListItemBackground, shape = RoundedCornerShape(12.dp))
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = currencyName,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = exchangeRate,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (isFavorite) {
                IconButton(modifier = Modifier
                    .padding(0.dp)
                    .size(24.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.favorites_on),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            } else {
                IconButton(modifier = Modifier
                    .padding(0.dp)
                    .size(24.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.favorites_off),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CurrencyListItemPreview() {
    CurrencyTrackerTheme {
        CurrencyListItem(
            currencyName = "USD",
            exchangeRate = "2.23674",
            isFavorite = true
        )
    }
}