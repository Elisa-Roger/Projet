package com.elisa.lucaskart.model

import com.elisa.lucaskart.model.ProductAPI.allProduct
import com.elisa.lucaskart.model.ProductAPI.createProd
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

fun main(){
 //createProd("softshell","url:joijoijoj","noire",16)
    allProduct()
}

object ProductAPI {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val gson = Gson() // Initialise l'objet Gson pour convertir des objets Java en JSON
    val client = OkHttpClient() // Initialise un client HTTP (OkHttpClient) pour effectuer des requêtes
    private const val URL_SERVER = "http://192.168.56.1:8080"
    //http://localhost:8080 mon adresse local  http://192.168.56.1:8080


    //une fonction qui envoie une requête POST à un serveur pour créer un nouveau produit en utilisant l'API REST.
    fun createProd(name: String, image: String, description: String, price: Int) {
        val res = sendPost("$URL_SERVER/myproduct/createproduct", ProduitBean(null, name, image, description, price, product_status = true))
        println("Données du produit")
        println(res)
    }
    //une fonction qui envoie une requête GET à un serveur pour récuperer l'ensemble des produits en utilisant l'API REST.
    fun allProduct(): List<ProduitBean> {
        var json = sendGet("$URL_SERVER/myproduct/allproduct")
        val test = gson.fromJson(json, Array<ProduitBean>::class.java).toList()
        println(test)
        return test
    }

/*
    fun add1Point(idMatch: Long, equipe: Int): MatchBean? {
        val responseJson = sendPostaddPoint("$URL_SERVER/mymatch/score", idMatch, equipe)
        println("Response from server : $responseJson")
        return gson.fromJson(responseJson, MatchBean::class.java)
    }
    fun stock(idProd: Int){
        sendPost("$URL_SERVER/statusover",idProd)
    }
    fun finishMatch(idMatch: Long){
        sendPost("$URL_SERVER/mymatch/statusover",idMatch)
    }
    fun load7DayzMatch(): List<MatchBean> {
        var json = sendGet("$URL_SERVER/mymatch/7dayz")
        val test = gson.fromJson(json, Array<MatchBean>::class.java).toList()
        println(test)
        return test

    }
*/

    fun sendGet(url: String): String {
        println("url : $url")
        val request = Request.Builder().url(url).get().build()
        return client.newCall(request).execute().use { //it:Response
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            it.body.string()
        }
    }
    fun sendPost(url: String, toSend: Any?): String {
        println("url : $url")
        val json = gson.toJson(toSend)
        val body = json.toRequestBody(MEDIA_TYPE_JSON)
        val request = Request.Builder().url(url).post(body).build()
        return client.newCall(request).execute().use {
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            it.body.string()
        }
    }
}
