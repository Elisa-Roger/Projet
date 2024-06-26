package org.example.lucas_kart_serveur.apirest

import org.example.lucas_kart_serveur.model.ProduitBean
import org.example.lucas_kart_serveur.model.ProduitService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/myproduct")
//toutes les méthodes de cette classe géreront les requêtes HTTP dont l'URL commence par "/myproduct"
class MyprodAPI(val prodService: ProduitService) {

    @GetMapping("/test")
    fun testHello() {
        println("hello la team")
    }

    // Une url pour créer un produit
    //http://localhost:8080/myproduct/createproduct
    @PostMapping("/createproduct") // répondra aux requêtes POST envoyées à l'URL "/myproduct/createproduct"
    fun createProd(@RequestBody prod: ProduitBean) {
        println("/save prod = $prod")
        prodService.createProduct(
            name = prod.product_name,
            image = prod.product_image,
            description = prod.product_description,
            price = prod.product_price
        )
    }

    //http://localhost:8080/myproduct/allproduct
    //Récupère la liste de TOUT les produits
    @GetMapping("/allproduct")
    fun allProduct(): List<ProduitBean> {
        println("Appel de la fonction /allproduct")
        return prodService.findAllProduct()
    }

    //  Une url pour supprimer un produit
    //http://localhost:8080/myproduct/deleteproduct/{productId}
    @DeleteMapping("/deleteproduct/{productId}")
    fun deleteProd(@PathVariable productId: Long): ResponseEntity<String> {
        try {
            prodService.deleteProduct(productId)
            return ResponseEntity.ok("Le produit avec l'identifiant $productId a été supprimé avec succès")
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    // Une url pour modifier un produit
    //http://localhost:8080/myproduct/updateproduct/{productId}
    @PutMapping("/updateproduct/{productId}")
    fun updateProd(@PathVariable productId: Long, @RequestBody updatedProd: ProduitBean): ResponseEntity<String> {
        return try {
            // Assurez-vous que l'ID du produit dans updatedProd est le même que celui passé dans l'URL
            if (updatedProd.product_id != productId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("L'ID du produit dans le corps de la requête ne correspond pas à l'ID dans l'URL.")
            }
            // Appeler le service pour mettre à jour le produit
            prodService.updateProduct(productId, updatedProd)
            // Retourner une réponse réussie
            ResponseEntity.ok("Le produit avec l'identifiant $productId a été mis à jour avec succès")
        } catch (e: Exception) {
            // En cas d'erreur, retourner une réponse d'erreur avec le message de l'exception
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    /*
        //  Une url pour ajouter un produit au panier
        //http://localhost:8080/myproduct/addproduct
        @PostMapping("/addproduct")
        fun add1Product(@RequestParam idProd: Long, equipe:Int):ProduitBean{
            //println("/score : ${matchService.add1Point(idMatch, equipe)}")
            return prodService.add1Point(idProd, equipe)
        }



        //Déclarer le status du produis (en stock ou non disponible)
        //http://localhost:8080/myproduct/status
        @GetMapping("/status")
        fun setStatusOver(@RequestBody idProd: Long) {
            return prodService.setStatusOver(idProd)
        }
    */
}