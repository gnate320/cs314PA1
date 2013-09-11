/* ASSIGNMENT 1
* File: Flight.java
* Date: 08/28/2012
*/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * A Flight represents one connection between airports (it's an edge
 * in a graph of airport nodes). The Flight can have up to three FlightSections,
 * but it doesn't necessarily have any. The Flight cannot have any seats without having
 * FlightSections. A Flight also must belong to an airline. 
 */

public class Flight extends Transportation {

	//Define the maximum and minimum number of ID characters,
	//public final static int MAXIDCHARS = 6;
	//public final static int MINIDCHARS = 3;
	
	//All flights must be scheduled after this day
	//public final static int AFTERYEAR = 2012;
	//public final static int AFTERMONTH = 12;
	//public final static int AFTERDAY = 31;
	//public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	//private String id;
	//private Airline owner;
	//private Airport origin;
	//private Airport destination;
	//private Calendar date;
	private LinkedList<FlightSection> sections;
	
	/*Constructs new Flight objects.
	*There are restrictions on flight ids, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Flight(Airline ownerArg, Airport originArg, Airport destinationArg, Calendar dateArg, String idArg) throws ManagementException
	{
		super(ownerArg, originArg, destinationArg, dateArg, idArg);
		
		sections = new LinkedList<FlightSection>();
		
		//Add the flight to the Airline. This will throw an error if the airline already has a flight with this id.
		//This constructor will throw that error again up to whoever called it.
		((Airline)owner).addFlight(this);	
	}
	
	
	//Returns true if there is at least one available seat in any section
	public boolean hasAvailableSeat()
	{
		boolean seatsAvailable = false;
		
		for(FlightSection section : sections)
		{
			if(section.hasAvailableSeat()) seatsAvailable = true;
		}
		return seatsAvailable;
	}
	
	//Books a seat in the given row and column of the given section
	public void bookSeat(SeatClass sectionType, int row, char col) throws ManagementException
	{
		FlightSection desiredSection = null;
		
		//This for loop looks for the section we are trying to book the seat in
		for(FlightSection currentFlightSection : sections)
		{
			if(currentFlightSection.getType() == sectionType)
			{
				desiredSection = currentFlightSection;
			}
		}
		
		//If we didn't find the section, throw an error
		if(desiredSection == null) throw new ManagementException("You tried to book a seat on " + this.toString() 
				+ ", in section " + FlightSection.SeatClassToString(sectionType) + ", but the section you specified does not exist.");
		
		//Everything went well, try to book the seat.
		desiredSection.bookSeat(row, col);
	}
	
	
	
	public LinkedList<FlightSection> getFlightSections()
	{
		return sections;
	}	
}
