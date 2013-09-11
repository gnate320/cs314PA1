import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.LinkedList;

public class Cruise extends Company {

	private Hashtable<String, Trip> myTrips;

	public Cruise (String idArg) throws ManagementException {
		super(idArg);
		myTrips = new Hashtable<String, Trip>();
	}
	
	public void addTrip(Trip tripArg) throws ManagementException
	{
		//Check if the flight object is null
		if(tripArg == null)
		{
			throw new ManagementException("You tried to assign a null trip to cruise " + toString());
		}
		
		//We just need to check if this airline already has a flight with that number.
		Trip existingTrip = myTrips.get(tripArg.getId());
		if(existingTrip != null)
		{
			//Then the flight must already exist if existingFlight is not null
			throw new ManagementException("You are attempting to add " + tripArg + ", but a trip with that ID is already registered with that cruise. " +existingTrip.getId() +
										existingTrip.getDestination());
		}
		
		//If the above test passed, then we can accept the flight for this Airline.
		myTrips.put(tripArg.getId(), tripArg);
	}
	
	
	
	public Trip findTrip(String idArg) throws NullPointerException
	{
		//Check if the string is null
		if(idArg == null)
		{
			throw new NullPointerException("You are attempting to look up a trip belonging to cruise " + toString() 
					+ ", but the trip ID is null.");
		}
		idArg = idArg.toUpperCase();
		return myTrips.get(idArg);
	}
	
	public LinkedList<Trip> findAvailableTrips(Port originPort, Port destinationPort) throws ManagementException
	{
		//Check if the airports are null
		if(originPort == null || destinationPort == null)
		{
			throw new ManagementException("You are attempting to find flights from one airport to another, but either the destination or origin airport is null, or both. Origin: "
					+ originPort + ", Destination: " + destinationPort);
		}
			
		//Create an empty list of acceptable flights and convert the flight hash to linked list for easy iteration
		LinkedList<Trip> acceptableTripList = new LinkedList<Trip>();
		LinkedList<Trip> allTripList = SystemManager.hashtableToLinkedList(myTrips);
			
		for(Trip currentTrip : allTripList)
		{
			//If the current flight matches the given criteria, add it into the list of acceptable flights
			if(currentTrip.getOrigin() == originPort && currentTrip.getDestination() == destinationPort && currentTrip.hasAvailableSeat())
			{
				acceptableTripList.add(currentTrip);
			}
		}
		
		return acceptableTripList;
	}
	
	
	public Hashtable<String, Trip> getTrips(){
		return myTrips;
	}

}
