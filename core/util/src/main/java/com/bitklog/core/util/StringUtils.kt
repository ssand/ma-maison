package com.bitklog.core.util

import java.text.NumberFormat
import java.util.Currency

// Simple logic. No missing currency handling for now.
fun Double.toCurrencyAmount(currency: String = "eur"): String =
    NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        setCurrency(Currency.getInstance(currency.uppercase()))
    }.format(this)
