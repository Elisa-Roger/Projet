package org.example.lucas_kart_serveur.model
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@Entity
@Table(name = "products")

data class ProduitBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var product_id:Long? = null,
    var product_name: String? = null,
    val product_image: String? = "",
    var product_description: String? = "",
    var product_price: Int? = 0,
    var product_status: Boolean = false)



@Repository
interface ProduitRepository : JpaRepository<ProduitBean, Long>

    //fun findAllByDateAfterOrderByDateDesc(date: Long):  List<ProduitBean>
    //fun findByid(id: Int):ProduitBean



@Service
class ProduitService(val prodRep:ProduitRepository) {

    //Créer un produit avec un nom, une description et un prix
    fun createProduct(name:String?, image:String?, description:String?, price:Int?) : ProduitBean {
        if(name.isNullOrBlank()){
            throw Exception("Il manque le nom du produit")
        }
        if(description.isNullOrBlank()){
            throw Exception("Il manque la description du produit")
        }
        if(price == null || price == 0){
            throw Exception("Il manque le prix du produit")
        }

        val product = ProduitBean(null, name,image, description, price, product_status = true)
        prodRep.save(product)
        return product
    }

    fun findAllProduct(): List<ProduitBean> {
        val allProduct = prodRep.findAll()
        return allProduct
    }
    fun deleteProduct(productId: Long) {
        // Vérifier si le produit existe avant de le supprimer
        if (!prodRep.existsById(productId)) {
            throw Exception("Le produit avec l'identifiant $productId n'existe pas")
        }
        // Supprimer le produit
        prodRep.deleteById(productId)
    }


/*

    //Supprime 1 produit au panier
    fun delete1Product(idProd: Long, equipe:Int):ProduitBean{
        var prod = prodRep.findByid(idProd)
        prod.score_equipe1--
        prodRep.delete(prod)
        return prod
    }

    //Produit non disponible
    fun setStatusOver(idProd: Long) {
        val prod=prodRep.findByid(idProd)
        //prod.status = !prod.status
         if (prod.status == true) prod.status=false
        else prod.status=true
        //println("statusover : ${prod.status}" )
    }
*/
}
