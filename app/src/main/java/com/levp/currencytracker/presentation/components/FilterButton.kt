package com.levp.currencytracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.levp.currencytracker.ui.theme.clMainBackground
import com.levp.currencytracker.ui.theme.clMainSecondary

@Composable
fun FilterButton(
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(width = 1.dp, color = clMainSecondary, shape = shape)
            .background(color = clMainBackground, shape = shape)
            .clickable { onClick() },
            //.padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(R.drawable.filter),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
@Preview
fun FilterButtonPreview(){
    CurrencyTrackerTheme {
        FilterButton {

        }
    }
}