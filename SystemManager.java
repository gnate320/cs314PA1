//Edited by
//Nate Gillard and Andrew Benevideas
//Date 9/11/2013

/* ASSIGNMENT 1
* File: SystemManager.java
* Date: 08/28/2012
*/

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
/* The SystemManager is the controlling mechanism for
 * Airport, Airline, Flight, FlightSection, and Seat.
 * The SystemManager will convert their parameters into a 
 * more Object-Oriented form before passing them onto the
 * other classes.
 */
public class SystemManager {

	private Hashtable<String, TransportStation> locationDictionary;
	private Hashtable<String, Company> companyDictionary;
	boolean crTr;

	public SystemManager()
	{
		locationDictionary = new Hashtable<String, TransportStation>();
		companyDictionary = new Hashtable<String, Company>();
	}
	
	public SystemManager(boolean crT)
	{
		locationDictionary = new Hashtable<String, TransportStation>();
		companyDictionary = new Hashtable<String, Company>();
		crTr = crT;
	}

	public boolean create(ArrayList<String> args) {
	
		if (args.get(0).equalsIgnoreCase("Airport") &&
			args.size() == 2)
			createAirport(args.get(1));
		else if (args.get(0).equalsIgnoreCase("Port") &&
                 args.size() == 2)
			createPort(args.get(1));
		else if (args.get(0).equalsIgnoreCase("Airline") &&
                 args.size() == 2)
			createAirline(args.get(1));
		else if (args.get(0).equalsIgnoreCase("Cruiseline") &&
                args.size() == 2)
			createCruiseline(args.get(1));
		else if (args.get(0).equalsIgnoreCase("Flight") && 
				args.size() == 8)
		{
			createFlight(args.get(0), args.get(1), args.get(2), 
							Integer.parseInt(args.get(3)), 
							Integer.parseInt(args.get(4)),
							Integer.parseInt(args.get(5)),
							args.get(6));
		}
		else if (args.get(0).equalsIgnoreCase("Cruise") && 
		args.size() < 6)
		{
			LinkedList<Port> stops;
			int stopCount = Integer.parseInt(args.get(args.size()-1));
			for (int i = 0; i < stopCount; i++)
			{
				String next = args.get(2+i*4);
				//int y = Integer.parseInt(args[3+i*4]);
				//int m = Integer.parseInt(args[4+i*4]);
				//int d = Integer.parseInt(args[5+i*4]);
				stops.add(new Port(next));
			}
			createCruise(args.get(1), stops,
				 args.get(3), args.get(4), args.get(5),
				args.get(args.size() - 2));
		}
		else if (args.get(0).equalsIgnoreCase("section") && 
				args.size() == 6)
		{
			int r = Integer.parseInt(args.get(3));
			int c = Integer.parseInt(args.get(4));
			createSection(args.get(1), args.get(2),
							 r, c, interpSectClass(args.get(5)));
		}
		else if (args.get(0).equalsIgnoreCase("cabin"))
		{
			//TODO
		}
	}

	//Creates a new Airport. Returns null if there is an error during construction.
	Airport createAirport(String idArg)
	{
		Airport newAirport;

		try
		{
			newAirport = new Airport(idArg);
			locationDictionary.put(idArg, newAirport);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newAirport = null;
		}

		return newAirport;
	}
	
	Port createPort(String idArg)
	{
		Port newPort;

		try 
		{
			newPort = new Port(idArg);
			locationDictionary.put(idArg, newPort);
		}
		catch (ManagementException me)
		{
			System.out.print(me);
			newPort = null;
		}
		
		return newPort;
	}
	
	//Creates a new Airline. Returns null if there is an error during construction.
	Airline createAirline(String idArg)
	{
		Airline newAirline;

		try
		{
			newAirline = new Airline(idArg);
			companyDictionary.put(idArg, newAirline);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newAirline = null;
		}

		return newAirline;
	}
	
	//TODO:
	Cruiseline createCruiseline(String idArg) 
	{
		Cruiseline newCruiseline;
		
		try
		{
			newCruiseline = new Cruiseline(idArg);
			companyDictionary.put(idArg, newCruiseline);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newCruiseline = null;
		}

		return newCruiseline;
	}

