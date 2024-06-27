package com.elisa.lucaskart.ui.theme.screens

import android.app.Dialog
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.elisa.lucaskart.R
import com.elisa.lucaskart.model.ProductAPI
import com.elisa.lucaskart.model.ProduitBean
import com.elisa.lucaskart.model.productList
import com.elisa.lucaskart.ui.theme.LucasKartTheme
import com.elisa.lucaskart.ui.theme.Routes
import com.elisa.lucaskart.viewmodel.MainView


//Code affiché dans la Preview, thème claire, thème sombre
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShopScreenPreview() {
    LucasKartTheme {
        Surface() {
            ShopScreen(prodViewModel = MainView())

            }
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopScreen(
    navHostController: NavHostController? = null,
    prodViewModel: MainView)
{


    //Couleur à retirer lors de l'utilisation des thèmes de couleur
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp), // Ajouter un padding pour l'exemple
        verticalArrangement = Arrangement.Center, // Alignement vertical au centre
        horizontalAlignment = Alignment.CenterHorizontally // Alignement horizontal au centre
    ){
        MyTopAppBar()
        Spacer(modifier = Modifier.height(25.dp)) // Espace en haut de l'écran

        // Texte centré horizontalement
        Text(
            text = "Boutique",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(25.dp)) // Espace en haut de l'écran

        // Texte centré horizontalement
        Text(
            text = "Faites partie de notre équipe et découvrez nos produits !",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp)) // Espace en haut de l'écran

        // Image à gauche de l'écran
        Image(
            painter = painterResource(id = R.drawable.prod),
            contentDescription = "Equipe de l'association",
            modifier = Modifier
                .fillMaxWidth()
                //.width(250.dp) // Largeur souhaitée
                //.aspectRatio(1f) // Conserver le ratio hauteur/largeur
        )

        Spacer(modifier = Modifier.height(35.dp)) // Ajoute un espace de 16dp entre le titre et la colonne

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(prodViewModel.myList.size) { index ->
                val prod = prodViewModel.myList[index]
                PictureRowItem(
                    data = prod,
                    onPictureClick = {
                        navHostController?.navigate(
                            Routes.ShopDetailScreen.withObject(
                                prod
                            )
                        )
                        println("test Produit")
                    },
                )

                // Ajouter une Divider après chaque élément, sauf le dernier
                if (index < prodViewModel.myList.size - 1) {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.Black)
                }
            }
        }
        // Boutons
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        prodViewModel.loadList()
                        println("click list")
                    },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    modifier = Modifier.padding(end = 16.dp), // Espacement à droite
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary) // Couleur de fond du premier bouton
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Afficher les produits")
                }
                Button(
                    onClick = { prodViewModel.dialogShown.value = true },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    modifier = Modifier.padding(start = 16.dp), // Espacement à gauche
                    colors = ButtonDefaults . buttonColors (MaterialTheme.colorScheme.primary) // Couleur de fond du premier bouton
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Créer un produit")
                }
                if (prodViewModel.dialogShown.value) {
                    AlertDialog(
                        onDismissRequest = {
                            prodViewModel.dialogShown.value = false
                        },
                        title = { Text(text = "Créer un produit") },
                        text = {
                            Column {
                                TextField(
                                    value = prodViewModel.name.value,
                                    onValueChange = { prodViewModel.name.value = it },
                                    label = { Text("Nom du produit") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                TextField(
                                    value = prodViewModel.image.value,
                                    onValueChange = { prodViewModel.image.value = it },
                                    label = { Text("Image du produit") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                TextField(
                                    value = prodViewModel.description.value,
                                    onValueChange = { prodViewModel.description.value = it },
                                    label = { Text("Description du produit") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                TextField(
                                    value = prodViewModel.price.value.toString(),
                                    onValueChange = { prodViewModel.price.value = it.toInt() },
                                    label = { Text("Prix du produit") }
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    println("click test creer")
                                    println(prodViewModel.name.value)
                                    println(prodViewModel.image.value)
                                    println(prodViewModel.description.value)
                                    println(prodViewModel.price.value)
                                    prodViewModel.createProd(
                                        prodViewModel.name.value,
                                        prodViewModel.image.value,
                                        prodViewModel.description.value,
                                        prodViewModel.price.value
                                    )
                                    prodViewModel.dialogShown.value = false
                                    // myMatchViewModel.equipe1 = equipe1
                                    //equipe2 = equipe2
                                }
                            ) {
                                // Créer le match
                                Text("Créer")
                            }
                        },
                        dismissButton = {
                            Button(
                                // Fermer boite de dialogue
                                onClick = { prodViewModel.dialogShown.value = false }
                            ) {
                                Text("Annuler")
                            }
                        }
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { prodViewModel.editDialogShown.value = true },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier.padding(end = 16.dp), // Espacement à droite
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary) // Couleur de fond du bouton
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Modifier un produit")
            }
// Dialog for editing a product
            if (prodViewModel.editDialogShown.value) {
                AlertDialog(
                    onDismissRequest = {
                        prodViewModel.editDialogShown.value = false
                    },
                    title = { Text(text = "Modifier un produit") },
                    text = {
                        Column {
                            TextField(
                                value = prodViewModel.productId.value.toString(),
                                onValueChange = { prodViewModel.productId.value = it.toLong() },
                                label = { Text("ID du produit") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = prodViewModel.name.value,
                                onValueChange = { prodViewModel.name.value = it },
                                label = { Text("Nom du produit") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = prodViewModel.image.value,
                                onValueChange = { prodViewModel.image.value = it },
                                label = { Text("Image du produit") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = prodViewModel.description.value,
                                onValueChange = { prodViewModel.description.value = it },
                                label = { Text("Description du produit") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = prodViewModel.price.value.toString(),
                                onValueChange = { prodViewModel.price.value = it.toInt() },
                                label = { Text("Prix du produit") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                println("click test modifier")
                                val updatedProduct = ProduitBean(
                                    prodViewModel.productId.value.toInt(),
                                    prodViewModel.name.value,
                                    prodViewModel.image.value,
                                    prodViewModel.description.value,
                                    prodViewModel.price.value
                                )
                                prodViewModel.updateProd(prodViewModel.productId.value, updatedProduct)
                                prodViewModel.editDialogShown.value = false
                            }
                        ) {
                            Text("Modifier")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { prodViewModel.editDialogShown.value = false }
                        ) {
                            Text("Annuler")
                        }
                    }
                )
            }
            Button(
                onClick = { prodViewModel.deleteDialogShown.value = true },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier.padding(start = 16.dp), // Espacement à gauche
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary) // Couleur de fond du bouton
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Supprimer un produit")
            }
        }
// Dialog for deleting a product
        if (prodViewModel.deleteDialogShown.value) {
            AlertDialog(
                onDismissRequest = {
                    prodViewModel.deleteDialogShown.value = false
                },
                title = { Text(text = "Supprimer un produit") },
                text = {
                    TextField(
                        value = prodViewModel.productId.value.toString(),
                        onValueChange = { prodViewModel.productId.value = it.toLong() },
                        label = { Text("ID du produit") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            println("click test supprimer")
                            println(prodViewModel.productId.value)
                            prodViewModel.deleteProd(prodViewModel.productId.value)
                            prodViewModel.deleteDialogShown.value = false
                        }
                    ) {
                        Text("Supprimer")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { prodViewModel.deleteDialogShown.value = false }
                    ) {
                        Text("Annuler")
                    }
                }
            )
        }

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


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(
    modifier: Modifier = Modifier,
    data: ProduitBean,
    onPictureClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable(onClick = onPictureClick)
    ) {
            // Produit
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

            ) {
                Text(
                    text = data.product_name,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = data.product_image,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = data.product_description,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = data.product_price.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

               // Spacer(modifier = Modifier.width(8.dp)) // Espace entre le texte de l'équipe 1 et le "-"
                //Spacer(modifier = Modifier.weight(1f)) // Espace flexible au milieu pour centrer le texte "-"
            }
    }

}
