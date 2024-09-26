package edu.ucne.miguelbetances_p1_ap2.data.repository


import edu.ucne.miguelbetances_p1_ap2.data.local.dao.VentasDao
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.VentasEntity
import javax.inject.Inject

class VentasRepository @Inject constructor(
    private val ventasDao: VentasDao
) {
    suspend fun save(algo: VentasEntity) = ventasDao.save(algo)
    suspend fun find(id: Int) = ventasDao.find(id)
    suspend fun delete(algo: VentasEntity) = ventasDao.delete(algo)
    fun getAll() = ventasDao.getAll()
}
