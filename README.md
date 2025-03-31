# **Zynetic Assignment** - Kotlin Multiplatform Project 🛍️
<img src="https://github.com/user-attachments/assets/1cff8dae-e63b-4e46-b8f5-5ca12e5afbc8 " width="100" height="100"><br>

A cross-platform product catalog application built with **Kotlin Multiplatform Mobile (KMM)**, showcasing shared business logic between platforms. The app demonstrates **modern architecture patterns** and **efficient API integration** using the [DummyJSON API](https://dummyjson.com/).

---

## 📱 Screenshots

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

---

## ✨ Features

- **Product Listing:** Scrollable list with thumbnails and basic info.
- **Detailed Views:** Complete product information with images.
- **Custom Rating System:** Visual star rating implementation.
- **Offline Support:** Network state handling with retry mechanism.
- **Clean UI:** Material Design components and intuitive layout.
- **Error Handling:** User-friendly error states and recovery options.

---

## 🛠️ Tech Stack

### **Shared Code (KMM)**
- **Kotlin Multiplatform**
- **Ktor** for networking
- **Kotlinx.serialization** for data handling
- **Coroutines** for async operations

### **Android Specific**
- **Jetpack Compose** for UI
- **Coil** for image loading
- **Android Architecture Components**

---

## 📁 Project Structure

```
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
```

---

## 🏗️ Architecture Overview

The project follows **MVVM architecture** with **Clean Code** principles:

- **Shared Components (KMM)**
    - `Product.kt`: Data model for product information
    - `ProductApi.kt`: API interface and implementation for network calls
    - `ProductRepository.kt`: Repository pattern implementation
    - `ProductViewModel.kt`: Business logic and state management

### **Key Features**

#### **📜 Product List Screen**
- Displays a scrollable product list.
- Shows thumbnails and basic product info.
- Implements loading states.
- Handles errors gracefully.

#### **📄 Product Detail Screen**
- Displays complete product information.
- Shows product images.
- Implements a custom rating display.
- Provides additional product details.

#### **🌐 Network Handling**
- Monitors connectivity.
- Supports offline mode.
- Implements a retry mechanism.
- Manages error states efficiently.

---
## 🚀 Setup and Running the Project

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin Multiplatform Mobile plugin
- JDK 11 or later
- Android SDK 21 or later

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/zynetic-assignment.git
cd zynetic-assignment
```

### Step 2: Open in Android Studio
1. Launch Android Studio.
2. Select **"Open an existing project"**.
3. Navigate to the cloned repository and click **"Open"**.

### Step 3: Configure the Project
1. Wait for **Gradle sync** to complete.
2. Ensure all dependencies are downloaded.
3. Verify **SDK location** in `local.properties`.

### Step 4: Build the Project
```bash
./gradlew build
```

### Step 5: Run the Application
1. Select an Android device/emulator.
2. Click the **"Run"** button (▶️) or press **Shift + F10**.
3. Wait for the app to install and launch.

### Troubleshooting
#### If **Gradle sync fails**:
- Go to **File -> Invalidate Caches / Restart**.
- Verify your **internet connection**.
- Update **Gradle version** if needed.

#### If **build fails**:
- Check **SDK installation**.
- Verify **JDK version**.
- Clean and rebuild the project (`Build -> Clean Project` then `Build -> Rebuild Project`).


## 🔄 Future Improvements

- Implement caching for offline support.
- Add search functionality.
- Implement product filters.
- Enhance UI with animations.
- Add unit tests.
- Implement UI tests.
- Introduce pagination for better performance.

---

### 📌 **Contributions & Feedback**

We welcome contributions! Feel free to fork the project, submit issues, or create pull requests. If you have any suggestions or feedback, let us know!

---
