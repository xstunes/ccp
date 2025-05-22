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
            if (isEmergency)
            {
                System.out.println("Plane: " + PlaneID + ": Emergency landing requested.");
                airport.requestLanding(PlaneID); //request landing
            }
            else
            {
                Random random = new Random();
                boolean landed = false;
                long arrivalTime = System.currentTimeMillis(); //record when plane arrives
                while (!landed) {
                    // Random chance to reject landing (e.g., 30% chance)
                    if (random.nextInt(100) < 30) {
                        airport.rejectLanding(PlaneID); //reject landing
                    } else {
                        airport.requestLanding(PlaneID);
                        //simulate landing time
                        Thread.sleep(1000);
                        long waitingTime = System.currentTimeMillis() - arrivalTime; //calculate waiting time
                        airport.releaseRunway(PlaneID);
                        statistics.recordPlane(waitingTime); //record waiting time
                        landed = true;
                    }
                }
            }

            airport.requestGate(PlaneID); //request gate

            //simulate disembark and cleaning time
            System.out.println("Plane: " + PlaneID + ": Disembarking "+ passengers + " passengers.");
            Thread.sleep(1000);
            System.out.println("Plane: " + PlaneID + ": Cleaning the plane.");
            Thread.sleep(1000);

            airport.refuel(PlaneID); //request refuel
            System.out.println("Plane: " + PlaneID + ": Embarking "+passengers+ " passengers.");
            Thread.sleep(1000);

            airport.releaseGate(PlaneID); //release gate
            System.out.println("Plane: " + PlaneID + ": Taking off.");
            Thread.sleep(1000);
            airport.releaseRunway(PlaneID); //release runway
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}
