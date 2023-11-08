package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.ProductUpdatedRequest
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {

        val productEntity = productRepository.findBySku(sku)

        if (productEntity!=null){
           return productEntity.toProductResponse();
        }
            return null;
    }

    fun getProductDetailsBySkus(skus: List<String>): List<ProductResponse> {
        return skus.mapNotNull { sku -> findProductBySku(sku) }
    }

    fun saveProduct(productRequest: ProductRequest): ProductResponse {
        val productEntity = ProductEntity(
                sku =  System.currentTimeMillis().toString(),
                name = productRequest.name,
                description = productRequest.description,
                price = productRequest.price,
                stockQuantity = productRequest.stockQuantity
        )
        val savedProduct = productRepository.save(productEntity)
        return savedProduct.toProductResponse()
    }

    fun updateProduct(sku: String, productUpdatedRequest: ProductUpdatedRequest): ProductResponse? {

        productRepository.findBySku(sku)?.let {
            val updatedProduct = it.copy(
                    sku = it.sku,
                    name = productUpdatedRequest.name ?: it.name,
                    description = productUpdatedRequest.description ?: it.description,
                    price = productUpdatedRequest.price ?: it.price,
                   stockQuantity= productUpdatedRequest.stockQuantity ?: it.stockQuantity ?: 0,
                    createdAt = it.createdAt,
                   updatedAt = ZonedDateTime.now(),
            )

            val updatedProt = productRepository.save(updatedProduct)
            return updatedProt.toProductResponse()
        }
        return null;
    }


}
