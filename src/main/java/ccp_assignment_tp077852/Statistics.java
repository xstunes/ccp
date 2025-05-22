package ccp_assignment_tp077852;
//libraries
import java.util.ArrayList;

public class Statistics {
    //object
    private int totalPlanesServed = 0;
    private long totalWaitingTime = 0;
    private long maximumWaitingTime = Long.MIN_VALUE;
    private long minimumWaitingTime = Long.MAX_VALUE;
    private final ArrayList<Long> waitingTimes = new ArrayList<>();


    public synchronized void recordPlane(long waitingTime) {
        totalPlanesServed++;
        totalWaitingTime += waitingTime;
        waitingTimes.add(waitingTime);

        // Calculate maximum and minimum waiting times
        if (waitingTime > maximumWaitingTime) {
            maximumWaitingTime = waitingTime;
        }
        if (waitingTime < minimumWaitingTime) {
            minimumWaitingTime = waitingTime;
        }
    }

    public synchronized void printSummary() {
        System.out.println("\n==== Simulation Summary ====");
        System.out.println("Planes served: " + totalPlanesServed);
        System.out.println("Waiting Times (ms): "+waitingTimes);
        System.out.println("Waiting Time (ms): Max = " + maximumWaitingTime + ", Min = " + minimumWaitingTime + ", Avg = " + (totalWaitingTime / totalPlanesServed));
    }
}
