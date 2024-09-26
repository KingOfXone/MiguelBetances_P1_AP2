package edu.ucne.miguelbetances_p1_ap2.presentation.navigation.ventas
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.VentasEntity
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

	private suspend fun existsWithDescription(descripcion: String): Boolean {
		return ventaRepository.find(descripcion) != null
	}



	init {
		getVentas()
	}

	fun save() {
		viewModelScope.launch {
			val state = _uiState.value
			when {
				state.descripcion.isBlank() -> {
					_uiState.update {
						it.copy(errorMessages = "La descripción no puede estar vacía", successMessage = null)
					}
				}
				state.monto <= 0.0 -> {
					_uiState.update {
						it.copy(errorMessages = "El monto debe ser mayor que cero", successMessage = null)
					}
				}
				else -> {
					try {
						val exists = ventaRepository.find(state.descripcion)
						if (exists != null && exists.ventaId != state.ventaId) {
							_uiState.update {
								it.copy(errorMessages = "Ya existe una venta con esta descripción", successMessage = null)
							}
						} else {
							ventaRepository.save(state.ToEntity())
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
							it.copy(errorMessages = "Error al guardar la Venta", successMessage = null)
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
				descripcion = "",
				monto = 0.0,
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
						descripcion = venta?.descripcion ?: "",
						monto = venta?.monto ?: 0.0,
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
				val entityToDelete = _uiState.value.ToEntity()
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
			it.copy(descripcion = descripcion, errorMessages = null, successMessage = null)
		}
	}

	fun onMontoChange(monto: Double) {
		_uiState.update {
			it.copy(monto = monto, errorMessages = null, successMessage = null)
		}
	}

	data class UiState(
		val ventaId: Int,
		val descripcion: String = "",
		val monto: Double = 0.0,
		val errorMessages: String? = null,
		val successMessage: String? = null,
		val ventas: List<VentasEntity> = emptyList()
	)

	fun UiState.ToEntity() = VentasEntity(
		ventaId = ventaId,
		descripcion = descripcion,
		monto = monto,
		galon = TODO(),
		descuento = TODO()
	)
}
