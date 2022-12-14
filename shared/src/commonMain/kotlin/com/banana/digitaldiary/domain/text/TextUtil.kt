package com.banana.digitaldiary.domain.text

object TextUtil {

    fun formatString(string: String): String {
        if (string.length >= 28)
            return string.take(25).plus("...")
        else
            return string
    }

}