package com.elisa.lucaskart.ui.theme.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elisa.lucaskart.R
import com.elisa.lucaskart.ui.theme.LucasKartTheme
import com.elisa.lucaskart.ui.theme.Routes
import com.elisa.lucaskart.viewmodel.MainView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController? = null) {
    Row(
        ) {

        IconButton(onClick = {  }) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu",
                Modifier.size(40.dp))
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = {  navController?.navigate(Routes.NewHomeScreen.route) }) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de l'association")
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { /* Gérer le clic ici */ }) {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Mon compte",
                Modifier.size(40.dp))
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navController?.navigate(Routes.ShopScreen.route)
            Log.d("Navigation", "Clicked on cart icon")}
        ) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = "Panier",
                Modifier.size(40.dp))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyTopAppBarPreview() {
    LucasKartTheme {
        Surface() {
            val prodViewModel = MainView()
            ShopScreen(prodViewModel = prodViewModel)
        }
    }
}