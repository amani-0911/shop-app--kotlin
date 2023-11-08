package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val description: String,
    val stockQuantity: Int?,
    val price: BigDecimal
) {
    companion object {
        fun ProductEntity.toProductResponse() = ProductRequest(
            name = name,
            stockQuantity =stockQuantity ,
            description = description ?: "",
            price = price
        )
    }
}
