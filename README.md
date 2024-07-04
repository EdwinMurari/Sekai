# Sekai - Your Anime Streaming Companion

Sekai is a modern Android app designed to provide a seamless experience for discovering and tracking anime, available for both mobile and TV devices.  Built with a focus on clean architecture, testability, and a delightful user experience, this project demonstrates my skills and experience as a Senior Android Engineer.

## Features ✨

- **Discover Trending and Popular Anime:** Explore curated lists of the latest and greatest anime.
- **Search for Anime:** Quickly find your favorites or explore new titles using powerful search and filtering capabilities.
- **Get Detailed Information:** Read synopses, track episodes, explore genres, tags, and more.
- **(Coming Soon) Stream Anime:** Watch your favorite anime directly within the app.
- **(Coming Soon) Manage Your List:** Keep track of what you're watching.

## Configurations 📺📱

- **TV:** In progress! Sekai for TV will bring a big-screen anime experience to your living room.
- **Mobile:** Coming soon! The mobile version of Sekai will be your pocket-sized anime companion.

## Built With 🛠️

- **Kotlin:** Modern and concise language for Android development.
- **Jetpack Compose:** Declarative UI framework for building beautiful and efficient UIs.
- **Hilt:** Dependency injection framework for simplified and type-safe dependency management.
- **Coroutines:** For efficient asynchronous programming.
- **Apollo GraphQL:** For querying the **AniList** and **Kitsu** APIs.
- **Retrofit:** For networking with the **Jikan** REST API.
- **Paging 3:**  For efficient loading and display of large datasets.
- **MockK:** Powerful mocking framework for Kotlin, used for testing.
- **JUnit:** Industry-standard testing framework.

## Architecture 🏗️

The project follows a modular architecture to improve code organization and maintainability while adhering to clean architecture principles:

- **UI Layer (Jetpack Compose):** Displays data and reacts to user interactions.
- **Domain Layer (Kotlin):** Contains business logic and use cases, independent of any specific Android component.
- **Data Layer (Repositories, Data Sources):** Provides access to data sources (network, database) through repositories.

**Modules:**

- **:app:** Main application module.
- **:tv:** Module containing TV-specific UI components and logic.
- **:mobile:** (Coming soon) Module for the mobile/phone version of the app.
- **:network:** Handles all network communication and API interactions.
  - **:network:anilist:** Module for interacting with the AniList API (GraphQL).
  - **:network:kitsu:** Module for interacting with the **Kitsu API (GraphQL).**
  - **:network:jikan:** Module for interacting with the **Jikan API (REST).**
- **:data:** Contains the data layer, including repositories and data sources.
- **:common:** Contains code shared between modules (like utility functions or base classes).
- **:design-system:** (Coming Soon) Module to house reusable UI components and theming.

## Technical Highlights 🏆

- **Modern Android Architecture:** Implemented using the official Android architecture guidelines, ensuring separation of concerns, testability, and maintainability.
- **Jetpack Compose:** A beautiful and responsive UI using Jetpack Compose, showcasing expertise in modern Android UI development.
- **Dependency Injection with Hilt:** Hilt provides a robust and type-safe dependency injection framework, leading to a more maintainable and testable codebase.
- **Asynchronous Programming with Coroutines:** Kotlin Coroutines are used for efficient asynchronous operations, resulting in a smooth and responsive user experience.
- **Network Communication:**  Retrofit is integrated for type-safe network requests to interact with REST APIs (Kitsu & Jikan).
- **GraphQL with Apollo:** Apollo GraphQL is utilized to efficiently query and consume data from the AniList and Kitsu(Episodes) API.
- **Paging 3:**  Efficiently loads and displays large datasets of anime, providing a seamless user experience.
- **Rigorous Testing:**
  - JUnit and MockK are employed for comprehensive unit testing of the data and domain layers, ensuring code quality and reliability.
  - (Future) Plan to integrate UI tests using Jetpack Compose's testing framework to guarantee a flawless user interface.
- **Network Communication:**
  - Retrofit is integrated for type-safe network requests to interact with the **Jikan REST API.**
  - Apollo GraphQL is utilized to efficiently query and consume data from the **AniList and Kitsu APIs.**

## Getting Started 🚀

1. Clone this repository: `git clone https://github.com/EdwinMurari/Sekai.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Connect with Me 🤝

https://www.linkedin.com/in/edwin-murari-4a6438163/