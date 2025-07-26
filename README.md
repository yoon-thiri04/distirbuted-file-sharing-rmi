# 📁 Distributed File Sharing System using Java RMI

A Java-based distributed file sharing system using **Remote Method Invocation (RMI)**. This project supports both **Command Line Interface (CLI)** and **Swing-based GUI** for client interaction.

---

## 📌 Project Scenario

This project simulates a simple distributed file server where:

- Users can **register** and **log in**.
- Once authenticated, they can:
  - **Upload** files to the server.
  - **Download** files they previously uploaded.
  - **View a list** of their uploaded files.
- All authentication is handled through a `users.txt` file (CSV format: `username,password`).
- The server stores files under a folder named `server_files/username/`.
- In the CLI version, downloaded files are saved under `client_files/username/`.
- In the GUI version, users can choose a custom path to save downloaded files via a `JFileChooser`.
- The GUI version also allows users to **log out** and return to the login screen.

---

## 🧩 Features

### ✅ User Authentication
- Register and log in via CLI or GUI.
- Credentials are stored in a file named `users.txt` using CSV format.
- Only registered users can log in.

### ✅ File Operations
- Upload a file to the server.
- View a list of uploaded files.
- Download files (with custom save path in GUI).

### ✅ GUI Version (Swing)
- Login / Register form
- Buttons for:
  - Upload File
  - Download File
  - View File List
  - Logout
- JFileChooser used to select download destination

### ✅ CLI Version
- Menu-driven with numeric choices
- Text-based interaction for upload, list, and download

---

## 🗂 Project Structure

```

project-root/
│
├── common/
│   └── FileService.java            # RMI Interface
│
├── Server/
│   ├── FileServer.java            # RMI Server
│   ├── FileServiceImpl.java       # Implementation of RMI Interface
│   └── users.txt                  # Stores registered users in CSV format
│
├── Client/
│   ├── FileClient.java            # CLI version of the client
│   ├── FileClientGUI.java         # Swing GUI version of the client
│   └── client\_files/              # Stores downloaded files (for CLI)
│
└── server\_files/                  # Stores uploaded files per user

````

---

## 🧪 How to Run

### 1️⃣ Compile the project:

```bash
javac -d . .\common\*.java .\Server\*.java .\Client\*.java
````

### 2️⃣ Start the RMI Server:

```bash
java Server.FileServer
```

### 3️⃣ Start the Client:

#### CLI Client:

```bash
java Client.FileClient
```

#### GUI Client:

```bash
java Client.FileClientGUI
```

---

## 📷 Screenshots (optional)

> You can add screenshots of the GUI here to showcase the login screen, upload, download, and file listing.

---

## 📌 Notes

* Ensure that RMI Registry is running automatically with the program (via `LocateRegistry.createRegistry(...)` in server).
* Make sure folders `server_files/` and `client_files/` exist or are created dynamically.
* All file paths are handled by username to isolate user data.

---

## 🧑‍💻 Author

Yoon Thiri Aung
📚 UCSY | 🎓 UoPeople Scholar | 💻 Backend & Java Developer

---

## 📃 License

This project is for educational purposes.

```

---

Let me know if you want a **badge-style title**, **screenshots section**, or if you're deploying this on GitHub Pages too!
```
