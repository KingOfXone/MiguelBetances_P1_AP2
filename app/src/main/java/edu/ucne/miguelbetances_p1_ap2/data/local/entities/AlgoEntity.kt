package edu.ucne.miguelbetances_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algo")
data class AlgoEntity(
    @PrimaryKey
    val id: Int? = null
)