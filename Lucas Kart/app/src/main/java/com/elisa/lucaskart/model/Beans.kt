package com.elisa.lucaskart.model


const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""

//data class PictureBean(val id:Int, val url: String, val title: String, val longText: String)
data class ProduitBean(
    var product_id:Int? = null,
    var product_name: String = "Produit",
    val product_image: String = "R.drawable.addimg",
    var product_description: String = "description vide",
    var product_price: Int = 0,
    var product_status: Boolean = true)

//jeu de donnée
val productList = arrayListOf(ProduitBean(1, "Softshell","https://picsum.photos/200", LONG_TEXT, 50, true),
)
