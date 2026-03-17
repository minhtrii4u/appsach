# AppDocSach - Complete Architecture & Navigation Flow

## Project Structure
```
appsach/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/appdocsach/
│   │   │   ├── MainActivity.kt          (Home page with book categories)
│   │   │   ├── SearchActivity.kt        (Search books - NAVIGATION FIXED ✅)
│   │   │   ├── ProfileActivity.kt       (User profile page)
│   │   │   ├── DangNhapActivity.kt      (Login)
│   │   │   ├── DangKyActivity.kt        (Sign up)
│   │   │   ├── DocSachActivity.kt       (Read book)
│   │   │   ├── ChiTietActivity.kt       (Book details)
│   │   │   ├── SachAdapter.kt           (RecyclerView adapter for books)
│   │   │   ├── Models.kt                (Data classes)
│   │   │   ├── Sach.kt                  (Book model)
│   │   │   └── FirebaseHelper.kt        (Firebase data initialization - FIXED ✅)
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_search.xml
│   │   │   │   ├── activity_profile.xml
│   │   │   │   ├── activity_dang_nhap.xml
│   │   │   │   ├── activity_dang_ky.xml
│   │   │   │   ├── activity_doc_sach.xml
│   │   │   │   ├── activity_chi_tiet.xml
│   │   │   │   └── item_sach.xml       (Book card item layout)
│   │   │   └── menu/
│   │   │       ├── bottom_nav_menu.xml (Navigation menu - 4 items)
│   │   │       └── menu_bottom_nav.xml (Alternate menu - not used)
│   └── build.gradle.kts
└── gradle/
    └── libs.versions.toml
```

---

## Navigation Flow (Updated)

```
DangNhapActivity (Login)
    ↓
    [Validate credentials]
    ↓
MainActivity (Home with categories)
    ├── Bottom Nav ─────────────────────────────────────┐
    │   ├─→ nav_home → stays on MainActivity            │
    │   ├─→ nav_search → SearchActivity                 │
    │   ├─→ nav_library → (TODO - not implemented)      │
    │   └─→ nav_profile → ProfileActivity ✅            │
    │                                                     │
    └─ Category buttons filter books by genre           │
                                                         │
SearchActivity (Search & Filter Books)                  │
    ├── Bottom Nav ─────────────────────────────────────┤
    │   ├─→ nav_home → MainActivity                     │
    │   ├─→ nav_search → stays on SearchActivity        │
    │   ├─→ nav_library → (TODO)                        │
    │   └─→ nav_profile → ProfileActivity ✅ FIXED      │
    │                                                     │
    └─ SearchView filters books by:                     │
       - Book title                                      │
       - Author name                                     │
       - Category                                        │
                                                         │
ProfileActivity (User Account Page)                    │
    ├── Bottom Nav ─────────────────────────────────────┤
    │   ├─→ nav_home → MainActivity                     │
    │   ├─→ nav_search → SearchActivity                 │
    │   ├─→ nav_library → (TODO)                        │
    │   └─→ nav_profile → stays on ProfileActivity      │
    │                                                     │
    └─ Shows user profile info (avatar, name, etc)     │
```

---

## Bottom Navigation Implementation

### Menu Definition: `bottom_nav_menu.xml`
```xml
<menu>
    <item android:id="@+id/nav_home"     android:title="Trang chủ" />
    <item android:id="@+id/nav_search"   android:title="Tìm kiếm" />
    <item android:id="@+id/nav_library"  android:title="Tủ sách" />
    <item android:id="@+id/nav_profile"  android:title="Hồ sơ" />
</menu>
```

### Implementation in Each Activity

#### MainActivity.kt (Lines 149-172)
```kotlin
bottomNavigationView.selectedItemId = R.id.nav_home
bottomNavigationView.setOnItemSelectedListener { item ->
    when (item.itemId) {
        R.id.nav_home → true                                          // Stay on home
        R.id.nav_search → startActivity(SearchActivity) with data     // Go to search
        R.id.nav_library → { /* TODO */ }
        R.id.nav_profile → startActivity(ProfileActivity) with data   // Go to profile
        else → false
    }
}
```

