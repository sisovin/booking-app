# Booking App

A modern Android application for discovering and booking unique accommodations worldwide, built with Jetpack Compose and powered by Google Gemini AI for intelligent, conversational search experiences.

## 🌟 Features

### 🤖 AI-Powered Search
- **Conversational Interface**: Chat with Gemini AI to find perfect stays using natural language
- **Smart Recommendations**: Get personalized suggestions based on preferences, budget, and location
- **Voice Search**: Speak your requirements for hands-free booking
- **Contextual Assistance**: Ask questions about listings, compare options, and get travel advice

### 🏠 Accommodation Discovery
- **Diverse Listings**: Villas, cabins, apartments, and unique stays
- **Rich Media**: High-quality photos, 360° views, and virtual tours
- **Detailed Information**: Amenities, policies, host details, and reviews
- **Interactive Maps**: Location-based discovery and navigation

### 💳 Seamless Booking
- **Instant Confirmation**: Real-time availability and booking
- **Flexible Payments**: Multiple payment methods with security
- **Transparent Pricing**: Clear breakdown of costs and fees
- **Cancellation Protection**: Flexible policies for peace of mind

### 🎨 Modern UI/UX
- **Material 3 Design**: Beautiful, consistent interface following latest design guidelines
- **Dark Mode Support**: Automatic theme switching based on system preferences
- **Smooth Animations**: Delightful micro-interactions and transitions
- **Accessibility First**: Inclusive design for all users

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Dependency Injection**: Hilt (Dagger)
- **Navigation**: Navigation Compose
- **Networking**: Retrofit + OkHttp
- **Image Loading**: Coil
- **Database**: Room (for offline caching)
- **AI Integration**: Google Gemini AI SDK
- **Testing**: JUnit, Espresso, Compose Testing
- **Build Tool**: Gradle with Kotlin DSL
- **Version Control**: Git

## 📱 Screenshots

*[Add screenshots here when available]*

## 🚀 Getting Started

### Prerequisites
- Android Studio Iguana (2023.2.1) or later
- JDK 17
- Android SDK API 35
- Google Cloud Project with Gemini AI API enabled

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/sisovin/booking-app.git
   cd booking-app
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Configure API Keys**
   - Create a `secrets.properties` file in the root directory
   - Add your Gemini AI API key:
     ```
     GEMINI_API_KEY=your_api_key_here
     ```

4. **Build the project**
   - Wait for Gradle sync to complete
   - Build → Make Project (Ctrl+F9)

5. **Run on device/emulator**
   - Connect an Android device or start an emulator
   - Run → Run 'app' (Shift+F10)

## 🏗 Project Structure

```
app/
├── src/main/java/com/bookingapp/
│   ├── data/           # Data layer (API, database, models)
│   ├── di/             # Dependency injection modules
│   ├── domain/         # Business logic and use cases
│   ├── presentation/   # UI layer (Compose screens, ViewModels)
│   │   ├── components/ # Reusable UI components
│   │   ├── screens/    # Screen composables
│   │   ├── theme/      # App theming and colors
│   │   └── navigation/ # Navigation setup
│   └── ui/             # Legacy views (if any)
├── src/test/           # Unit tests
└── src/androidTest/    # Instrumentation tests
```

## 🔧 Configuration

### Build Variants
- **Debug**: Development build with logging and debugging features
- **Release**: Production build with optimizations and obfuscation

### Environment Setup
- **API Endpoints**: Configure base URLs in `BuildConfig`
- **Feature Flags**: Toggle features via remote config
- **Analytics**: Firebase Analytics integration

## 🧪 Testing

### Unit Tests
```bash
./gradlew testDebugUnitTest
```

### Instrumentation Tests
```bash
./gradlew connectedDebugAndroidTest
```

### Code Coverage
```bash
./gradlew createDebugCoverageReport
```

## 📦 Build & Deployment

### Debug APK
```bash
./gradlew assembleDebug
```

### Release Bundle
```bash
./gradlew bundleRelease
```

### Play Store Deployment
1. Generate signed bundle
2. Upload to Google Play Console
3. Configure store listing and pricing
4. Publish to production

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add documentation for public APIs
- Write tests for new features

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Google for Jetpack Compose and Gemini AI
- Material Design team for design system inspiration
- Open source community for amazing libraries

## 📞 Support

For support, email support@bookingapp.com or join our Discord community.

---

**Built with ❤️ using Jetpack Compose and Gemini AI**
