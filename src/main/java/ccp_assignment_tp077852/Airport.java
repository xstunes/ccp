package ccp_assignment_tp077852;
//libraries
import java.util.concurrent.Semaphore;

public class Airport {
    //semaphore variables
    public final Semaphore runway = new Semaphore(1); //one plane, one runway
    private final Semaphore gates = new Semaphore(2); //two gates only available, planes on ground can be 3.
    private final Semaphore refuelTruck = new Semaphore(1); //one refuel truck only available

    private final boolean[] gateAvailable = {true, true}; // Track availability of each gate

    private int waitingEmergencyPlanes = 0;

    // Check if runway is empty
    public boolean isRunwayEmpty() {
        return runway.availablePermits() == 1;
    }

    public synchronized void requestLanding(int PlaneID, boolean isEmergency) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Requesting runway for landing...");
        if (isEmergency) {
            waitingEmergencyPlanes++;
            while (runway.availablePermits() == 0) {
                wait();
            }
            waitingEmergencyPlanes--;
            runway.acquire();
        } else {
            while (runway.availablePermits() == 0 || waitingEmergencyPlanes > 0) {
                wait();
            }
            runway.acquire();
        }
        System.out.println("ATC: Approved "+ Thread.currentThread().getName() + " for landing.");
        System.out.println(Thread.currentThread().getName() + ": Landing on the runway.");
        Thread.sleep(2000); // Simulate landing time
        System.out.println(Thread.currentThread().getName()+ ": Landed on the runway.");
    }

    public synchronized void releaseRunway(int PlaneID) throws InterruptedException
    {
        System.out.println("Runway currently occupied by " + Thread.currentThread().getName() + ": Releasing the runway...");
        Thread.sleep(2000); // Simulate releasing time
        runway.release();
        notifyAll();
        System.out.println("Runway currently free.");
    }

    public void rejectLanding(int PlaneID, boolean isEmergency) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Landing rejected. Circling in the air.");
        Thread.sleep(2000); // Simulate circling time
        requestLanding(PlaneID, isEmergency); // Request landing again
    }

    public void TakeOff(int PlaneID) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Requesting runway for takeoff...");
        Thread.sleep(2000); // Simulate waiting time
        runway.acquire();
        System.out.println("ATC: Approved "+ Thread.currentThread().getName() + " for take off.");
    }


    // Assign gates automatically after landing
    public synchronized int assignGate(int PlaneID) throws InterruptedException
    {
        gates.acquire();
        int assignedGate = -1;
        for (int i = 0; i < gateAvailable.length; i++) {
            if (gateAvailable[i]) {
                gateAvailable[i] = false;
                assignedGate = i + 1; // Gate numbers start at 1
                break;
            }
        }
        System.out.println("ATC: " + Thread.currentThread().getName() + " is assigned gate " + assignedGate + ".");
        Thread.sleep(2000); // Simulate coasting time
        System.out.println(Thread.currentThread().getName() + ": Docked at gate " + assignedGate + ".");
        return assignedGate;
    }

    public synchronized void releaseGate(int PlaneID, int gateNumber) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Leaving gate " + gateNumber + "...");
        Thread.sleep(2000); // Simulate releasing time
        gateAvailable[gateNumber - 1] = true;
        gates.release();
    }

    public void refuel(int PlaneID) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Waiting to acquire refuel truck...");
        refuelTruck.acquire();
        System.out.println(Thread.currentThread().getName() + ": Acquired refuel truck.");
        Thread.sleep(2000); // Simulate refueling time
        System.out.println(Thread.currentThread().getName() + ": Refueling completed.");
        refuelTruck.release();
        System.out.println(Thread.currentThread().getName() + ": Released refuel truck.");
    }
}
