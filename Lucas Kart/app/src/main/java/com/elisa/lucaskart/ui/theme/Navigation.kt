package com.elisa.lucaskart.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elisa.lucaskart.model.ProduitBean
import com.elisa.lucaskart.ui.theme.screens.NewHomeScreen
import com.elisa.lucaskart.ui.theme.screens.ShopDetailScreen
import com.elisa.lucaskart.ui.theme.screens.ShopScreen
import com.elisa.lucaskart.viewmodel.MainView

sealed class Routes(val route: String) {

    // Route 1
    data object NewHomeScreen : Routes("NewHomeScreen")

    //Route 2
    data object ShopScreen : Routes("ShopScreen")

    //Route 3 avec paramètre
    data object ShopDetailScreen : Routes("ShopDetailScreen/{id}") {
        //Méthode(s) qui vienne(nt) remplit le ou les paramètres
        fun withId(id: Int) = "ShopDetailScreen/$id"

        fun withObject(data: ProduitBean) = "ShopDetailScreen/${data.product_id}"
    }

}

@Composable
fun AppNavigation() {

    val navHostController: NavHostController = rememberNavController()

    //viewModel appartient au framework peremt de récupérer une instance déjà existante s'il en existe une
    val mainViewModel: MainView = viewModel()
    val prodViewModel: MainView = viewModel()


    //Import version avec Composable
    NavHost(navController = navHostController, startDestination = Routes.NewHomeScreen.route) {
        //Route 1 vers 1ere page
        composable(Routes.NewHomeScreen.route) {
            //on peut passer le navHostController à un écran s'il déclenche des navigations
            NewHomeScreen(navHostController, mainViewModel = mainViewModel)
        }
        //Route 2
        composable(Routes.ShopScreen.route) {
            //on peut passer le navHostController à un écran s'il déclenche des navigations
            ShopScreen(navHostController, prodViewModel = prodViewModel)
        }
        //Route 3 vers un écran de détail
        composable(
            route = Routes.ShopDetailScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 1
            ShopDetailScreen(id, navHostController, prodViewModel = MainView())
        }
    }
}