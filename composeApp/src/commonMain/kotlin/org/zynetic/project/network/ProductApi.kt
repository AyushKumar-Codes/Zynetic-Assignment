package org.zynetic.project.network




import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.zynetic.project.model.Product

interface ProductApi {
    suspend fun getProduct(id: Int): Product
}

class ApiClientImpl : ProductApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override suspend fun getProduct(id: Int): Product {
        return try {
            client.get("https://dummyjson.com/products/$id").body()
        } catch (e: Exception) {
            throw Exception("Failed to fetch product: ${e.message}")
        }
    }
}