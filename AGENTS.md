# Booking App - Project Rules & Guidelines

## 🏗️ Architecture
- **Framework:** Jetpack Compose (Material 3)
- **Pattern:** MVVM (Model-ViewModel-ViewIntent/State)
- **Dependency Injection:** Hilt
- **Asynchronous:** Coroutines & StateFlow for UI state
- **UI State:** Sealed classes for `UiState` (Loading, Success, Error)

## 📁 Project Structure
- `com.bookingapp.data`: Data layer (Models, Repositories, API, DAO)
- `com.bookingapp.ui`: UI layer (Theme, Components, Screens, Navigation)
- `com.bookingapp.viewmodel`: ViewModels
- `com.bookingapp.utils`: Utility classes and Extensions

## 🏷️ Naming Conventions
- **ViewModels:** Must end with `ViewModel` suffix (e.g., `HomeViewModel`)
- **Composables:** PascalCase (e.g., `ListingCard`)
- **Repositories:** Must end with `Repository` suffix (e.g., `ListingRepository`)
- **Di Modules:** Must end with `Module` suffix (e.g., `NetworkModule`)

## 🎨 UI/UX Design
- Follow the "Clarity First" philosophy.
- Use skeleton loading states for data fetching.
- Implement rich aesthetics with vibrant colors and smooth animations.
- Prioritize visual excellence as per `BOOKING-APP.md`.

## 🤖 AI Integration
- `GeminiAgentManager`: Core class for Gemini SDK interactions.
- Use `StateFlow` to stream AI responses to the UI.

## 🧪 Testing
- **Unit Tests:** Mandatory for business logic and ViewModels.
- **Compose Previews:** Required for all reusable UI components.
- **UI Tests:** Use `compose-test-rule` for critical user flows.

## 🛠️ Tools & SDKs
- **Gemini SDK:** `com.google.ai.client.generativeai`
- **Coil:** For image loading.
- **Retrofit:** For networking.
- **Room:** For local persistence.
