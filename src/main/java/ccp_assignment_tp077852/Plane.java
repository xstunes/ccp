package ccp_assignment_tp077852;
//libraries

import java.util.Random;

public class Plane extends Thread{
    //object
    private final int PlaneID;
    private final Airport airport;
    private final Statistics statistics;
    private final boolean isEmergency;
    private final int passengers; //max number of passengers

    //constructor
    public Plane(int PlaneID, Airport airport, Statistics statistics, boolean isEmergency, int passengers) {
        this.PlaneID = PlaneID;
        this.airport = airport; //refer to airport
        this.statistics = statistics; //refer to stats
        this.isEmergency = isEmergency; //is it an emergency?
        this.passengers = new Random().nextInt(50); //max number of passengers

        setName("Thread-" + PlaneID);
    }

    @Override
    public void run()
    {
        try
        {
            long startWait = System.currentTimeMillis();

            // Random sleep to simulate random arrival time
            Thread.sleep(new Random().nextInt(2000));

            if (isEmergency)//emergency airplane
            {
                System.out.println(Thread.currentThread().getName() + ": Emergency landing requested.");
                airport.requestLanding(PlaneID, isEmergency); //request landing
                Thread.sleep(1500); //wait for 1.5 second
                airport.releaseRunway(PlaneID); //release runway

            }
            else if(!isEmergency) //if not an emergency
            {
                airport.requestLanding(PlaneID, isEmergency); // request landing directly without rejection
                Thread.sleep(1000); //wait for 1 second
                airport.releaseRunway(PlaneID); //release runway
            }

            int assignedGate = airport.assignGate(PlaneID); // request and get assigned gate

            //simulate disembark and cleaning time
            System.out.println(Thread.currentThread().getName() + ": Disembarking "+ passengers + " passengers.");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + ": Cleaning the plane.");
            Thread.sleep(1000);

            airport.refuel(PlaneID); //request refuel
            System.out.println(Thread.currentThread().getName() + ": Embarking "+passengers+ " passengers.");
            Thread.sleep(1000);

            airport.releaseGate(PlaneID, assignedGate); //release gate
            
            airport.TakeOff(PlaneID); //request takeoff
            System.out.println(Thread.currentThread().getName() + ": Taking off.");
            Thread.sleep(1000);
            airport.releaseRunway(PlaneID); //release runway

            long endWait = System.currentTimeMillis();
            long waitingTime = endWait - startWait;
            statistics.recordPlane(waitingTime, passengers);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}
