package com.levp.currencytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levp.currencytracker.R
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clListItemBackground
import com.levp.currencytracker.ui.theme.clMainBackground

//hehe fix
@Composable
fun FilterListItem(
    filterName: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .border(width = 0.dp, shape = RectangleShape, color = Color.Unspecified)
            .background(color = clMainBackground)
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = filterName,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        )
            if (isSelected) {
                IconButton(modifier = Modifier
                    .padding(0.dp)
                    .size(24.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_selected),
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
                        imageVector = ImageVector.vectorResource(R.drawable.ic_unselected),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }

    }
}

@Composable
@Preview
fun FilterListItemPreview() {
    CurrencyTrackerTheme {
        Column {
            FilterListItem(
                filterName = "Code A-Z",
                isSelected = true
            )
            FilterListItem(
                filterName = "Quote Asc.",
                isSelected = false
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}