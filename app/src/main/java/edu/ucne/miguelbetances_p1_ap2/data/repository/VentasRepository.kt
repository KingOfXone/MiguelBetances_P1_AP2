package edu.ucne.miguelbetances_p1_ap2.data.repository

import edu.ucne.miguelbetances_p1_ap2.data.local.dao.VentasDao
import javax.inject.Inject

class VentasRepository @Inject constructor(
    private val ventasDao: VentasDao
) {
    suspend fun save(ventasDao: VentasDao) = ventasDao.save(venta)
    suspend fun find(id: Int) = ventasDao.find(id)
    suspend fun delete(venta) = ventasDao.delete(venta)
    fun getAll() = ventasDao.getAll()
}
