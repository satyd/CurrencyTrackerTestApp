package com.levp.currencytracker.data.util

import android.util.Log
import com.levp.currencytracker.data.remote.QuoteDto
import org.json.JSONObject


fun parseJson(
    baseSymbol: String,
    jsonString: String
): MutableList<QuoteDto> {
    val jsonObject = JSONObject(jsonString)
    val ratesObject = jsonObject.getJSONObject("rates")
    val keys = ratesObject.keys()
    val result = mutableListOf<QuoteDto>()
    while (keys.hasNext()) {
        val key = keys.next() as String
        val value = ratesObject.get(key).toString()
        result.add(QuoteDto(currencyName1 = baseSymbol, currencyName2 = key, rate = value))

    }
    Log.w("hehe", "res = ${result}")
    return result
}