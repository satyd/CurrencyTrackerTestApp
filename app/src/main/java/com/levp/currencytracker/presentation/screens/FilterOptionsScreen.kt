package com.levp.currencytracker.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.levp.currencytracker.domain.util.SortingOptions
import com.levp.currencytracker.presentation.components.FilterOptionsHeader
import com.levp.currencytracker.presentation.elements.FilterListItem
import com.levp.currencytracker.ui.theme.clButton
import com.levp.currencytracker.ui.theme.clDivider
import com.levp.currencytracker.ui.theme.clMainBackground
import com.levp.currencytracker.ui.theme.clTextDescription

@Composable
fun FilterOptionsScreen(
    navController: NavController,
    selectedItem: Int,
    onApplyClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(selectedItem)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = clMainBackground)
    ) {
        FilterOptionsHeader(onBack = { navController.popBackStack() })
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(color = clDivider)
        )
        Text(
            text = "SORT BY", style = TextStyle(
                color = clTextDescription,
                fontSize = 12.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                //.padding(horizontal = 16.dp)
                .background(color = clMainBackground),
            verticalArrangement = Arrangement.Top
        ) {
            items(SortingOptions.entries.size) { index ->
                val entry = SortingOptions.entries[index]
                FilterListItem(
                    filterName = entry.title,
                    isSelected = index == selectedItemIndex,
                    onClick = {
                        selectedItemIndex = index
                    }
                )
                if (index < SortingOptions.entries.size) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Button(
            onClick = {
                Log.v("hehe", "saving value $selectedItemIndex")
                onApplyClick(selectedItemIndex)
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Unspecified,
            ),
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = clButton, shape = RoundedCornerShape(100.dp)),
        ) {
            Text(
                text = "Apply",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight(500)
                ),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}