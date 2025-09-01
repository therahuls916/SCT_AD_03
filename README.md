# ⏱️ AuricSnap - Modern Time-Tracking Utility
### Internship Project @ SkillCraft Technology

Auric Stopwatch is a modern, feature-rich time-tracking utility for Android, built entirely with Kotlin and Jetpack Compose. It offers millisecond precision, a clean and responsive user interface, and advanced features like a persistent lap history and Picture-in-Picture (PiP) mode. The app is designed with a robust MVVM architecture to ensure a scalable and maintainable codebase.

---
---

## 📸 Screenshots

*(You can replace these placeholders with links to your actual screenshots)*

| Splash Screen | Main Screen (with Laps) | Picture-in-Picture (PiP) Mode |
| :---: |:---:|:---:|
| `[Link to Splash Screen Image]` | `[Link to Main Screen Image]` | `[Link to PiP Window Image]` |

---

## 🚀 Features

| Feature | Description |
|---|---|
| ⚡ **Precision Timing** | A highly accurate timer that tracks time down to the hundredth of a second, powered by Kotlin Coroutines. |
| ✨ **Modern UI/UX** | Built entirely with **Jetpack Compose & Material 3**. Features a custom "Slate & Teal" theme for a smooth, visually appealing user interface. |
|  LAP **Lap Recording** | Record and display an unlimited number of laps. The lap list is displayed in a clean, elevated `Card` for excellent readability. |
| 🖼️ **Picture-in-Picture (PiP)** | When the timer is running, the app can be minimized into a floating window, allowing you to track time while using other apps. The window includes a functional Play/Pause button. |
| 💾 **Persistent History (Architected)** | Architected to save all laps to a local **Room database**. The foundation is in place to ensure that lap history is never lost, even if the app is closed. |
| 📱 **Adaptive Design** | Features a professional adaptive icon and a custom splash screen for a seamless and branded launch experience on all modern Android devices. |

---

## 🛠 Tech Stack

-   **Tech:** Kotlin, Coroutines, Flow
-   **Architecture:** MVVM (Model-View-ViewModel)
-   **UI:** Jetpack Compose, Material 3
-   **Data Persistence:**
    -   Room: For creating and managing the local SQLite database for lap history.
-   **System Integration:**
    -   Picture-in-Picture (PiP) API
    -   BroadcastReceiver: To handle actions from the PiP window.
-   **Utilities:**
    -   Custom Splash Screen Activity

---

## 🔧 Installation

1.  Clone the repository:
    ```bash
    git clone [your-repository-url]
    ```
2.  Navigate to the project directory:
    ```bash
    cd [your-project-directory-name]
    ```
3.  Open the project in the latest stable version of Android Studio, let Gradle sync, and click ▶️ **Run**.

---

## 📂 Folder Structure

```plaintext
app/src/main/java/com/rahul/auric/auricsnap/
├── data/
│   └── Lap.kt
├── splashscreen/
│   └── SplashActivity.kt
├── ui/
│   ├── screens/
│   │   └── StopwatchScreen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── utils/
│   └── TimeFormatter.kt
├── viewmodels/
│   └── StopwatchViewModel.kt
└── MainActivity.kt

---

## 🔐 Permissions Used

This application currently requires **no special permissions**, providing a secure and privacy-focused user experience.

---

## 🧠 How It Works

-   **UI Layer:** The entire UI is built with Jetpack Compose. `StopwatchScreen` is a stateless Composable that observes data from the `StopwatchViewModel` and displays it. It is designed to adapt its layout based on whether the app is in Picture-in-Picture mode.
-   **State Management:** The `StopwatchViewModel` holds all application state (elapsed time, running status, lap list) using `StateFlow`. This allows the UI to be a simple, reactive layer that automatically updates whenever the state changes.
-   **Timer Engine:** The core timer logic runs in a coroutine within the `viewModelScope`. A `while` loop with a `delay` continuously calculates the elapsed time, ensuring high precision without blocking the main UI thread.
-   **PiP Logic:** When the user leaves the app (`onUserLeaveHint`), `MainActivity` checks if the timer is running and enters PiP mode. A `BroadcastReceiver` listens for clicks on the Play/Pause `RemoteAction` in the PiP window and forwards these commands to the `StopwatchViewModel`.
-   **Database (Architected):** Room is set up with a `Lap` entity. The next step is to integrate the DAO (Data Access Object) into the ViewModel to save laps on creation and load them on startup.

---

## ✅ Planned Features

-   [ ] 💾 **Implement Database Save/Load:** Fully connect the ViewModel to the Room database to save and restore the lap list.
-   [ ] 🏃‍♂️ **True Background Service:** Implement a `Foreground Service` to ensure the timer continues running accurately even if the app is closed by the system.
-   [ ] ✨ **UI Animations:** Add animations to the timer text and the lap list for a more dynamic feel.
-   [ ] 📋 **Lap Management:** Add "Copy" and "Share" actions for the lap list.
-   [ ] ⚙️ **Settings Screen:** Add a settings screen, for example, to keep the screen on while the stopwatch is active.

---

## 🤝 Contributing

Contributions are welcome! If you'd like to help, please fork the repository, create a new branch for your feature or fix, and submit a pull request.

---

## 📄 License

This project is licensed under the MIT License - see the `LICENSE` file for details.

---

## 👨‍💻 Developer

**Rahul Salunke**  
[GitHub](https://github.com/therahuls916) | [LinkedIn](https://www.linkedin.com/in/rahulasalunke/)