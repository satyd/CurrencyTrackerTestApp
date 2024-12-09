package com.levp.currencytracker.presentation.elements

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
import com.levp.currencytracker.domain.model.ExchangeRateEntry
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clListItemBackground
import com.levp.currencytracker.ui.theme.clText

//hehe fix font and favorite icon, add onclick
//prob fix text color
@Composable
fun CurrencyListItem(
    rateEntry: ExchangeRateEntry,
    isFavoritesTab: Boolean = false,
    onFavoriteClick: (ExchangeRateEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val text = if(isFavoritesTab) {
        "${rateEntry.symbol1}/${rateEntry.symbol2}"
    } else {
        rateEntry.symbol2
    }

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
            text = text,
            style = TextStyle(
                color = clText,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = rateEntry.rate.toString(),
                style = TextStyle(
                    color = clText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (rateEntry.isFavorite) {
                IconButton(modifier = Modifier
                    .padding(0.dp)
                    .size(24.dp),
                    onClick = {onFavoriteClick(rateEntry)}
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
                    onClick = {onFavoriteClick(rateEntry)}
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
            rateEntry = ExchangeRateEntry("USD", "EUR", 0.95),
            isFavoritesTab = true,
            {}
        )
    }
}