package com.levp.currencytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levp.currencytracker.R
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clHeaderBackground
import com.levp.currencytracker.ui.theme.clMainBackground
import com.levp.currencytracker.ui.theme.clMainSecondary

@Composable
fun CurrencySwitch(
    currencyName: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

            .border(width = 1.dp, color = clMainSecondary, shape = shape)
            .height(48.dp)
            //.fillMaxWidth()
            //.width(272.dp)
            .background(color = clMainBackground, shape = shape)
            .padding(start = 16.dp, end = 16.dp)
        //
    ) {
        Text(text = currencyName)
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_down),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun CurrencySwitchPreview() {
    CurrencyTrackerTheme {
        Box(modifier = Modifier
            .width(400.dp)
            .height(100.dp)
            .background(color = clHeaderBackground),
            contentAlignment = Alignment.Center
        ) {
            CurrencySwitch(currencyName = "USD")
        }
    }
}