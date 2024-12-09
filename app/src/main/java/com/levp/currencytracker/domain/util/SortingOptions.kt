package com.levp.currencytracker.domain.util

enum class SortingOptions(val title: String, val isSelected: Boolean = false) {
    CodeAsc("Code A-Z"),
    CodeDesc("Code Z-A"),
    QuoteAsc("Quote Asc."),
    QuoteDesc("Quote Desc."),
}