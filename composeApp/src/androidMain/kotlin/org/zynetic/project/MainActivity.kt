package org.zynetic.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.zynetic.project.model.Product
import org.zynetic.project.network.ApiClientImpl
import org.zynetic.project.repository.ProductRepositoryImpl
import kotlin.math.floor
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var networkUtils: NetworkUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkUtils = NetworkUtils(this)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isNetworkAvailable by remember { mutableStateOf(networkUtils.isNetworkAvailable()) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            isNetworkAvailable = networkUtils.isNetworkAvailable()
                            delay(1000) // Check every second
                        }
                    }

                    if (!isNetworkAvailable) {
                        NoInternetConnection {
                            isNetworkAvailable = networkUtils.isNetworkAvailable()
                        }
                    } else {
                        ProductApp()
                    }
                }
            }
        }
    }
}

@Composable
fun NoInternetConnection(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Internet Connection",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please check your internet connection and try again",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun ProductApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productList") {
        composable("productList") {
            ProductListScreen(
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }
        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: return@composable
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun ProductListScreen(onProductClick: (Int) -> Unit) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessages by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }

    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClientImpl() }
    val repository = remember { ProductRepositoryImpl(apiClient) }

    LaunchedEffect(Unit) {
        isLoading = true
        for (id in 1..194) {
            coroutineScope.launch {
                repository.getProduct(id)
                    .onSuccess { product ->
                        products = (products + product).sortedBy { it.id }
                        errorMessages = errorMessages - id
                    }
                    .onFailure { error ->
                        errorMessages = errorMessages + (id to (error.message ?: "Unknown error"))
                    }
            }
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isLoading && products.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            items(products.sortedBy { it.id }) { product ->
                ProductListItem(
                    product = product,
                    onClick = { onProductClick(product.id) }
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val filledStars = floor(rating).toInt()
        val hasHalfStar = rating - filledStars >= 0.5

        repeat(5) { index ->
            when {
                index < filledStars -> FilledStar()
                index == filledStars && hasHalfStar -> HalfStar()
                else -> EmptyStar()
            }
        }

        Text(
            text = String.format("%.1f", rating),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun FilledStar() {
    Text(
        text = "★",
        color = Color(0xFFFFD700)
    )
}

@Composable
private fun HalfStar() {
    Text(
        text = "⭐",
        color = Color(0xFFFFD700)
    )
}

@Composable
private fun EmptyStar() {
    Text(
        text = "☆",
        color = Color.Gray
    )
}

@Composable
fun ProductListItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Product Image
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            // Product Details
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(4.dp))

                RatingBar(
                    rating = product.rating,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2
                )

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit
) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClientImpl() }
    val repository = remember { ProductRepositoryImpl(apiClient) }

    LaunchedEffect(productId) {
        isLoading = true
        repository.getProduct(productId)
            .onSuccess {
                product = it
                error = null
            }
            .onFailure {
                error = it.message
            }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                product != null -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            // Product Image
                            AsyncImage(
                                model = product!!.thumbnail,
                                contentDescription = product!!.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = product!!.title,
                                style = MaterialTheme.typography.h5
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "$${product!!.price}",
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.primary
                                        )
                                        RatingBar(rating = product!!.rating)
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Category: ${product!!.category}",
                                        style = MaterialTheme.typography.body1
                                    )
                                    Text(
                                        text = "Brand: ${product!!.brand}",
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Description",
                                        style = MaterialTheme.typography.h6
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = product!!.description,
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp
                            ) {
                                DetailedRatingBar(product!!.rating)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailedRatingBar(rating: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Rating & Reviews",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = String.format("%.1f", rating),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                RatingBar(
                    rating = rating,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Based on product rating",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}