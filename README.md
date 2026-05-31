# VinUni Lost & Found System
**COMP1020 Object-Oriented Programming & Data Structures – Spring 2026**
**Group 12**

---

## Table of Contents
1. [Project Overview](#1-project-overview)
2. [Project Structure](#2-project-structure)
3. [Technologies & Requirements](#3-technologies--requirements)
4. [Build & Run Instructions](#4-build--run-instructions)
5. [Configuration](#5-configuration)
6. [Demo Scenarios (via Web UI)](#6-demo-scenarios-via-web-ui)
7. [OOP & Algorithm Design Decisions](#8-oop--algorithm-design-decisions)

---

## 1. Project Overview

A web-based Campus Lost & Found System for VinUniversity students and staff to report, search, and recover lost items. The system is built on a **3-tier architecture**:

- **Frontend** — Single-page HTML/CSS/JS (`index.html`) served as a Spring Boot static resource
- **Backend** — Java Spring Boot REST API
- **Database** — Firebase Firestore (cloud NoSQL)

**Live demo (deployed on Railway):** The app is live at [https://lostfoundsystem-production.up.railway.app/](https://lostfoundsystem-production.up.railway.app/)

**GitHub Repository:** https://github.com/quyneehcl/LostFoundSystem.git

---

## 2. Project Structure

```
src/main/java/com/lostfound/demo/
├── DemoApplication.java          # Spring Boot entry point (@EnableScheduling)
├── config/
│   └── FirebaseConfig.java       # Firebase Admin SDK initialization
├── models/
│   ├── Item.java                 # Abstract base class
│   ├── LostItem.java             # Concrete subclass (type = "lost")
│   ├── FoundItem.java            # Concrete subclass (type = "found")
│   ├── User.java                 # Immutable user model
│   └── MatchResult.java          # Match result with score; implements Comparable
├── services/
│   ├── ItemService.java          # Central service, @Scheduled cleaner
│   ├── ItemSearcher.java         # Linear Search (keyword, filter, findById)
│   ├── ItemSorter.java           # Merge Sort (newest-first by date)
│   ├── MatchingService.java      # Weighted matching with Max-Heap PriorityQueue
│   ├── ItemCleaner.java          # Min-Heap for auto-removing items > 30 days old
│   └── AuthService.java          # In-memory user registry
├── repositories/
│   └── ItemRepository.java       # Firestore CRUD operations
└── controllers/
    ├── ItemController.java       # REST endpoints for items
    └── AuthController.java       # REST endpoint for auth

src/main/resources/
├── application.properties        # server.port config
└── static/
    └── index.html                # Full frontend SPA

pom.xml                           # Maven dependencies
railway.toml                      # Railway deployment config
```

---

## 3. Technologies & Requirements

| Requirement | Version / Notes |
|---|---|
| Java (JDK) | **17** or higher |
| Maven | 3.9+ (Maven Wrapper `mvnw` included — no install needed) |
| Firebase project | Requires a service account key (see Configuration) |
| Internet connection | Required for Firebase Firestore |

> **No other software installation is required.** The Maven Wrapper (`mvnw` / `mvnw.cmd`) downloads Maven automatically on first run.

---

## 4. Build & Run Instructions

### Option A — Live Demo (No Setup Required)

The app is already deployed and accessible at:

**https://lostfoundsystem-production.up.railway.app/**

No installation needed — open the link in your browser and test all features directly.

### Option B — Command Line

**Step 1: Clone the repository**
```bash
git clone https://github.com/quyneehcl/LostFoundSystem.git
cd LostFoundSystem
```

**Step 2: Add Firebase credentials** (see [Section 5](#5-configuration))

**Step 3: Build the project**

On macOS / Linux:
```bash
./mvnw clean package -DskipTests
```

On Windows:
```cmd
mvnw.cmd clean package -DskipTests
```

**Step 4: Run the application**
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

**Step 5: Open the app**

Navigate to [http://localhost:8080](http://localhost:8080) in your browser.

---

### Option C — IDE (IntelliJ IDEA / VS Code)

1. Open the project root folder in your IDE.
2. Ensure **JDK 17** is configured as the project SDK.
4. Run `DemoApplication.java` (the class with `@SpringBootApplication`).
5. Navigate to [http://localhost:8080](http://localhost:8080).

---

## 5. Configuration

### Firebase Credentials

The `serviceAccountKey.json` file is included in the submission zip at:
```
src/main/resources/serviceAccountKey.json
```
No additional setup is required — the app will connect to Firebase Firestore automatically on startup.

---

## 6. Demo Scenarios (via Web UI)

After running the app, open [http://localhost:8080](http://localhost:8080) in your browser, or access the live demo at [https://lostfoundsystem-production.up.railway.app/](https://lostfoundsystem-production.up.railway.app/), and follow the scenarios below.

---

### Scenario 1 — User Registration & Login

1. The login modal appears automatically when the app loads.
2. Switch to the **"Sign up"** tab, enter a name, email (e.g. `student@vinuni.edu.vn`), and a password of at least 6 characters → click **"Create Account"**.
3. The system registers the account and redirects to the main page with a `Welcome, student!` toast notification.

**Pre-seeded Admin account:**
- Email: `admin@vinuni.edu.vn` (hardcoded in `AuthService`)
- Admin can view and delete all items in the system.

---

### Scenario 2 — Report a Lost Item

1. Click **"Report Lost"** in the navigation bar.
2. Fill in the form:
   - Item Name: `iPhone 15 Pro`
   - Category: `Electronics`
   - Description: `Space black, cracked screen protector`
   - Location: `Library 2nd Floor`
   - Contact Info: `0912345678`
3. Click **"Submit Lost Report"**.
4. A `Report submitted!` toast appears and the app redirects to Browse. The new item appears at the top of the list — sorted newest-first by **Merge Sort**.

---

### Scenario 3 — Search Items by Keyword

1. On the **Browse Items** page, type `iphone` in the search bar → click **Search**.
2. The system applies **Linear Search** across all items, matching against name, description, and location (case-insensitive).
3. Results are displayed with a count and sorted newest-first.

Use the **Lost / Found** tab buttons and category chips (e.g. **Electronics**, **Bags**) to filter further.

---

### Scenario 4 — Matching Algorithm

**Setup:** Create two items:
- Report **Lost**: Name `iPhone 15 Pro`, Category `Electronics`, Location `Library 2nd Floor`
- Report **Found**: Name `iPhone 15`, Category `Electronics`, Location `Library`

**Steps:**
1. Go to **My Reports**, find the Found item just created.
2. Click **"Find Matches"**.
3. The system uses a **Max-Heap PriorityQueue** with weighted scoring:
   - Category match → +40%
   - Location similarity (substring) → +~21% (0.7 × 30%)
   - Name similarity (substring) → +~14% (0.7 × 20%)
4. `iPhone 15 Pro` appears at the top with a score around **75–82%**, labeled **BEST MATCH**.

---

### Scenario 5 — Mark as Returned

1. Go to **My Reports**, find any active item.
2. Click **"Returned"**.
3. The item status changes to `RETURNED`, the "Find Matches" button disappears, and the item is excluded from future matching results.

---

### Scenario 6 — Delete Authorization Check

1. Log in as a regular user and click on an item card created by a different user.
2. The **"Delete"** button is **not shown** — only the original reporter or an Admin can delete an item.
3. Log in as `admin@vinuni.edu.vn` → go to **My Reports** → all items in the system are listed and can be deleted.

---

## 7. OOP & Algorithm Design Decisions

### Inheritance & Polymorphism
`Item` is an **abstract class** with abstract methods `getItemType()`, `getSummary()`, and `toMap()`. `LostItem` and `FoundItem` extend it, enabling polymorphic `List<Item>` collections throughout the codebase.

### Encapsulation
`User` is immutable — it has only getters, no setters, enforcing data integrity.

### Custom Data Structures

| Structure | Class | Purpose | Time Complexity |
|-----------|-------|---------|-----------------|
| Merge Sort | `ItemSorter` | Sort items by date, newest-first | O(n log n) |
| Linear Search | `ItemSearcher` | Keyword search, filter, findById | O(n) |
| Max-Heap (`PriorityQueue`) | `MatchingService` | Rank matches by score descending | O(n log n) |
| Min-Heap (`PriorityQueue`) | `ItemCleaner` | Efficiently find oldest items for cleanup | O(k log n) |
| `HashMap<String, Object>` | `Item.toMap()`, `MatchResult.toMap()` | Firestore-compatible serialization | O(1) per field |

### Weighted Matching Algorithm (`MatchingService`)
Each item pair is scored from 0–100% using:
- **Category** (40%) — exact match only
- **Location** (30%) — exact / substring / word overlap
- **Name** (20%) — exact / substring / word overlap
- **Description** (10%) — exact / substring / word overlap

The `stringSimilarity()` function returns 1.0 (exact), 0.7 (contains), or up to 0.5 (word overlap ratio).

### Scheduled Cleanup
`ItemService.cleanOldReports()` is annotated with `@Scheduled(cron = "0 0 0 * * *")` and uses `ItemCleaner`'s Min-Heap to pop items older than 30 days in O(k log n), where k = number of items removed.

---

*VinUni Lost & Found — COMP1020 Group 12 — Spring 2026*
