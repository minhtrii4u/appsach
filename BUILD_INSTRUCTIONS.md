# Build Instructions for AppDocSach

## Problem: Java/JAVA_HOME Not Configured

The error encountered:
```
ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
```

---

## Solution 1: Use Android Studio (Easiest)

Android Studio includes bundled Java, so you can build directly:

1. **Open the project in Android Studio**
   - File → Open → Select `D:\appsach`

2. **Build the project**
   - Build → Make Project (or Ctrl+F9)
   - Or Build → Build Bundle(s) / APK(s)

3. **Run the app**
   - Run → Run 'app' (or Shift+F10)
   - Select an emulator or connected device

---

## Solution 2: Install Java JDK 11+

### Option A: Oracle JDK
1. Download from: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
2. Install to default location (e.g., `C:\Program Files\Java\jdk-11.x.x`)
3. Set environment variable:
   ```powershell
   # In PowerShell (as Administrator):
   [Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-11.0.x", "Machine")
   $env:JAVA_HOME = "C:\Program Files\Java\jdk-11.0.x"
   ```

4. Verify installation:
   ```powershell
   java -version
   javac -version
   ```

### Option B: Adoptium (OpenJDK)
1. Download from: https://adoptium.net/
2. Choose Java 11 or later
3. Install and set JAVA_HOME as above

### Option C: Android Studio's Bundled Java
1. Find Java location in Android Studio:
   - File → Settings → Build, Execution, Deployment → Gradle → Gradle JDK
   - It shows the path (usually something like: `C:\Program Files\Android\Android Studio\jbr`)

2. Set JAVA_HOME in PowerShell:
   ```powershell
   $env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
   ```

3. Verify:
   ```powershell
   $env:JAVA_HOME
   & "$env:JAVA_HOME\bin\java.exe" -version
   ```

---

## Solution 3: Build Using Gradle Wrapper (Once Java is set)

```powershell
# Navigate to project root
cd D:\appsach

# Set Java home (choose one of the options above)
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"

# Run gradle build
.\gradlew build

# Or clean build
.\gradlew clean build

# Or install to device/emulator
.\gradlew installDebug
```

---

## Gradle Build Commands

```powershell
cd D:\appsach

# Check Gradle version and status
.\gradlew --version

# Clean build
.\gradlew clean

# Build debug APK
.\gradlew assembleDebug

# Build release APK
.\gradlew assembleRelease

# Run tests
.\gradlew test

# Check for lint issues
.\gradlew lint

# Full build with all checks
.\gradlew build
```

---

## Build Artifacts Location

After successful build, find the APK files at:

```
app/build/outputs/apk/debug/      # Debug APK
app/build/outputs/apk/release/    # Release APK (after full release build)
app/build/outputs/bundle/         # Android App Bundle
```

---

## Troubleshooting

### Error: "Gradle sync failed"
1. File → Sync Now (in Android Studio)
2. Check Java/Gradle compatibility
3. Check internet connection for dependency downloads

### Error: "compileSdk 36 not installed"
```powershell
# Update Android SDK in Android Studio:
# Tools → SDK Manager → SDK Platforms → Android 15 (API Level 36)
```

### Error: Dependency resolution issues
```powershell
# Clean cache and retry:
.\gradlew clean --refresh-dependencies build
```

### Error: Port 8008 already in use
```powershell
# Android Studio is already running. Either:
# 1. Use that instance
# 2. Kill the process: Get-Process -Name "gradle" | Stop-Process
```

---

## Running the App

### Option 1: Android Emulator
1. Open Android Studio
2. Tools → AVD Manager → Create or select a virtual device
3. Tools → AVD Manager → Launch the emulator
4. Run → Run 'app'

### Option 2: Physical Device
1. Enable Developer Mode on your Android device
2. Connect via USB
3. Allow USB debugging when prompted
4. Run → Run 'app'

### Option 3: Command Line (Requires emulator or device connected)
```powershell
cd D:\appsach
.\gradlew installDebug
```

---

## Firebase Integration

Once the app is built and running:

1. **First Launch**
   - FirebaseHelper.addSampleData() is called automatically
   - Creates accounts, books, and favorite records
   - Check Firebase Console to verify

2. **Firebase Console**
   - Go to: https://console.firebase.google.com/
   - Project: book-a8796
   - Realtime Database section
   - Verify data structure matches:
     ```
     /accounts/ (3 records)
     /books/ (6 records)
     /favorites/ (1 record)
     ```

3. **Test with Sample Accounts**
   - admin@book.com / admin123 (Admin)
   - MinhTri@gmail.com / 25082006 (User)
   - huy@gmail.com / 08112006 (User)

---

## Quick Build Checklist

- [ ] Java JDK 11+ installed or Android Studio open
- [ ] JAVA_HOME environment variable set correctly
- [ ] Android SDK (API 36) installed
- [ ] Run `.\gradlew build` successfully
- [ ] APK generated in `app/build/outputs/apk/debug/`
- [ ] App installs and runs on device/emulator
- [ ] Firebase data appears in console after first launch
- [ ] Bottom navigation works (Home → Search → Profile)
- [ ] Sample data loads and displays in app