	//Creates a flight given the name of an airline, origin airport, destination airport, date, and flight id.
	//All parameters are converted to object oriented format before being passed to their respective classes
	Flight createFlight(String airlineName, String origAirport, String destAirport, int year, int month, int day, String flightIdArg)
	{
		//Create a calendar object given the date arguments
		Calendar date = Calendar.getInstance();
		try
		{
			date.set(year, month, day);
		}
		catch(Exception e)
		{
			//If a bad date is passed in, like Feb 30th, we want to catch any errors
			System.out.println("You are attempting to create a flight with ID " + flightIdArg + " on an invalid date, " +year+"/"+month+"/"+day);
		}

		Flight newFlight;

		try
		{
			//Turn airport & airline strings into airport & airline objects
			TransportStation origPort = findLocation(origAirport);
			TransportStation destPort = findLocation(destAirport);
			Company airline = findCompany(airlineName);
			
			//The Flight constructor adds the given flight to the owning airlines flight list
			newFlight = new Flight( (Airline) airline, (Airport) origPort,(Airport) destPort, date, flightIdArg);
			
			//This was Nate ane Andrew
			//airline.addFlight(newFlight);
		}
		catch(Exception e)
		{
			//If lookups on airports or airlines return no result, or the flight creation failed, then an error was thrown... return null
			System.out.println(e);
			newFlight = null;
		}

		return newFlight;
	}
	
	//TODO
	Cruise createCruise() {}

	//Creates a seating section given the airline name, flight id, number of rows and columns and the class of the seating section
	FlightSection createSection(String airlineName, String flightID, int rows, int cols, SeatClass sectionType)
	{
		FlightSection newSection;

		try
		{
			//Lookup airline and flight
			Airline air = (Airline) findCompany(airlineName);
			Flight flight =(Flight) air.findFlight(flightID);
			newSection = new FlightSection(flight, rows, cols, sectionType);
		}
		catch(Exception e)
		{
			//If lookups on airline or flight return no result, or the section creation failed, then an error was thrown... return null
			System.out.println(e);
			newSection = null;
		}

		return newSection;
	}
	
	//Returns a string array of flights that have at least one available seat and are flying from the origin airport to a specific destination
	String[] findAvailableTransportation(String originName, String destinationName)
	{
		//Check to make sure strings are not null before converting them to objects
		if(originName == null || destinationName == null)
		{
			System.out.println("You are attempting to find a flight with either a null origin or null destination, or both."
					+ "\nOrigin is " + originName + ", destination is " + destinationName + ".");
		}
		
		String[] transportIds;
		
		try
		{
			//Look up airports
			TransportStation originObj = findLocation(originName);
			TransportStation destinationObj = findLocation(destinationName);

			//Create a linked list of all the flights from all airlines that satisfy the criteria
			LinkedList<Transportation> transportList = new LinkedList<Transportation>();
			LinkedList<Company> companyList = hashtableToLinkedList(companyDictionary);
			
			//Look through all airlines and add those that have flights that satisfy criteria
			for(Company currentCompany : companyList)
			{
				LinkedList<Transportation> validTransport =  currentCompany.findAvailableTransport(originObj, destinationObj);
				transportList.addAll(validTransport);
			}
			
			//Find the length of the linked list of validFlights and create a string array
			int listLength = transportList.size();
			 transportIds = new String[listLength];

			//Put the names of all the flights into the string array and then return it
			int i = 0;
			for(Transportation entry : transportList)
			{
				transportIds[i] = entry.toString();
				++i;
			}
		}
		catch(ManagementException me)
		{
			//If lookups on the airports failed, then an error was thrown... return null
			System.out.println(me);
			transportIds = null;
		}

		if(transportIds != null)
		{
			//Print all flights that match that given criteria
			for(String currentTransport : transportIds)
			{
				System.out.println(currentTransport);
			}
		}
		
		return transportIds;
	}

