package edu.ucne.miguelbetances_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.miguelbetances_p1_ap2.data.local.dao.VentasDao
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.VentasEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [VentasEntity::class]
)
abstract class Parcial1Db: RoomDatabase() {
    abstract fun ventasDao(): VentasDao
}
