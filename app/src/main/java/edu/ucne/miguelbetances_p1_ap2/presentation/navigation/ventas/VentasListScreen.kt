package edu.ucne.miguelbetances_p1_ap2.presentation.navigation.ventas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.miguelbetances_p1_ap2.data.local.entities.VentasEntity

@Composable
fun VentasListScreen(
	viewModel:  VentasViewModel = hiltViewModel(),
	onCreate: () -> Unit,
	onEdit: (Int) -> Unit,
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	VentaListBodyScreen(
		uiState = uiState,
		onCreate = onCreate,
		onEdit = onEdit
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaListBodyScreen(
	uiState: VentasViewModel.UiState,
	onCreate: () -> Unit,
	onEdit: (Int) -> Unit
) {
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = onCreate,
				modifier = Modifier.padding(16.dp),
				containerColor = MaterialTheme.colorScheme.secondary,
				contentColor = Color.Gray
			) {
				Icon(
					imageVector = Icons.Default.Add,
					contentDescription = "Agregar"
				)
			}
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(20.dp)
		) {
			Text(
				text = "Registro de Ventas",
				style = MaterialTheme.typography.headlineSmall,
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = 20.dp),
				textAlign = TextAlign.Center,
				color = MaterialTheme.colorScheme.primary
			)
			Spacer(modifier = Modifier.height(20.dp))
			LazyColumn(
				modifier = Modifier.fillMaxWidth(),
				contentPadding = PaddingValues(16.dp)
			) {
				items(uiState.ventas) { venta ->
					ListVentaItem { }(
						venta = venta,
						onEdit = onEdit
					)
				}
			}
		}
	}
}

@Composable
fun ListVentaItem(
	venta: VentasEntity,
	onEdit: (Int) -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp),
		onClick = {
			venta.ventaId?.let { onEdit(it) }
		}
	) {
		Column(
			modifier = Modifier.padding(16.dp)
		) {
			Text(
				text = venta.descripcion,
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.primary
			)
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = "Monto: ${venta.monto}",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}