	//Attempt to book a seat on a given airline's flight in the row and column of a given section
	boolean bookSeat(String airlineName, String flightID, SeatClass sectionType, int row, char column)
	{
		//Check to make sure strings are not null before converting them to objects
		if(airlineName == null) System.out.println("You are attempting to book a seat, but no airline was specified.");
		if(flightID == null) System.out.println("You are attempting to book a seat, but no flight was specified.");

		boolean bookingSuccess;

		try
		{
			//Look up airline and flight
			Airline airline = (Airline) findCompany(airlineName);
			Flight flight = airline.findFlight(flightID);
			//System.out.println(sectionType);
			//System.out.println(row);
			//System.out.println(column);
			flight.bookSeat(sectionType, row, column);
			bookingSuccess = true;
		}
		catch(ManagementException me)
		{
			//If look up on the airline or flight failed, or the seat was unable to be booked, throw an error... return false
			System.out.println(me);
			bookingSuccess = false;
		}

		return bookingSuccess;
	}

	//Print out details of all objects
	void displaySystemDetails()
	{
		System.out.println("___Airports___");
		LinkedList<TransportStation> locationList = hashtableToLinkedList(locationDictionary);
		for(TransportStation currentLocation : locationList)
		{
			//Print all airports
			System.out.println(currentLocation);
		}

		System.out.println("\n___Airlines___");
		LinkedList<Company> companyList = hashtableToLinkedList(companyDictionary);
		for(Company currentCompany : companyList)
		{
			//Print all Transports
			System.out.println(currentCompany);
			
			LinkedList<Transportation> transportationList = hashtableToLinkedList(currentCompany.getTransports());
			for(Transportation currentTransport : transportationList)
			{
				//Print all flights for a given airline
				System.out.println("\tFlight " + currentTransport.getId() + " from " + currentTransport.getOrigin() + " to " 
						+ currentTransport.getDestination() + " on " + currentTransport.getDateString());
				if (!crTr)
				{
					LinkedList<FlightSection> partList = currentTransport.getPartitions();
					for(Partition currentSection : partList)
					{
						//Print all sections and seats for a given flight
						System.out.println("\t\t" + currentSection);
						System.out.println(currentSection.seatDetails(3));	
					}
				}
				else
				{
					//TODO loop cabins and print
				}
			}
		}
	}

	//Find an airport object given the string name. Cannot be given null strings.
	public TransportStation findLocation(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an airport, but the airport name is null.");
		}
		name = name.toUpperCase();
		TransportStation loc = locationDictionary.get(name);
		if(air == null)
		{
			throw new ManagementException("You have attempted to look up an airport that does not exist. The name you gave was " +name);
		}
		return loc;
	}

	//Find an airline object given the string name. Cannot be given null strings.
	public Company findCompany(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an airline, but the airline name is null.");
		}
		name = name.toUpperCase();
		Company comp = companyDictionary.get(name);
		if(comp == null)
		{
			throw new ManagementException("You have attempted to look up an airline that does not exist. The name you gave was " +name);
		}
		return comp;
	}

	//Converts hash tables to linked lists for easier iteration. This is static so that other classes can call this method,
	//and you can use this method even if you aren't otherwise using the SystemManager.
	static <KeyType, ValueType> LinkedList<ValueType> hashtableToLinkedList(Hashtable<KeyType,ValueType> hash)
	{
		/*This next section was inspired by Adamski's answer to a question on Stackoverflow:
		stackoverflow.com/question/2351331/iterating-hashtable-in-java
		 */
		LinkedList<ValueType> newList = new LinkedList<ValueType>();
		Iterator<Entry<KeyType, ValueType>> iter = hash.entrySet().iterator();

		while(iter.hasNext())
		{
			Map.Entry<KeyType, ValueType> entry = iter.next();
			ValueType current = entry.getValue();

			newList.add(current);
		}
		return newList;
	}

		static SeatClass interpSectClass(String in)
		{
			do {
				if ( in.equalsIgnoreCase("b") ||
					in.equalsIgnoreCase("bus") ||
					in.equalsIgnoreCase("business") )
					return SeatClass.business;
				else if (in.equalsIgnoreCase("e") ||
						in.equalsIgnoreCase("ec") ||
						in.equalsIgnoreCase("econ") ||
						in.equalsIgnoreCase("economy") )
					return SeatClass.economy;
				else if (in.equalsIgnoreCase("f") ||
						in.equalsIgnoreCase("fir") ||
						in.equalsIgnoreCase("1st") ||
						in.equalsIgnoreCase("first") )
					return SeatClass.first;
				else
				{
					System.out.println("Invalid Section class!");
					System.out.print("Enter 'econ', 'bus', or 'first': ");
				}

			} while (true);
		}
}
