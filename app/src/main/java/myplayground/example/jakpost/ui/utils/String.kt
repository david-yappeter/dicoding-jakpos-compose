package myplayground.example.jakpost.ui.utils

import java.util.Locale

fun String.capitalized(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}