package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductUpdatedRequest
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @GetMapping("/products")
    fun getProductDetailsBySkus(@RequestParam skus: List<String>): ResponseEntity<List<ProductResponse>> {
        return  ResponseEntity.ok(productService.getProductDetailsBySkus(skus));

    }

    @PostMapping("/products")
    fun saveProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.saveProduct(productRequest));
    }
    @PostMapping("/products/{sku}")
    fun updateProduct(
            @PathVariable sku: String,
            @RequestBody productUpdatedRequest: ProductUpdatedRequest
    ): ResponseEntity<ProductResponse> {
        logger.info("update for product $sku")
        val updatedProduct = productService.updateProduct(sku, productUpdatedRequest)
        return if(updatedProduct == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(updatedProduct)
        }
    }
}
