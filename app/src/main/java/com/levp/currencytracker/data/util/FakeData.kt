package com.levp.currencytracker.data.util

import com.levp.currencytracker.domain.util.SupportedSymbols
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.ThreadLocalRandom


suspend fun getFakeExchangeRatesResponse(symbol: SupportedSymbols): String {
    delay(500)
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 4
    }
    with(formatter) {
        val random1 = format(ThreadLocalRandom.current().nextDouble(0.311, 1.012)).replace(",", ".")
        val random5 = format(ThreadLocalRandom.current().nextDouble(3.011, 7.032)).replace(",", ".")
        val random100 =
            format(ThreadLocalRandom.current().nextDouble(80.332, 100.076)).replace(",", ".")
        val random150 =
            format(ThreadLocalRandom.current().nextDouble(120.132, 200.005)).replace(",", ".")
        return when (symbol) {
            SupportedSymbols.USD -> {
                "{\"base\":\"USD\",\"date\":\"2021-03-17\",\"rates\":{\"EUR\":$random1,\"BYN\":$random5,\"JPY\":$random150,\"RUB\":$random100},\"success\":true,\"timestamp\":1519296206}"
            }

            SupportedSymbols.EUR -> {
                "{\"base\":\"EUR\",\"date\":\"2021-03-17\",\"rates\":{\"USD\":$random1,\"BYN\":$random5,\"JPY\":$random150,\"RUB\":$random100},\"success\":true,\"timestamp\":1519296206}"
            }

            SupportedSymbols.BYN -> {
                "{\"base\":\"BYN\",\"date\":\"2021-03-17\",\"rates\":{\"USD\":$random1,\"EUR\":$random5,\"JPY\":$random150,\"RUB\":$random100},\"success\":true,\"timestamp\":1519296206}"
            }

            SupportedSymbols.RUB -> {
                "{\"base\":\"RUB\",\"date\":\"2021-03-17\",\"rates\":{\"USD\":$random1,\"BYN\":$random5,\"JPY\":$random150,\"EUR\":$random1},\"success\":true,\"timestamp\":1519296206}"
            }

            SupportedSymbols.JPY -> {
                "{\"base\":\"JPY\",\"date\":\"2021-03-17\",\"rates\":{\"USD\":$random1,\"BYN\":$random5,\"RUB\":$random150,\"EUR\":$random5},\"success\":true,\"timestamp\":1519296206}"
            }
        }
    }
}

suspend fun getFakeFavExchangeRatesResponse(base: String, symbols: String): String {
    delay(500)
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 4
    }
    with(formatter) {
        val resString = StringBuilder("{\"base\":\"$base\",\"date\":\"2021-03-17\",\"rates\":{")
        symbols.split(",").forEach { symbol ->
            resString.append("\"$symbol\":${getRandomNumberString()},")
        }
        resString[resString.length - 1] = '}'
        resString.append(",\"success\":true,\"timestamp\":1519296206}")

        return resString.toString()
        // "{\"base\":\"$base\",\"date\":\"2021-03-17\",\"rates\":{\"EUR\":$random1,\"BYN\":$random5,\"JPY\":$random150,\"RUB\":$random100},\"success\":true,\"timestamp\":1519296206}"
    }
}

fun getRandomNumberString(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 4
    }
    return formatter.format(ThreadLocalRandom.current().nextDouble(0.0, 150.0)).replace(",", ".")
}

