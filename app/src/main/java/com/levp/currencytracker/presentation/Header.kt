package com.levp.currencytracker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levp.currencytracker.R
import com.levp.currencytracker.presentation.components.CurrencySwitch
import com.levp.currencytracker.presentation.components.FilterButton
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clHeaderBackground
import com.levp.currencytracker.ui.theme.clText

@Composable
fun Header(
    switchOnClick: () -> Unit,
    filterOnClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth().background(clHeaderBackground)) {
        Text(
            text = stringResource(R.string.main_title),
            color = clText,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
        )
        //Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth(),

            ) {
            CurrencySwitch(
                currencyName = "USD",
                modifier = Modifier.weight(1f),
                switchOnClick = switchOnClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterButton {
                filterOnClick()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    CurrencyTrackerTheme {
        Header({}, {})
    }
}