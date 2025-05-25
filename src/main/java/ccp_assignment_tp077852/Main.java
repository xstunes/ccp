package ccp_assignment_tp077852;

//libraries
import java.util.Random;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Airport airport = new Airport();// Create an instance of the Airport class
        Statistics statistics = new Statistics();// Create an instance of the Statistics class

        java.util.List<Thread> planeThreads = new java.util.ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            boolean isEmergency = (i == 1); // First plane is assumed to be an emergency
            int passengers = new Random().nextInt(50); // Random number of passengers for each plane, max 50
            Plane plane = new Plane(i, airport, statistics, isEmergency, passengers); // Create a new Plane object
            Thread planeThread = new Thread(plane, "Plane-" + i); // Create a new thread for the plane
            plane.setName("Plane-" + i); // Set the thread name
            planeThread.start();
            planeThreads.add(planeThread);
        }
        // Wait for all planes to finish
        try {
            for (Thread planeThread : planeThreads) {
                planeThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Print statistics summary after all planes are served
        statistics.printSummary();
    }
}
