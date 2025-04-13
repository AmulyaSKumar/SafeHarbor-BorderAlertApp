# 🌊 Ocean Border Alert App for Fishermen

A safety-focused **Android mobile app built with Java and Gradle** that provides **real-time offline alerts** to Indian fishermen when they approach or cross international maritime borders using GPS technology. Designed to work without internet and tailored to the needs of coastal fishing communities.

---

## 📱 Features

- ✅ **Offline Support** — Entirely functional without internet connectivity.
- 🌍 **Real-Time GPS-Based Alerts** — Uses device GPS to track current location and alert when approaching border.
- 📳 **Vibration Alerts** — Strong haptic feedback when nearing danger zones.
- 🗺️ **Preloaded Maritime Boundary Data** — Works without network requests.
- 🇮🇳 **Made for Indian Waters** — Focused on Indian Ocean region borders and Exclusive Economic Zones (EEZ).
- 🔋 **Runs in Background** — Alert system works even when app is minimized.

---

## 📦 Prerequisites

Before setting up the project, ensure you have:

- [Android Studio](https://developer.android.com/studio) installed.
- Java JDK 8 or higher installed and configured.
- A physical Android device or emulator with **GPS capability**.
- Maritime border coordinates (in CSV, JSON, or embedded format).
- Gradle properly installed (Android Studio manages it by default).

---

## 🚀 Installation and Setup

1. **Clone this Repository**
   ```bash
   git clone https://github.com/your-username/ocean-border-alert-java.git
   cd ocean-border-alert-java
   ```

2. **Sync the application**
   -  File → Sync Project with Gradle Files

3. **Permissions Setup**
   - Edit `AndroidManifest.xml` to include:
     ```xml
     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
     <uses-permission android:name="android.permission.VIBRATE" />
     ```

4. **Build & Run**
   - Connect your Android device with USB Debugging enabled.
   - Click **Run ▶️** from Android Studio or press **Shift + F10** .

---

## 🧠 How It Works

- **Step 1**: Uses `LocationManager` to get the user's current latitude and longitude.
- **Step 2**: Loads pre-stored border points of international waters.
- **Step 3**: Uses the **Haversine Formula** to calculate the distance between current location and nearest boundary point:
  ```java
  distance = 2 * R * arcsin(√[sin²((lat2 - lat1)/2) + cos(lat1)*cos(lat2)*sin²((lon2 - lon1)/2)])
  ```
  - R = Radius of Earth (6371 km)
  - Output: Distance in kilometers

- **Step 4**: If distance < predefined threshold (e.g., 1 km), alert is triggered with vibration and visual notification.

---

## 🛠️ Tech Stack

- Java (Android SDK)
- Gradle (Build system)
- Android LocationManager & Services
- Haversine Formula for distance calculation
- Android Vibration API


## 💡 Future Enhancements

- Add **Voice Alerts** in local languages.
- Integrate **weather updates** for storm warnings.
- Support **dynamic boundary updates** via Bluetooth transfer or offline sync.
- Include **multi-language UI** and **marine radio signal support**.

## 🧑‍🤝‍🧑 Shout to the Contributors

| Name | GitHub |
|------|--------|
| [Amulya S] | [github.com/AmulyaSKumar](https://github.com/AmulyaSKumar) |
| [Supriya S] | [github.com/Supriya](https://github.com/Supriya-21-04) |
| [Gagana] | [github.com/Gagana](https://github.com/Gagana09) |
| [Sanajana Bhagwath] | [github.com/SanjanaBhagwath](https://github.com/SanjanaBhagwath) |
