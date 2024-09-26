package edu.ucne.miguelbetances_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.VentasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentasDao {
    @Upsert
    suspend fun save(ventasEntity: VentasEntity)
    @Query("""
        SELECT * FROM Ventas
        WHERE ventaId = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): VentasEntity?
    @Query(
        """
        SELECT * FROM Ventas
        WHERE descripcion = :descripcion
        LIMIT 1
    """
    )
    suspend fun findByDescripcion(descripcion: String): VentasEntity?


    @Delete
    suspend fun delete(Venta: VentasEntity)
    @Query("SELECT * FROM Ventas")
    fun getAll(): Flow<List<VentasEntity>>
}
