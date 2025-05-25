package ccp_assignment_tp077852;
//libraries
import java.util.ArrayList;

public class Statistics {
    //object
    private int totalPlanesServed = 0;
    private long totalWaitingTime = 0;
    private long maximumWaitingTime = Long.MIN_VALUE;
    private long minimumWaitingTime = Long.MAX_VALUE;
    private int totalPassengersBoarded = 0;
    private final ArrayList<Long> waitingTimes = new ArrayList<>();

    private boolean[] gateAvailable;

    public void setGateStatus(boolean[] gateAvailable) {
        this.gateAvailable = gateAvailable;
    }

    public synchronized void recordPlane(long waitingTime, int passengers) {
        totalPlanesServed++;
        totalWaitingTime += waitingTime;
        totalPassengersBoarded += passengers;
        waitingTimes.add(waitingTime);

        // Calculate maximum and minimum waiting times
        if (waitingTime > maximumWaitingTime) {
            maximumWaitingTime = waitingTime;
        }
        if (waitingTime < minimumWaitingTime) {
            minimumWaitingTime = waitingTime;
        }
    }

    public synchronized boolean areAllGatesEmpty() {
        if (gateAvailable == null) {
            System.out.println("Error: Gate status not set.");
            return false;
        }
        for (boolean available : gateAvailable) {
            if (!available) {
                return false;
            }
        }
        return true;
    }

    public synchronized long getMaxWaitingTime() {
        return maximumWaitingTime;
    }

    public synchronized long getMinWaitingTime() {
        return minimumWaitingTime;
    }

    public synchronized double getAverageWaitingTime() {
        if (totalPlanesServed == 0) return 0;
        return (double) totalWaitingTime / totalPlanesServed;
    }

    public synchronized int getTotalPlanesServed() {
        return totalPlanesServed;
    }

    public synchronized int getTotalPassengersBoarded() {
        return totalPassengersBoarded;
    }

    public synchronized void printSummary() {
        System.out.println("\n==== Simulation Summary ====");
        System.out.println("Planes served: " + totalPlanesServed);
        System.out.println("Passengers boarded: " + totalPassengersBoarded);
        System.out.println("Waiting Times (ms): "+ waitingTimes);
        System.out.println("Waiting Time (ms): Max = " + maximumWaitingTime + ", Min = " + minimumWaitingTime + ", Avg = " + getAverageWaitingTime());
        System.out.println("All gates empty: " + areAllGatesEmpty());
    }
}
