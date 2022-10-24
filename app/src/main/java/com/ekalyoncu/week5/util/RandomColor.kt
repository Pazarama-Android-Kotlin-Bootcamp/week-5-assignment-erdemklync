package com.ekalyoncu.week5.util

fun generateRandomColor() : String {
    val allowedChars = ('a'..'f') + ('0'..'9')
    return (1..6)
        .map { allowedChars.random() }
        .joinToString("")
}