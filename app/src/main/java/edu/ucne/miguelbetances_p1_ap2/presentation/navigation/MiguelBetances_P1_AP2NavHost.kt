package edu.ucne.miguelbetances_p1_ap2.presentation.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.miguelbetances_p1_ap2.presentation.navigation.ventas.VentasListScreen
import edu.ucne.miguelbetances_p1_ap2.ui.theme.MiguelBetances_P1_AP2Theme

@Composable
fun MiguelBetances_P1_AP2NavHost(
    navHostController: NavHostController
){
    NavHost(
        startDestination = Screen.VentasScreen,
        navController = navHostController
    ) {
        composable<Screen.ListScreen> {
            VentasListScreen { }(
                onCreate = {
                    navHostController.navigate(Screen.VentasScreen(0))
                },
                onEdit = {
                    navHostController.navigate(Screen.VentasScreen(it))
                },
            )
        }
        composable<Screen.VentasScreen> {
            val args =  it.toRoute<Screen.VentasScreen>()
            Screen.VentasScreen(
                ventasId = args.id,
                goBack = {
                    navHostController.navigateUp()
                }
            )

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MiguelBetances_P1_AP2NavHostPreview() {
    MiguelBetances_P1_AP2Theme() {
        val navHostController = rememberNavController()
        MiguelBetances_P1_AP2NavHost(
            navHostController = navHostController)
    }
}
