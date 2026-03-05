# 🧠 Advanced Programming Projects

This repository contains an academic projects developed for the **Advanced Programming course**.

👨‍💻 Developed by:

* **David Lavado González**
* **Rubén Hernández Molina**

---

# 📂 Projects Included

This repository contains the following projects:

1️⃣ **Multi-Agent Travel Reservation System (JADE)**

---

# Multi-Agent Travel Reservation System

This project implements a **multi-agent system using the JADE platform**, simulating the behavior of a travel agency similar to *El Corte Inglés*.

The system allows users to request travel reservations through agents that communicate with each other to find hotel availability.

## 🧩 Agents Implemented

### 👤 User Agent

The **User Agent** interacts with the user via console and asks:

* Destination
* Departure date
* Return date

Once the data is entered, the agent sends a request to the travel agency agent.

### 🏢 CorteIngles Agent

This agent acts as the **central coordinator**.

When it receives a request:

1. It contacts the **Accommodation Agent**
2. Waits for availability confirmation
3. Returns the result to the User Agent

The system must be implemented so the agent **does not block while waiting for responses**, allowing it to process multiple requests concurrently. 

### 🏨 Accommodation Agent

This agent manages hotel availability and stores information about:

* City
* Hotel name
* Number of rooms
* Booking calendar

Example cities included:

* Vigo – Playa Samil
* Plasencia – Parador
* Madrid – Plaza Colón
* Madrid – Bernabéu
* Madrid – Cibeles 

# 🛠 Technologies Used

* **Java**
* **JADE Agent Framework**

---

# 🚀 How to Run

Each project is located in its own directory.

### JADE Agents Project

Compile and run the Java agents using the JADE platform.

Example:

```bash
javac *.java
java jade.Boot
```

---


# 📚 Academic Context

These projects were developed as part of the **Advanced Programming course**, focusing on:

* Multi-agent systems
* Concurrent programming
* Task synchronization
* Distributed communication

---

# 📫 Contact

**David Lavado González**
📧 [davidlavgonz@hotmail.com](mailto:davidlavgonz@hotmail.com)

**Rubén Hernández Molina**
📧 [hm04ruben@gmail.com](mailto:hm04ruben@gmail.com)
