package com.elisa.lucaskart.ui.theme.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.elisa.lucaskart.R
import com.elisa.lucaskart.model.productList
import com.elisa.lucaskart.ui.theme.LucasKartTheme
import com.elisa.lucaskart.viewmodel.MainView


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShopDetailScreenPreview() {
    LucasKartTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //Jeu de donnée pour la Preview
            val mainViewModel = MainView()
            mainViewModel.myList.addAll(productList)
            ShopDetailScreen(1, prodViewModel = mainViewModel)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable //id du PictureBean à afficher
fun ShopDetailScreen(id: Int,
                 navHostController: NavHostController? = null,
                 prodViewModel: MainView)
{

    val pictureBean = prodViewModel.myList.find { it.product_id == id }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)) {
        Text(
            text = pictureBean?.product_name ?: "-",
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary
        )

        if (pictureBean != null) {
            GlideImage(
                model = pictureBean.product_image,
                //Dans string.xml
                //contentDescription = getString(R.string.picture_of_cat),
                //En dur
                contentDescription = "une photo de chat",
                // Image d'attente. Permet également de voir l'emplacement de l'image dans la Preview
                loading = placeholder(R.mipmap.ic_launcher_round),
                // Image d'échec de chargement
                failure = placeholder(R.mipmap.ic_launcher),
                contentScale = ContentScale.Fit,
                //même autres champs qu'une Image classique
                modifier = Modifier.fillMaxWidth().weight(2f)
            )
        }

        Text(
            text = pictureBean?.product_description ?: "Elément inconnu",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        Button(
            onClick = { navHostController?.popBackStack() },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Retour")
        }
    }
}