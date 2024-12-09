package com.levp.currencytracker.presentation.elements

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levp.currencytracker.R
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clHeaderBackground
import com.levp.currencytracker.ui.theme.clMainBackground
import com.levp.currencytracker.ui.theme.clMainSecondary
import com.levp.currencytracker.ui.theme.clSelectedItem
import com.levp.currencytracker.ui.theme.clText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySwitch(
    selectedCurrency: SupportedSymbols,
    onValueChange: (SupportedSymbols) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    val options = SupportedSymbols.entries
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier.background(color = clMainBackground, shape = shape)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .border(width = 1.dp, color = clMainSecondary, shape = shape)
                .height(48.dp)
                .padding(0.dp)
                .fillMaxWidth()
                .background(color = clMainBackground, shape = shape),
            readOnly = true,
            textStyle = TextStyle(
                color = clText,
                lineHeight = 20.sp,
                fontSize = 14.sp,
                fontWeight = FontWeight(500)
            ),
            supportingText = null,
            value = selectedCurrency.name,
            onValueChange = { },
            trailingIcon = {
                if (expanded) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_up),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_down),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedIndicatorColor = clMainBackground,
                disabledIndicatorColor = clMainBackground,
                focusedIndicatorColor = clMainBackground,
                focusedContainerColor = clMainBackground,
                unfocusedContainerColor = clMainBackground
            )
        )
        ExposedDropdownMenu(
            modifier = Modifier.background(color = clMainBackground),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            options.forEach { selectionOption ->
                val color = if (selectionOption == selectedCurrency) {
                    clSelectedItem
                } else {
                    clMainBackground
                }
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = color),
                    onClick = {
                        Log.d("hehe", "update data for $selectionOption")
                        onValueChange(selectionOption)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = selectionOption.name,
                            style = TextStyle(
                                color = clText,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(500),
                            )
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrencySwitchPreview() {
    CurrencyTrackerTheme {
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(100.dp)
                .background(color = clHeaderBackground),
            contentAlignment = Alignment.Center
        ) {
            //hehe CurrencySwitch(currencyName = "USD", {})
        }
    }
}