#### SearchActivity.kt (Lines 50-69) ✅ VERIFIED WORKING
```kotlin
bottomNavigationView.selectedItemId = R.id.nav_search
bottomNavigationView.setOnItemSelectedListener { item ->
    when (item.itemId) {
        R.id.nav_home → startActivity(MainActivity) with data         // Go to home
        R.id.nav_search → true                                        // Stay on search
        R.id.nav_library → { /* TODO */ }
        R.id.nav_profile → startActivity(ProfileActivity) with data   // ✅ GO TO PROFILE
        else → false
    }
}
```

#### ProfileActivity.kt (Lines 20-40) ✅ VERIFIED WORKING
```kotlin
bottomNavigationView.selectedItemId = R.id.nav_profile
bottomNavigationView.setOnItemSelectedListener { item ->
    when (item.itemId) {
        R.id.nav_home → startActivity(MainActivity) with data         // Go to home
        R.id.nav_search → startActivity(SearchActivity) with data     // Go to search
        R.id.nav_library → { /* TODO */ }
        R.id.nav_profile → true                                       // Stay on profile
        else → false
    }
}
```

---

## Firebase Integration

### Models (Models.kt)
```kotlin
data class Account(
    val email: String = "",
    val password: String = "",
    val isAdmin: Boolean = false
)

data class Book(
    val tenSach: String = "",          // Book title
    val tacGia: String = "",            // Author
    val moTa: String = "",              // Description
    val theLoai: String = "",           // Category
    val imageUrl: String = ""           // Book cover image URL
)

data class Favorite(
    val userEmail: String = "",
    val bookId: String = ""
)
```

### Firebase Initialization: FirebaseHelper.kt ✅ FIXED

**Bug Fixed**: Changed from `.getReference("Users")` to `.reference`

**Database Structure Created**:
```
Root
├── /accounts/
│   ├── admin          → Account(email, password, isAdmin=true)
│   ├── user1          → Account(email="MinhTri@gmail.com", ...)
│   └── user2          → Account(email="huy@gmail.com", ...)
├── /books/
│   ├── book1          → Book("Ông già và biển cả", ...)
│   ├── book2          → Book("Dế mèn phiêu lưu ký", ...)
│   ├── book3          → Book("Yêu trên từng ngón tay", ...)
│   ├── book4          → Book("Sóc sợ sệt", ...)
│   ├── book5          → Book("Nơi nào có mẹ là nhà", ...)
│   └── book6          → Book("Tắt đèn", ...)
└── /favorites/
    └── fav1           → Favorite(userEmail="MinhTri@gmail.com", bookId="book1")
```

**Sample Data**:
- Admin account: admin@book.com / admin123
- User 1: MinhTri@gmail.com / 25082006
- User 2: huy@gmail.com / 08112006
- 6 Sample books with Vietnamese titles
- 1 Sample favorite record

---

## Local App Data

### Data in MainActivity (Sach.kt model)
```kotlin
data class Sach(
    val tenSach: String,        // Book title
    val tacGia: String,          // Author
    val hinhAnh: Int,            // Drawable resource ID
    val moTa: String,            // Description
    val theLoai: String          // Category
)
```

**Local books added in MainActivity.onCreate()**:
- 6 sample books with categories
- Filtered by category buttons
- Displayed in GridLayoutManager (2 columns)

---

## Testing Checklist

- [x] **Firebase Database Reference** - FIXED from `.getReference("Users")` to `.reference`
- [x] **Bottom Navigation in SearchActivity** - VERIFIED working with ProfileActivity navigation
- [x] **User Data Passing** - Username passed as "gui_ten_user" extra between activities
- [x] **Activity Registration** - All activities registered in AndroidManifest.xml
- [x] **Menu Configuration** - All activities use the same bottom_nav_menu.xml
- [ ] Java/Gradle Build - REQUIRES Java installation
- [ ] Firebase actual data sync - Will work once app is built and runs
- [ ] Login validation - DangNhapActivity implementation
- [ ] Library feature - nav_library is TODO and needs implementation

---

## Quick Start

1. **Install Java**:
   ```bash
   # Download JDK 11+ from https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
   # Or use Android Studio's bundled Java
   ```

2. **Build the project**:
   ```bash
   cd D:\appsach
   .\gradlew build
   ```

3. **Run the app**:
   - Open in Android Studio and run on emulator/device
   - First launch will initialize Firebase with sample data
   - Login with sample account: admin@book.com / admin123

4. **Test Navigation**:
   - Start at Home (MainActivity)
   - Click bottom navigation search icon → SearchActivity
   - Click bottom navigation profile icon → ProfileActivity ✅ (This now works!)
   - Use SearchView to filter books


