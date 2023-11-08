package de.imedia24.shop.controller

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductUpdatedRequest
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import java.math.BigDecimal

@WebMvcTest(ProductController::class)
@AutoConfigureMockMvc
class ProductControllerTest {


    @MockBean
    private lateinit var productService: ProductService

    @MockBean
    private lateinit var productRepository: ProductRepository

    @MockBean
    private lateinit var productController:ProductController;
    @BeforeEach
    fun setup() {
        productService = ProductService(productRepository)
        productController=ProductController(productService)
    }
    @Test
    @DisplayName("Should Find Product By SKU")
    fun shouldFindProductBySku() {
        // Arrange
        val sku = "12345"
        val productName = "Test Product"
        val productDescription = "Test Description"
        val stockQuantity=2
        val productPrice = BigDecimal("19.99")

        val entity = ProductEntity(sku,stockQuantity, productName, productDescription, productPrice)

        `when`(productRepository.findBySku(sku)).thenReturn(entity)

        // Act
        val actual = productController.findProductsBySku(sku)

        // Assert the response
        org.junit.jupiter.api.Assertions.assertNotNull(actual)
        org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.OK, actual.statusCode)
        org.junit.jupiter.api.Assertions.assertNotNull(actual.body)

        // Assert the response body
        org.junit.jupiter.api.Assertions.assertEquals(sku, actual.body!!.sku)
        org.junit.jupiter.api.Assertions.assertEquals(productName, actual.body!!.name)
        org.junit.jupiter.api.Assertions.assertEquals(productDescription, actual.body!!.description)
        org.junit.jupiter.api.Assertions.assertEquals(productPrice, actual.body!!.price)
    }
    @Test
    @DisplayName("Should Update Product When making POST request to endpoint - /products/{sku}")
    fun shouldUpdateProduct() {
        val sku = "12345"
        val updatedProductName = "Updated Product Name"
        val updatedProductDescription = "Updated Product Description"
        val stockQuantity=1
        val updatedProductPrice = BigDecimal("29.99")

        val entity = ProductEntity(sku,stockQuantity, updatedProductName, updatedProductDescription, updatedProductPrice)

        val request = ProductUpdatedRequest(updatedProductName, updatedProductDescription, updatedProductPrice,stockQuantity)


        `when`(productRepository.save(any())).thenReturn(entity)
        `when`(productRepository.findBySku(sku)).thenReturn(entity)


        var actual = productController.updateProduct(sku, request);
        // Assert the response
        org.junit.jupiter.api.Assertions.assertNotNull(actual)
        org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.OK, actual.statusCode)
        org.junit.jupiter.api.Assertions.assertNotNull(actual.body)

        // Assert the response body
        org.junit.jupiter.api.Assertions.assertEquals(sku, actual.body!!.sku)
        org.junit.jupiter.api.Assertions.assertEquals(updatedProductName, actual.body!!.name)
        org.junit.jupiter.api.Assertions.assertEquals(updatedProductDescription, actual.body!!.description)
        org.junit.jupiter.api.Assertions.assertEquals(updatedProductPrice, actual.body!!.price)
    }
}
