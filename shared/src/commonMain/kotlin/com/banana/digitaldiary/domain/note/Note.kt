package com.banana.digitaldiary.domain.note

import com.banana.digitaldiary.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(celadonBlueHex, queenBlueHex, steelTealHex, jungleGreenHex, pistachioHex, maizeCrayolaHex, mangoTangoHex, yellowOrangeColorWheelHex, orangeRedHex, bittersweetHex)

        fun generateRandomColor() = colors.random()
    }
}

