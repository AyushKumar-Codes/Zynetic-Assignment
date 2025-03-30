package org.zynetic.project.viewmodel



import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.zynetic.project.model.Product
import org.zynetic.project.repository.ProductRepository

sealed class ProductState {
    object Loading : ProductState()
    data class Success(val product: Product) : ProductState()
    data class Error(val message: String) : ProductState()
}

class ProductViewModel(private val repository: ProductRepository) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> = _productState.asStateFlow()

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            repository.getProduct(id)
                .onSuccess { product ->
                    _productState.value = ProductState.Success(product)
                }
                .onFailure { error ->
                    _productState.value = ProductState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun clear() {
        viewModelScope.cancel()
    }
}