package com.elisa.lucaskart.ui.theme.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyBottomAppBar() {
    BottomAppBar(
        // Personnalisez l'apparence de votre BottomAppBar ici
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,

        ) {
        IconButton(onClick = { /* Gérer le clic ici */ }) {
            Icon(
                Icons.Filled.Share, contentDescription = "Accueil",
                Modifier.size(40.dp))
        }
        Spacer(Modifier.weight(1f, true))
        Text(
            text = "Mention légale",
            fontSize = 20.sp,
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyBottomAppBarPreview() {
    MyBottomAppBar()
}