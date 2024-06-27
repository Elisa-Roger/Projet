package com.elisa.lucaskart.model

import com.elisa.lucaskart.model.ProductAPI.allProduct
import com.elisa.lucaskart.model.ProductAPI.createProd
import com.elisa.lucaskart.model.ProductAPI.deleteProduct
import com.elisa.lucaskart.model.ProductAPI.updateProduct
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

fun main() {
    deleteProduct(17)
}

object ProductAPI {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val gson = Gson() // Initialise l'objet Gson pour convertir des objets Java en JSON
    val client =
        OkHttpClient() // Initialise un client HTTP (OkHttpClient) pour effectuer des requêtes
    private const val URL_SERVER = "http://192.168.56.1:8080"
    //http://localhost:8080 mon adresse local  http://192.168.56.1:8080


    //une fonction qui envoie une requête POST à un serveur pour créer un nouveau produit en utilisant l'API REST.
    fun createProd(name: String, image: String, description: String, price: Int) {
        val res = sendPost(
            "$URL_SERVER/myproduct/createproduct",
            ProduitBean(null, name, image, description, price, product_status = true)
        )
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

    fun deleteProduct(productId: Long) {
        val res = sendDelete("$URL_SERVER/myproduct/deleteproduct/$productId")
        println(res)
        println("Produit <${productId}> supprimé")
    }

    // Une fonction qui envoie une requête PUT à un serveur pour mettre à jour un produit en utilisant l'API REST.
    fun updateProduct(productId: Long, updatedProd: ProduitBean) {
        val jsonData = gson.toJson(updatedProd)
        val res = sendPut("$URL_SERVER/myproduct/updateproduct/$productId", jsonData)
        println(res)
        println("Produit $productId mis à jour")
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

    fun sendDelete(url: String): String {
        println("url : $url")
        val client = OkHttpClient()
        val request = Request.Builder().url(url).delete().build()
        return try {
            val response: Response = client.newCall(request).execute()
            response.body.string() ?: "Empty response"
        } catch (e: IOException) {
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }
    // Fonction pour envoyer une requête HTTP PUT
    fun sendPut(url: String, data: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "PUT"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")
        val outputStream = connection.outputStream
        val writer = OutputStreamWriter(outputStream, "UTF-8")
        writer.write(data)
        writer.flush()
        val responseCode = connection.responseCode
        val responseData = if (responseCode == HttpURLConnection.HTTP_OK) {
            connection.inputStream.bufferedReader().use { it.readText() }
        } else {
            connection.errorStream.bufferedReader().use { it.readText() }
        }
        connection.disconnect()
        return responseData
    }
}
