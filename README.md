# 🧠 Advanced Programming Projects

This repository contains two academic projects developed for the **Advanced Programming course**.
Both projects focus on **concurrent and distributed programming concepts**, using different technologies and paradigms.

👨‍💻 Developed by:

* **David Lavado**
* **Rubén Hernández**

---

# 📂 Projects Included

This repository contains the following projects:

1️⃣ **Multi-Agent Travel Reservation System (JADE)**
2️⃣ **Nuclear Reactor Simulation (Ada)**

---

# 🤖 Project 1 — Multi-Agent Travel Reservation System

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

---

# ⚛️ Project 2 — Nuclear Reactor Simulation (Ada)

This project simulates the behavior of a **nuclear power plant control system** using concurrent programming in **Ada**.

The system models three nuclear reactors and ensures that their temperature remains within safe limits.

## ⚙️ Reactor Behaviour

Each reactor:

* Samples temperature every **2 seconds**
* Sends a message to a **coordinator task** to confirm it is active
* Opens a **cooling water gate** when temperature reaches critical levels

If the temperature:

* exceeds **1500°C**, the cooling system activates
* exceeds **1750°C**, an alert message is printed 

Cooling reduces temperature by **50°C per second** while active.

---

## 🧵 Concurrency Model

The system includes multiple concurrent tasks:

* Reactor temperature monitoring
* Cooling actuator control
* Reactor health monitoring
* Coordinator task supervising system activity

The implementation ensures proper **synchronization when accessing shared resources**. 

---

# 🛠 Technologies Used

* **Java**
* **JADE Agent Framework**
* **Ada**
* **Concurrent Programming**

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

### Ada Reactor Simulation

Compile the Ada program using GNAT:

```bash
gnatmake main.adb
./main
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

**David Lavado**
📧 [davidlavgonz@hotmail.com](mailto:davidlavgonz@hotmail.com)

**Rubén Hernández**
📧 [hm04ruben@gmail.com](mailto:hm04ruben@gmail.com)
