package com.banana.digitaldiary.domain.contact

import com.banana.digitaldiary.presentation.*

data class Contact (
    val id: Long?,
    val name: String,
    val phone: String,
    val email: String,
    val colorHex: Long
) {
    companion object {
        private val colors = listOf(lavenderWebHex, periwinkleCrayolaHex, isabellineHex, powderBlueHex, mintCreamHex, mimiPinkHex, mistyRoseHex, linenHex, magnoliaHex)

        fun generateRandomColor() = colors.random()
    }
}