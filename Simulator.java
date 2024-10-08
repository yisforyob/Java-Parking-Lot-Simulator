/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * 3600;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * 3600;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
			MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 * This probability is calculated in the constructor based on the perHourArrivalRate
	 * passed to the constructor.
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second.
	 */
	private int clock;

	/**
	 * Total number of steps (simulated seconds) that the simulation should run for.
	 * This value is fixed at the start of the simulation. The simulation loop
	 * should be executed for as long as clock < steps. When clock == steps, the
	 * simulation is finished.
	 */
	private int steps;

	/**
	 * Instance of the parking lot being simulated.
	 */
	private ParkingLot lot;

	/**
	 * Queue for the cars wanting to enter the parking lot
	 */
	private Queue<Spot> incomingQueue;

	/**
	 * Queue for the cars wanting to leave the parking lot
	 */
	private Queue<Spot> outgoingQueue;

	/**
	 * @param lot                 is the parking lot to be simulated
	 * @param perHourArrivalRate  is the HOURLY rate at which cars show up in front of the lot
	 * @param steps               is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) {

		this.lot = lot;

		this.steps = steps;

		this.clock = 0;
		
		// YOUR CODE HERE! YOU SIMPLY NEED TO COMPLETE THE LINES BELOW:

		// What should the two questions marks be filled with? 
		// Hint: you are being given a perHourArrivalRate. 
		// All you need to do is to convert this hourly rate into 
		// a per-second rate (probability).
		
		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, 3600);

		
		// Finally, you need to initialize the incoming and outgoing queues

		incomingQueue = new LinkedQueue <Spot>();
		outgoingQueue = new LinkedQueue <Spot>();

	}

	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable.
	 * In this method, you will implement the algorithm shown in Figure 3 of the A2 description.
	 */
	public void simulate() {
	
		// Local variables can be defined here.

		this.clock = 0;
		// Note that for the specific purposes of A2, clock could have been 
		// defined as a local variable too.
		
                boolean check = true;    // determine whether the car dequeued from the incomingQueue has been parked.
		Spot in = null;      
		// these local variables used from line 141 to 153
		
		while (clock < steps) {
	
			// WRITE YOUR CODE HERE!
			boolean carArrives = RandomGenerator.eventOccurred(probabilityOfArrivalPerSec); 
			if (carArrives){
				Car car = RandomGenerator.generateRandomCar();
				Spot s = new Spot(car, clock);
				incomingQueue.enqueue(s);	
			}
			
			
			for (int i = 0; i < lot.getNumRows(); i++){
				for (int j = 0; j < lot.getNumSpotsPerRow(); j++){
					if (lot.getSpotAt(i,j) != null){
						int parkDuration = clock - lot.getSpotAt(i,j).getTimestamp();   // calculate the duration that c has been parked
					
					    if (parkDuration == MAX_PARKING_DURATION){
							Spot s = lot.remove(i,j);    // remove the car from the lot
							outgoingQueue.enqueue(s);    // place it in the outgoing queue  
						    
						    
					    }else{
						    boolean carDeparts = RandomGenerator.eventOccurred(departurePDF.pdf(parkDuration));
						    if(carDeparts){
								Spot s = lot.remove(i,j);
							    outgoingQueue.enqueue(s);
							    
						    }
					    }
				    }
				}
			}
			
			
			if (!incomingQueue.isEmpty() && check){    
			    in = incomingQueue.dequeue();
				check = lot.attemptParking(in.getCar(), in.getTimestamp());  // attempt to park the car dequeued
				if (check){
					System.out.println(in.getCar() + " ENTERED at timestep " + clock + "; occupancy is a " + lot.getTotalOccupancy());
				}
			}else if (!check){
				check = lot.attemptParking(in.getCar(), in.getTimestamp());  // checks whether an appropriate spot has been freed since the last iteration
				if (check){
					System.out.println(in.getCar() + " ENTERED at timestep " + clock + "; occupancy is a " + lot.getTotalOccupancy());
				}
				
			}
			
			if(!outgoingQueue.isEmpty()){
				Spot out = outgoingQueue.dequeue();
				System.out.println(out.getCar() + " EXITED at timestep " + clock + "; occupancy is a " + lot.getTotalOccupancy());
			}

			clock++;
		}
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the parking-lot design. Next, it simulates the parking lot
	 * for a number of steps (this number is specified by the steps parameter). At
	 * the end, the method prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();
		
		if (args.length < 2) {
			System.out.println("Usage: java Simulator <lot-design filename> <hourly rate of arrival>");
			System.out.println("Example: java Simulator parking.inf 11");
			return;
		}

		if (!args[1].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		ParkingLot lot = new ParkingLot(args[0]);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		Simulator sim = new Simulator(lot, Integer.parseInt(args[1]), SIMULATION_DURATION);

		long start, end;

		System.out.println("=== SIMULATION START ===");
		start = System.currentTimeMillis();
		sim.simulate();
		end = System.currentTimeMillis();
		System.out.println("=== SIMULATION END ===");

		System.out.println();

		System.out.println("Simulation took " + (end - start) + "ms.");

		System.out.println();

		int count = 0;

		// The Queue interface does not provide a method to get the size of the queue.
		// We thus have to dequeue all the elements to count how many elements the queue has!
		
		while (!sim.incomingQueue.isEmpty()) {
			sim.incomingQueue.dequeue();
			count++;
		}

		System.out.println("Length of car queue at the front at the end of simulation: " + count);
	}
}
