package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal

data class ProductResponse(
    val sku: String,
    val name: String,
    val description: String,
    val stockQuantity:Int,
    val price: BigDecimal
) {
    companion object {
        fun ProductEntity.toProductResponse() = ProductResponse(
            sku = this.sku,
            name = this.name,
            description = this.description ?: "",
            stockQuantity=this.stockQuantity?:0,
            price =this. price
        )
    }
}
