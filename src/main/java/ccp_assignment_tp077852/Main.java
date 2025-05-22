package ccp_assignment_tp077852;

//libraries
import java.util.Random;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Airport airport = new Airport();// Create an instance of the Airport class
        Statistics statistics = new Statistics();// Create an instance of the Statistics class

        for (int i = 1; i <= 6; i++) {
            boolean isEmergency = (i == 1); // First plane is assumed to be an emergency
            int passengers = new Random().nextInt(50); // Random number of passengers for each plane
            Plane plane = new Plane(i, airport, statistics, isEmergency, passengers); // Create a new Plane object
            plane.start();
        }
        // Wait for all planes to finish
        try {
            for (Thread plane : Thread.getAllStackTraces().keySet()) {
                if (plane instanceof Plane) {
                    plane.join();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
        statistics.printSummary();
    }
}

