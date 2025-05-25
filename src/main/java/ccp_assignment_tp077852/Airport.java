package ccp_assignment_tp077852;
//libraries
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Airport {
    //semaphore variables
    public final Semaphore runway = new Semaphore(1); //one plane, one runway
    private final Semaphore gates = new Semaphore(2); //two gates only available, planes on ground can be 3.
    private final ReentrantLock refuelTruck = new ReentrantLock(); //one refuel truck only available

    public final boolean[] gateAvailable = {true, true}; // Track availability of each gate

    private int waitingEmergencyPlanes = 0;
    private int waitingTakeoffPlanes = 0; // new counter for waiting takeoff planes

    // Check if runway is empty
    public boolean isRunwayEmpty() {
        return runway.availablePermits() == 1;
    }

    public void requestLanding(int PlaneID, boolean isEmergency) throws InterruptedException
    {
        synchronized(this) {
            if (isEmergency) {
                waitingEmergencyPlanes++;
                while (runway.availablePermits() == 0) {
                    wait();
                }
                waitingEmergencyPlanes--;
                runway.acquire();
            } else {
                
                // Accept non-emergency planes if runway is available and no emergency planes waiting or takeoff planes waiting
                while (runway.availablePermits() == 0 || waitingEmergencyPlanes > 0 || waitingTakeoffPlanes > 0) {
                    wait();
                }
                runway.acquire();
            }
        }
        System.out.println("ATC: Approved "+ Thread.currentThread().getName() + " for landing.");
        System.out.println(Thread.currentThread().getName() + ": Landing on the runway.");
        Thread.sleep(2000); // Simulate landing time outside synchronized block
        System.out.println(Thread.currentThread().getName()+ ": Landed on the runway.");
    }

    public void releaseRunway(int PlaneID) throws InterruptedException
    {
        System.out.println("Runway currently occupied by " + Thread.currentThread().getName() + ": Releasing the runway...");
        Thread.sleep(2000); // Simulate releasing time outside synchronized block
        synchronized(this) {
            runway.release();
            notifyAll();
        }
        System.out.println("Runway currently free.");
    }

    public void rejectLanding(int PlaneID, boolean isEmergency) throws InterruptedException
    {
        System.out.println("ATC: " + Thread.currentThread().getName() + " Landing is rejected. Airport is full. Circling in the air.");
        Thread.sleep(2000); // Simulate circling time
        boolean landed = false;
        while (!landed) {
            try {
                requestLanding(PlaneID, isEmergency);
                landed = true;
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + ": Interrupted while waiting to land.");
                throw e;
            }
        }
    }

    public void TakeOff(int PlaneID) throws InterruptedException
    {
        synchronized(this) {
            waitingTakeoffPlanes++;
        }
        System.out.println(Thread.currentThread().getName() + ": Requesting runway for takeoff...");
        Thread.sleep(2000); // Simulate waiting time
        runway.acquire();
        synchronized(this) {
            waitingTakeoffPlanes--;
            notifyAll(); // Notify waiting threads that runway state changed
        }
        System.out.println("ATC: Approved "+ Thread.currentThread().getName() + " for take off.");
    }


    // Assign gates automatically after landing
    public int assignGate(int PlaneID) throws InterruptedException
    {
        gates.acquire();
        int assignedGate = -1;
        synchronized(this) {
            for (int i = 0; i < gateAvailable.length; i++) {
                if (gateAvailable[i]) {
                    gateAvailable[i] = false;
                    assignedGate = i + 1; // Gate numbers start at 1
                    break;
                }
            }
        }
        System.out.println("ATC: " + Thread.currentThread().getName() + " is assigned gate " + assignedGate + ".");
        Thread.sleep(2000); // Simulate coasting time
        System.out.println(Thread.currentThread().getName() + ": Docked at gate " + assignedGate + ".");
        return assignedGate;
    }

    public void releaseGate(int PlaneID, int gateNumber) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Leaving gate " + gateNumber + "...");
        Thread.sleep(2000); // Simulate releasing time
        synchronized(this) {
            gateAvailable[gateNumber - 1] = true;
        }
        gates.release();
    }

    public void refuel(int PlaneID) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + ": Waiting to acquire refuel truck...");
        refuelTruck.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": Acquired refuel truck.");
            Thread.sleep(2000); // Simulate refueling time
            System.out.println(Thread.currentThread().getName() + ": Refueling completed.");
        } finally {
            refuelTruck.unlock();
            System.out.println(Thread.currentThread().getName() + ": Released refuel truck.");
        }
    }
}
