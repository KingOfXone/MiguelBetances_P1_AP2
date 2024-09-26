package edu.ucne.miguelbetances_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentasEntity(
    @PrimaryKey
    val ventaId: Int? = null,
    val datoCliente: String,
    val galon: Double = 0.0,
    val descuento: Double = 0.0,
    val monto: Double = 0.0,
    val totalDescuento: Double = 0.0,
    val total: Double = 0.0,
)
