package edu.ucne.miguelbetances_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable
    data object ListScreen : Screen()
    @Serializable
    data class VentasScreen(val id: Int) : Screen()
}