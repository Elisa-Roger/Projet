package com.elisa.lucaskart.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elisa.lucaskart.model.ProductAPI
import com.elisa.lucaskart.model.ProduitBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

// Définition de la classe ViewModel pour gérer les données liées aux produits.
class MainView : ViewModel() {

    // Déclaration de listes et variables observables.
    // Ces variables sont observées par l'interface utilisateur (UI) et toute modification déclenchera une mise à jour.

    val searchText = mutableStateOf("")
    val myList = mutableStateListOf<ProduitBean>()
    var name = mutableStateOf("")
    var image = mutableStateOf("")
    var description = mutableStateOf("")
    var price = mutableStateOf(0)
    val dialogShown = mutableStateOf(false)
    val editDialogShown = mutableStateOf(false)
    val deleteDialogShown = mutableStateOf(false)
    val productId = mutableStateOf(0)
    val runInProgress = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    //fun filterList() = myList.filter { it.title.contains(searchText.value, true) }

    fun uploadSearchText(newText: String) {
        searchText.value = newText
    }

    fun loadList() {
        runInProgress.value = true
        errorMessage.value = ""
        // myList.clear()
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val newData = ProductAPI.allProduct()
                launch(Dispatchers.Main) {
                    myList.addAll(newData)
                    println("Load list")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    errorMessage.value = "Erreur : ${e.message}"
                }
            }
        }
    }

    fun createProd(nom: String, image: String, description: String, price: Int) {
        viewModelScope.launch(Dispatchers.Default) {// Cela permet d'exécuter le bloc de code dans un thread non principal.
            // Lance une coroutine dans le contexte du ViewModelScope sur le dispatcher par défaut pour des opérations non bloquantes.
            try {
                println("Création d'un produit")
                ProductAPI.createProd(
                    name = nom,
                    image = image,
                    description = description,
                    price = price
                ) //Appel d'une fonction createProd de l'API ProductAPI
                val newProd = ProduitBean(
                    product_name = nom,
                    product_image = image,
                    product_description = description,
                    product_price = price
                ) //Création d'un nouvel objet ProduitBean représentant le produit
                launch(Dispatchers.Main) {// Bascule vers le thread principal pour effectuer des modifications de l'UI.
                    myList.add(newProd) //ajoute le nouveau produit à une liste myList
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun deleteProd(productId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                println("Suppression du produit avec ID : $productId")
                ProductAPI.deleteProduct(productId)
                launch(Dispatchers.Main) {
                    val index = myList.indexOfFirst { it.product_id == productId.toInt() }
                    if (index != -1) {
                        myList.removeAt(index)
                    }
                    println("Produit supprimé de la liste")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    errorMessage.value = "Erreur : ${e.message}"
                }
            }
        }
    }

    fun updateProd(productId: Long, updatedProd: ProduitBean) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                println("Modification du produit avec ID : $productId")
                // Appel de la fonction d'API pour mettre à jour le produit
                ProductAPI.updateProduct(productId, updatedProd)
                // Mettre à jour la liste locale si nécessaire
                launch(Dispatchers.Main) {
                    val index = myList.indexOfFirst { it.product_id == productId.toInt() }
                    if (index != -1) {
                        // Remplacer l'ancien produit par le produit mis à jour
                        myList[index] = updatedProd
                    }
                    println("Produit mis à jour dans la liste")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    errorMessage.value = "Erreur : ${e.message}"
                }
            }
        }
    }
}


/*
    fun loadData() {//Simulation de chargement de donnée
        myList.clear()

        //Une tache en cours
        runInProgress.value = true
        errorMessage.value = ""

        viewModelScope.launch(Dispatchers.Default) {
            try {
                //Controle paramètre
                if(searchText.value.length < 3) {
                    throw Exception("Il faut au moins 3 caractères")
                }

                //Requête
                val listWeather = WeatherAPI.loadWeatherAround(searchText.value)

                //Controle de résultat
                if(listWeather.isEmpty()) {
                    throw Exception("Aucun résultat")
                }

                //Traitement de résultat
                val listPicture = listWeather.map { weather ->
                    PictureBean(
                        weather.id,
                        weather.weather.getOrNull(0)?.icon ?: "",
                        weather.name,
                        "Il fait ${weather.main.temp}°"
                    )
                }
                //Retourner sur le Thread principale pour l'affichage
                launch(Dispatchers.Main) {
                    //J'ajoute tous les éléments à myList qui est observé
                    myList.addAll(listPicture)
                    //Tache terminée
                    runInProgress.value = false
                }
            }
            catch(e:Exception) {
                e.printStackTrace() //permet d'afficher dans les logs la raison de l'exception
                //Retourner sur le Thread principale pour l'affichage
                launch(Dispatchers.Main) {
                    //J'ajoute tous les éléments à myList qui est observé
                    errorMessage.value = "Une erreur est survenue : ${e.message}"
                    //Tache terminée
                    runInProgress.value = false
                }
            }
        }
   }
}
*/