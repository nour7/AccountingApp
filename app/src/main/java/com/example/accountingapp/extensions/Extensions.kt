package com.example.accountingapp.extensions

fun Long.isValid() = when(this) {
    -1L -> false
    else -> true
}

fun Int.isValid() = when(this) {
    -1 -> false
    else -> true
}