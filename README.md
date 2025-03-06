# HCS-IDN Android Assessment Project: GitHub User Search App

## Project Overview
This project is a **GitHub User Search App** that enables users to search for GitHub users, view search results in a list, and see detailed information about a selected user. The app utilizes the **GitHub API** for fetching user data and **Room** for local data persistence. The project follows **Clean Architecture** principles, ensuring modular, maintainable, and scalable code.

## Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Networking:** Retrofit, OkHttp
- **Local Storage:** Room Database
- **Dependency Injection:** Hilt
- **Concurrency:** Kotlin Coroutines
- **Image Loading:** Glide
- **Testing:** JUnit, MockK, Turbine, Kotlin Coroutines Test
- **Debugging:** Chucker
- **Dependency Versioning:** Version Catalog
- **Background Processing:** Work Manager

## Features
### **Must Have**
1. **Search Screen**
    - Users can search for GitHub users.
    - Uses Retrofit and OkHttp to fetch data from the GitHub API.
    - Displays search results in a **RecyclerView**.

2. **User List Screen**
    - Displays a list of GitHub users with their usernames and avatars.
    - Uses **Glide** for image loading.

3. **User Detail Screen**
    - Displays detailed information about a selected user.
    - Includes **username, avatar, bio, and other relevant details**.

4. **Local Data Persistence**
    - Uses **Room** for local storage to persist GitHub users.
    - Implements **data caching and retrieval**.

5. **Clean Architecture**
    - Follows the **MVVM pattern**.
    - Separates concerns into **Domain, Data, and Presentation layers**.
    - Uses **Hilt** for dependency injection.

6. **Concurrency**
    - Uses **Kotlin Coroutines** for asynchronous operations.
    - Utilizes **Jetpack libraries** (LiveData, ViewModel) to manage UI-related data.

7. **Git History**
    - Maintains a **clean and descriptive Git history**.

### **Nice to Have**
1. **Unit Testing & UI Testing**
    - Implements unit tests and UI tests using **JUnit, Espresso, and Mockito**.

2. **Debugging Support**
    - Integrates **Chucker** for API request/response debugging.

3. **Dependency Versioning**
    - Uses **Version Catalog** for managing dependencies.

4. **Background Processing**
    - Implements **Work Manager** for handling background tasks.

5. **JSON Parsing**
    - Uses **Moshi** for JSON parsing.

## Installation & Setup
### **Prerequisites**
- Android Studio (latest version recommended)
- Git and GitHub account

### **Steps to Build & Run the Project**
1. **Clone the repository**:
   ```sh
   git clone https://github.com/Envy-7z/Githubsearch-Wisnu
   ```

2. **Open the project in Android Studio**.
3. **Build and Run the app**:
    - Connect a physical device or use an emulator.
    - Click on "Run" in Android Studio.

## Architectural Decisions
- **MVVM (Model-View-ViewModel)** was chosen for better separation of concerns and testability.
- **Hilt** for dependency injection to improve modularity.
- **Room** for local persistence to enhance offline capabilities.
- **Kotlin Coroutines** for better async handling.

## Challenges & Improvements
- **API Rate Limiting:** Implemented caching using Room to reduce API calls.
- **Error Handling:** Implemented proper exception handling and user-friendly error messages.
---
### 🔥 Happy Coding & Good Luck! 🚀

