# Face Detection Based Attendance System (Java + OpenCV)

This project implements a real-time face-detection-based attendance system using **Java** and **OpenCV**.

## 🔹 Features
- Real-time webcam access
- Face detection using Haar Cascade
- Automatic attendance marking
- CSV-based attendance storage
- Lightweight and fast (no face recognition dependency)

## 🛠 Technologies Used
- Java
- OpenCV
- Haar Cascade Classifier
- CSV File Handling

## 📂 Project Structure
Javastart/
├── DatasetCapture.java
├── AttendanceCapture.java
├── haarcascade_frontalface_default.xml
├── attendance.csv
├── lib/opencv-4120.jar

## ▶ How to Run
1. Install Java (JDK)
2. Download OpenCV and configure it
3. Add OpenCV JAR to classpath
4. Run `DatasetCapture.java` to collect images
5. Run `AttendanceCapture.java` to mark attendance

## 📝 Note
This project uses **face detection** for attendance.  
Face recognition can be added using OpenCV Contrib modules.

## 👨‍💻 Author
Aniket
