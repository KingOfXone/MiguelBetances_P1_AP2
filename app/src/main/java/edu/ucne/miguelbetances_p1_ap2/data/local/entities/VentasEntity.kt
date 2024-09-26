package edu.ucne.miguelbetances_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentasEntity(
    @PrimaryKey
    val ventaId: Int? = null,
    val galon: Double,
    val descuento: Int? = null,
    val monto: Double,
    val descripcion: String
)
