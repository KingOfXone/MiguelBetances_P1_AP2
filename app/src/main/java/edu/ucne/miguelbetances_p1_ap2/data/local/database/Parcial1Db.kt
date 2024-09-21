package edu.ucne.miguelbetances_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.miguelbetances_p1_ap2.data.local.dao.AlgoDao
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.AlgoEntity


@Database(
    version = 1,
    exportSchema = false,
    entities = [AlgoEntity::class]
)
abstract class Parcial1Db: RoomDatabase() {
    abstract fun algoDao(): AlgoDao
}