package ccp_assignment_tp077852;
//libraries
import java.util.concurrent.Semaphore;

public class Airport {
    //semaphore variables
    private final Semaphore runway = new Semaphore(1); //one plane, one runway
    private final Semaphore gates = new Semaphore(3); //three gates only available
    private final Semaphore refuelTruck = new Semaphore(1); //one refuel truck only available

    public void requestLanding( int PlaneID) throws InterruptedException
    {
        System.out.println("Plane " + PlaneID + ": Requesting runway for landing... Circling in the air.");
        Thread.sleep(2000); // Simulate circling time
        runway.acquire();
        System.out.println("ATC: Approved Plane "+ PlaneID + " for landing.");
        System.out.println("Plane " + PlaneID + ": Landing on the runway.");
        Thread.sleep(2000); // Simulate landing time
        System.out.println("Plane " + PlaneID + ": Landed on the runway.");
    }

    public void rejectLanding(int PlaneID) throws InterruptedException
    {
        System.out.println("Plane " + PlaneID + ": Landing rejected. Circling in the air.");
        Thread.sleep(2000); // Simulate circling time
        requestLanding(PlaneID); // Request landing again
    }

    public void releaseRunway(int PlaneID) throws InterruptedException
    {
        System.out.println("Runway currently occupied by Plane " + PlaneID + ": Releasing the runway...");
        Thread.sleep(2000); // Simulate releasing time
        runway.release();
        System.out.println("Runway currently free.");
    }

    public void requestGate(int PlaneID) throws InterruptedException
    {
        System.out.println("Plane " + PlaneID + ": Requesting a gate.");
        System.out.println("Plane " + PlaneID + ": Waiting to acquire a gate...");
        gates.acquire();
        System.out.println("Plane " + PlaneID + ": Acquired a gate.");
        Thread.sleep(2000); // Simulate coasting time
        System.out.println("Plane " + PlaneID + ": Docked at the gate.");
    }

    public void releaseGate(int PlaneID) throws InterruptedException
    {
        System.out.println("Plane " + PlaneID + ": Releasing the gate...");
        Thread.sleep(2000); // Simulate releasing time
        gates.release();
        System.out.println("Plane " + PlaneID + ": Leaving the gate...");
    }

    public void refuel(int PlaneID) throws InterruptedException
    {
        System.out.println("Plane " + PlaneID + ": Waiting to acquire refuel truck...");
        refuelTruck.acquire();
        System.out.println("Plane " + PlaneID + ": Acquired refuel truck.");
        System.out.println("Plane " + PlaneID + ": Requesting refueling.");
        Thread.sleep(2000); // Simulate refueling time
        System.out.println("Plane " + PlaneID + ": Refueling completed.");
        refuelTruck.release();
        System.out.println("Plane " + PlaneID + ": Released refuel truck.");
    }
}