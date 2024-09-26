package edu.ucne.miguelbetances_p1_ap2.presentation.navigation.ventas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.VentasEntity
import edu.ucne.miguelbetances_p1_ap2.data.repository.VentasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentasViewModel @Inject constructor(
	private val ventaRepository: VentasRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(UiState())
	val uiState get() = _uiState.asStateFlow()

	init {
		getVentas()
	}

	fun save() {
		viewModelScope.launch {
			val state = _uiState.value
			when {
				state.datoCliente.isBlank() -> {
					_uiState.update {
						it.copy(errorMessages = "Los datos del cliente no pueden estar vacíos", successMessage = null)
					}
				}
				state.monto <= 0.0 -> {
					_uiState.update {
						it.copy(errorMessages = "El monto debe ser mayor que cero", successMessage = null)
					}
				}
				else -> {
					try {
						val exists = ventaRepository.find(state.ventaId ?: 0)
						if (exists != null && exists.ventaId != state.ventaId) {
							_uiState.update {
								it.copy(errorMessages = "Ya existe una venta con estos datos del cliente", successMessage = null)
							}
						} else {
							ventaRepository.save(state.toEntity())
							_uiState.update {
								it.copy(
									successMessage = "Venta guardada exitosamente",
									errorMessages = null
								)
							}
							nuevo()
						}
					} catch (e: Exception) {
						_uiState.update {
							it.copy(errorMessages = "Error al guardar la venta", successMessage = null)
						}
					}
				}
			}
		}
	}

	fun nuevo() {
		_uiState.update {
			it.copy(
				ventaId = null,
				datoCliente = "",
				galon = 0.0,
				monto = 0.0,
				descuento = 0.0,
				totalDescuento = 0.0,
				total = 0.0,
				errorMessages = null,
				successMessage = null
			)
		}
	}

	fun selectVenta(ventaId: Int) {
		viewModelScope.launch {
			if (ventaId > 0) {
				val venta = ventaRepository.find(ventaId)
				_uiState.update {
					it.copy(
						ventaId = venta?.ventaId,
						datoCliente = venta?.datoCliente ?: "",
						monto = venta?.monto ?: 0.0,
						galon = venta?.galon ?: 0.0,
						descuento = venta?.descuento ?: 0.0,
						totalDescuento = venta?.totalDescuento ?: 0.0,
						total = venta?.total ?: 0.0,
						errorMessages = null,
						successMessage = null
					)
				}
			}
		}
	}

	fun delete() {
		viewModelScope.launch {
			try {
				val entityToDelete = _uiState.value.toEntity()
				ventaRepository.delete(entityToDelete)

				_uiState.update {
					it.copy(
						successMessage = "Venta eliminada exitosamente",
						errorMessages = null
					)
				}

				nuevo()

			} catch (e: Exception) {
				Log.e("VentasViewModel", "Error al eliminar la venta", e)

				_uiState.update {
					it.copy(
						errorMessages = "Error al eliminar la venta",
						successMessage = null
					)
				}
			}
		}
	}

	fun getVentas() {
		viewModelScope.launch {
			ventaRepository.getAll().collect { venta ->
				_uiState.update {
					it.copy(ventas = venta)
				}
			}
		}
	}

	fun onDescripcionChange(descripcion: String) {
		_uiState.update {
			it.copy(datoCliente = descripcion, errorMessages = null, successMessage = null)
		}
	}

	fun onMontoChange(monto: Double) {
		_uiState.update {
			it.copy(monto = monto, errorMessages = null, successMessage = null)
		}
	}

	data class UiState(
		val ventaId: Int? = null,  // Puede ser nullable
		val datoCliente: String = "",
		val galon: Double = 0.0,
		val monto: Double = 0.0,
		val totalDescuento: Double = 0.0,
		val total: Double = 0.0,
		val descuento: Double = 0.0,
		val errorMessages: String? = null,
		val successMessage: String? = null,
		val ventas: List<VentasEntity> = emptyList()
	)

	fun UiState.toEntity() = VentasEntity(
		ventaId = ventaId,
		datoCliente = datoCliente,
		monto = monto,
		galon = galon,
		descuento = descuento,
		totalDescuento = totalDescuento,
		total = total
	)
}
