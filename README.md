# **Zynetic Assignment**- Kotlin Multiplatform Project 🛍️
<img src="https://github.com/user-attachments/assets/1cff8dae-e63b-4e46-b8f5-5ca12e5afbc8" width="100" height="100"><br>
A cross-platform product catalog application built with Kotlin Multiplatform Mobile (KMM), showcasing shared business logic between platforms. The app demonstrates modern architecture patterns and efficient API integration using DummyJSON API.

## Screenshots 📱

[Place your screenshots here]

<table style="width:100%; text-align:center;">
  <tr>
    <th>Screen</th>
    <th>Screenshot</th>
  </tr>
  <tr>
    <td><strong>Main Screen</strong></td>
    <td><img src="https://github.com/user-attachments/assets/a9ce28ab-7c2b-4447-aa73-5ca612818ada" width="300" height="500"></td>
  </tr>
  <tr>
    <td><strong>Product Details</strong></td>
    <td><img src="https://github.com/user-attachments/assets/183a1665-4de9-4547-b015-1690cacf3aa1" width="300" height="500"></td>
  </tr>
  
</table>

## Features ✨

- **Product Listing:** Scrollable list with thumbnails and basic info
- **Detailed Views:** Complete product information with images
- **Custom Rating System:** Visual star rating implementation
- **Offline Support:** Network state handling with retry mechanism
- **Clean UI:** Material Design components and intuitive layout
- **Error Handling:** User-friendly error states and recovery options

## Tech Stack 🛠️

### Shared Code (KMM)
- Kotlin Multiplatform
- Ktor for networking
- Kotlinx.serialization
- Coroutines for async operations

### Android Specific
- Jetpack Compose
- Coil for image loading
- Android Architecture Components

## Project Structure 📁

```plaintext
MyKMMProject/
├── androidApp/
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           └── kotlin/org/zynetic/project/
│               └── MainActivity.kt
└── shared/
    └── src/
        └── commonMain/kotlin/org/zynetic/project/
            ├── model/
            │   └── Product.kt
            ├── network/
            │   └── ProductApi.kt
            ├── repository/
            │   └── ProductRepository.kt
            └── viewmodel/
                └── ProductViewModel.kt


Architecture Overview 🏗️
The project follows MVVM architecture with Clean Code principles:

Shared Components (KMM)
Product.kt: Data model for product information
ProductApi.kt: API interface and implementation for network calls
ProductRepository.kt: Repository pattern implementation
ProductViewModel.kt: Business logic and state management
Key Features
Product List Screen

Displays scrollable product list
Shows thumbnails and basic info
Implements loading states
Handles errors gracefully
Product Detail Screen

Shows complete product information
Displays product images
Custom rating display
Additional product details
Network Handling

Monitors connectivity
Shows offline mode
Implements retry mechanism
Error state management
Setup Requirements 🚀
Android Studio Arctic Fox or later
Kotlin Multiplatform Mobile plugin
JDK 11 or later
Android SDK 21 or later

Required Permissions 🔐
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Future Improvements 🔄
Implement caching
Add search functionality
Implement filters
Add animations
Add unit tests
Implement UI tests
Add pagination
