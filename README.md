# 📁 Distributed File Sharing System using Java RMI

A Java-based distributed file sharing system using **Remote Method Invocation (RMI)**. This project supports both **Command Line Interface (CLI)** and **Swing-based GUI** for client interaction.

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

## 📝 Server Logs with Timestamps

Every user action is logged on the server with a timestamp for auditing and tracking purposes. Logs are stored in a plain text file and follow this format:

```

\[YYYY-MM-DD HH\:mm\:ss]username action

```

**Examples:**
```

\[2025-07-26 00:27:07]yoon uploaded eg.py
\[2025-07-26 00:28:56]yoon downloaded new\.txt
\[2025-07-26 10:41:47]mary downloaded lll.txt
\[2025-07-26 10:48:52]sue downloaded mysql.txt

```
---

## 🗂 Project Structure

```

project-root/
│
├── Client/
│   ├── FileClient.java            
│   ├── FileClientGUI.java         # Swing GUI version of the client
│   └── client_files/
|           
├── common/
│   └── FileService.java            
│
├── Server/
│   ├── FileServer.java            
│   └── FileServiceImpl.java
|                        
|── server_files/
|    ├── username1/
|        ├── file1.txt
|        └── file2.png
|    └── username2/
|        └── file5.py
└── server.log
└── users.txt
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

## 📃 License / Disclaimer

This project is created purely for educational purposes and personal learning.  
It is part of my exploration into Java RMI while studying the **Distributed Systems** course.

I built this project to deepen my understanding of how Java Remote Method Invocation works in real-world scenarios.  
It reflects lessons learned during development and is not intended for commercial use.

Feel free to explore, modify, or extend this project for learning purposes!
