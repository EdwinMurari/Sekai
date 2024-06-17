# Sekai - Your Anime Streaming Companion

Sekai is a modern Android app designed to provide a seamless experience for discovering and tracking anime. Built with a focus on clean architecture, testability, and a delightful user experience, this project demonstrates my skills and experience as a Senior Android Engineer.

## Features ✨

- **Discover Trending and Popular Anime:** Explore curated lists of the latest and greatest anime.
- **(Coming Soon) Search for Anime:** Quickly find your favorites or explore new titles.
- **(Coming Soon) Get Detailed Information:**  Read synopses, track episodes, and more.
- **(Coming Soon) Stream Anime:**  Watch your favorite anime directly within the app.
- **(Coming Soon) Manage Your List:**  Keep track of what you're watching or reading.

## Built With 🛠️

- **Kotlin:** Modern and concise language for Android development.
- **Jetpack Compose:**  Declarative UI framework for building beautiful and efficient UIs.
- **Hilt:** Dependency injection framework for simplified and type-safe dependency management.
- **Coroutines:**  For efficient asynchronous programming.
- **Apollo GraphQL:**  For querying the AniList API.
- **MockK:** Powerful mocking framework for Kotlin, used for testing.
- **JUnit:**  Industry-standard testing framework.

## Architecture 🏗️

The project follows a modular architecture to improve code organization and maintainability while adhering to clean architecture principles:

- **UI Layer (Jetpack Compose):** Displays data and reacts to user interactions.
- **Domain Layer (Kotlin):** Contains business logic and use cases, independent of any specific Android component.
- **Data Layer (Repositories, Data Sources):** Provides access to data sources (network, database) through repositories.

**Modules:**

- **:app:**  Main application module.
- **:tv:**  Module containing TV-specific UI components and logic.
- **:mobile:** (Coming soon) Module for the mobile/phone version of the app.
- **:network:**  Handles all network communication and API interactions.
- **:data:**  Contains the data layer, including repositories and data sources.
- **:design-system:** (Coming soon) Module to house reusable UI components and theming.

## Technical Highlights 🏆

- **Modern Android Architecture:** Implemented using the official Android architecture guidelines ([https://developer.android.com/topic/architecture/intro](https://developer.android.com/topic/architecture/intro)), ensuring separation of concerns, testability, and maintainability.
- **Jetpack Compose:**  Crafted a beautiful and responsive UI using Jetpack Compose, showcasing expertise in modern Android UI development.
- **Dependency Injection with Hilt:**  Leveraged Hilt for a robust and type-safe dependency injection framework, leading to a more maintainable and testable codebase.
- **Asynchronous Programming with Coroutines:** Utilized Kotlin Coroutines for efficient asynchronous operations, resulting in a smooth and responsive user experience.
- **Network Communication:**  Integrated Retrofit for type-safe network requests to interact with the AniList API.
- **GraphQL with Apollo:**  Utilized Apollo GraphQL to efficiently query and consume data from the AniList API.
- **Rigorous Testing:**
    - Employed JUnit and MockK for comprehensive unit testing of the data and domain layers, ensuring code quality and reliability.
    - (Future) Plan to integrate UI tests using Jetpack Compose's testing framework to guarantee a flawless user interface.

## Getting Started 🚀

1. Clone this repository: `git clone https://github.com/EdwinMurari/Sekai.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Connect with Me 🤝

https://www.linkedin.com/in/edwin-murari-4a6438163/