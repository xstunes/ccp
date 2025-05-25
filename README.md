# Airport Concurrent Control Project

## Overview
This project simulates an airport control system managing concurrent plane landings, gate assignments, refueling, and takeoffs using Java multithreading and synchronization mechanisms. It models the behavior of planes requesting to land, disembark passengers, refuel, and take off while ensuring safe and efficient use of limited airport resources such as runways and gates.

## Features
- **Concurrent Plane Simulation:** Multiple planes run as separate threads, simulating real-world concurrency.
- **Emergency Priority:** Emergency planes have priority for landing over non-emergency planes.
- **Resource Management:** Uses semaphores to manage access to a single runway, two gates, and one refuel truck.
- **Landing and Takeoff Coordination:** Ensures only one plane uses the runway at a time, and takeoff planes are prioritized to maintain ground capacity limits.
- **Gate Assignment:** Automatically assigns one of two available gates to planes after landing.
- **Statistics Tracking:** Records and reports statistics such as total planes served, passengers boarded, and waiting times.

## Project Structure
- `src/main/java/ccp_assignment_tp077852/Main.java` - Entry point that initializes the airport and planes, and starts the simulation.
- `src/main/java/ccp_assignment_tp077852/Plane.java` - Defines the Plane class simulating plane behavior as a thread.
- `src/main/java/ccp_assignment_tp077852/Airport.java` - Manages airport resources and synchronization logic.
- `src/main/java/ccp_assignment_tp077852/Statistics.java` - Collects and prints statistics about the simulation.

## How to Run
1. Compile the Java source files.
2. Run the `Main` class.
3. The simulation will start with multiple planes requesting landing, disembarking, refueling, and taking off.
4. After all planes complete, statistics will be printed.

## Concurrency Details
- The runway is controlled by a semaphore allowing only one plane at a time.
- Two gates are available, controlled by a semaphore.
- A single refuel truck is shared among planes.
- Emergency planes have priority for landing.
- Takeoff planes are prioritized over landing planes to maintain a maximum of three planes on the ground (1 on runway, 2 at gates).

## Notes
- The simulation uses random delays to mimic real-world timing.
- The system prints detailed logs of plane actions and ATC approvals.
- The project demonstrates synchronization, thread coordination, and resource management in Java.

## Author
Developed as a concurrent programming assignment project.
