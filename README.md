# Pedalpdss — Smart Campus Bicycle Rental and Management System

## Features
- Separate student website (`index.html`) and admin page (`admin.html`)
- 20 bicycle catalog entries across City, Hybrid, Mountain, Road, Electric, and Comfort categories
- Catalog search and category/availability filters
- Each bicycle has a sample rate and specialty/features
- Student rental requires name, ARMIET PIN, and phone number
- Rental return checks name, PIN, and phone number against the active record
- Student sign-in and login landing page (demo-only browser profile)
- Rental history and CSV export
- Admin rental history includes a clearly labelled **Confirm payment collected** button for cash rentals; after confirmation, payment status becomes **Cash collection verified**.
- Light/dark theme saved in browser
- Java console implementation in `Main.java`

## Run locally
Open `index.html` using VS Code Live Server. For local testing, open `admin-login.html` directly or use `admin.html`, which redirects to it. For GitHub Pages, the clean route `/admin/login/` is also included.

Demo admin password: `PedalAdmin2026!`

Student sign-in is a front-end demo, not college-verified authentication. Admin cash verification is a manual status marker only; it does not verify a bank transaction.

## Important security and data limitations
This is a static GitHub Pages demo. The admin password is embedded in client-side JavaScript and can be found by inspecting source files. It only hides the admin interface casually; it does **not** protect the admin data from outsiders. Do not publish sensitive real student details. A genuinely private admin panel requires server-side authentication and a database.

The student site and admin page use browser `localStorage`, so records are shared only between pages opened on the same browser origin/profile. Different students/devices will not share records. A real connected campus system needs a backend and central database.

The catalog rates, bicycle specialties, and model entries are sample project data. External bicycle photos load from Unsplash and need internet access.

## Java
Compile and run:
```bash
javac Main.java
java Main
```
The Java console program runs independently of the website.

## GitHub Pages
Upload `index.html`, `admin.html`, `style.css`, `script.js`, `admin.js`, `Main.java`, `README.md`, and `armiet-logo.png` to the repository root. Enable Pages under Settings → Pages, branch `main`, folder `/(root)`.


## Rental duration, billing and payment demo
Students choose a whole-hour rental duration (1–24) and a payment method (cash or online). The estimated bill is the hourly rate multiplied by booked hours. Admin tables show booked hours, bill, payment method, and payment status. Returning a bicycle keeps rented bicycles selectable and verifies the student's name, ARMIET PIN, and phone number.

Online payment is a demo selection only; it does not charge money. Cash collection is not automatically verified. Real online payments require a payment gateway and secure backend.

## Java requirements (important for submission)

- Install **JDK 17 or later** and make sure `java -version` and `javac -version` work in the terminal.
- Keep `Main.java` in the project folder.
- Open a terminal in the `Pedalpdss` folder and run:
  ```bash
  javac Main.java
  java Main
  ```
- `Main.java` is the separate Java desktop/console-style implementation for the Java requirement. The website itself runs with HTML, CSS, and JavaScript; Java does not run inside GitHub Pages.
- Admin fleet additions/removals in this website are saved in the same browser's local storage and appear in the student catalogue after refreshing it. They are not synced across different browsers/devices.
