package com.example.stattrack.View.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.Fragment

sealed class NavItem(var route: String, var icon: ImageVector, var title: String) {
    object Kamp : NavItem("Kamp", Icons.Default.SportsHandball, "KAMP")
    object Hold : NavItem("Hold", Icons.Default.People, "HOLD")
    object Spiller : NavItem("fragment_spiller", Icons.Default.Person, "SPILLER")
}