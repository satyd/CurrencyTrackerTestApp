package com.levp.currencytracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levp.currencytracker.R
import com.levp.currencytracker.ui.theme.clHeaderBackground
import com.levp.currencytracker.ui.theme.clText

@Composable
fun FavoritesHeader(
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(clHeaderBackground)) {
        Text(
            text = stringResource(R.string.favorites_title),
            color = clText,
            lineHeight = 28.sp,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
        )
    }
}
