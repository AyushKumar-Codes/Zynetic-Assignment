package org.zynetic.project.repository

import org.zynetic.project.model.Product
import org.zynetic.project.network.ProductApi


interface ProductRepository {
    suspend fun getProduct(id: Int): Result<Product>
}

class ProductRepositoryImpl(private val api: ProductApi) : ProductRepository {
    override suspend fun getProduct(id: Int): Result<Product> {
        return try {
            val product = api.getProduct(id)
            println("Product fetched successfully: $product") // Debug log
            Result.success(product)
        } catch (e: Exception) {
            println("Error fetching product: ${e.message}") // Debug log
            Result.failure(e)
        }
    }
}