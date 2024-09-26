package edu.ucne.miguelbetances_p1_ap2.presentation.navigation.ventas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.miguelbetances_p1_ap2.presentation.navigation.Screen

@Composable
fun Screen.VentasScreen(
	viewModel: VentasViewModel = hiltViewModel(),
	ventasId,
	goBack: () -> Unit

) {
	LaunchedEffect(ventasId) {
		viewModel.selectVenta(ventasId)
	}
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	VentasBodyScreen { }(
		uiState = uiState,
		onDescripcionChange = viewModel::onDescripcionChange,
		onMontoChange = viewModel::onMontoChange,
		onGuardarClick = viewModel::save,
		onEliminarClick = viewModel::delete,
		onCancelarClick = goBack
	)

}

@Composable
fun VentasBodyScreen(
	uiState: VentasViewModel.UiState,
	onDescripcionChange: (String) -> Unit,
	onMontoChange: (Double) -> Unit,
	onGuardarClick: () -> Unit,
	onEliminarClick: () -> Unit,
	onCancelarClick: () -> Unit

)

{
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		OutlinedTextField(
			value = uiState.descripcion,
			onValueChange = { onDescripcionChange(it) },
			label = { Text("Descripción") },
			modifier = Modifier.fillMaxWidth()
		)
		Spacer(modifier = Modifier.height(16.dp))


		OutlinedTextField(
			value = if (uiState.monto != 0.0) uiState.monto.toString() else "",
			onValueChange = { newValue ->
				newValue.toDoubleOrNull()?.let { onMontoChange(it) }
			},
			label = { Text("Monto") },
			keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
			modifier = Modifier.fillMaxWidth()
		)
		Spacer(modifier = Modifier.height(16.dp))


		uiState.errorMessages?.let {
			Text(
				text = it,
				color = androidx.compose.material3.MaterialTheme.colorScheme.error,
				modifier = Modifier.padding(8.dp)
			)
		}


		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			Button(onClick = onGuardarClick) {
				Text("Guardar")
			}
			Button(onClick = onEliminarClick, enabled = uiState.ventaId != null) {
				Text("Eliminar")
			}
			Button(onClick = onCancelarClick) {
				Text("Cancelar")
			}
		}


		uiState.successMessage?.let {
			Spacer(modifier = Modifier.height(16.dp))
			Text(
				text = it,
				color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(8.dp)
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PreviewVentasBodyScreen() {
	VentasBodyScreen { }(
		uiState = VentasViewModel.UiState(
			descripcion = "Ahorro para viaje",
			monto = 1000.0,
			successMessage = "Venta guardada exitosamente"
		),
		onDescripcionChange = {},
		onMontoChange = {},
		onGuardarClick = {},
		onEliminarClick = {},
		onCancelarClick = {}
	)
}
