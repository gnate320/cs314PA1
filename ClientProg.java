import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientProg {
	public static void main(String args[]){
		SystemManager res = new SystemManager();
		
		//TODO: Establish Customer Vs Admin....
		//    Air vs Sea.....

		String location = "Airport";
		String company = "Airline";
		String transport = "Flight";

		//Create airports
		/*	res.createAirport("DEN");
		res.createAirport("DFW");
		res.createAirport("LON");
		res.createAirport("JPN");
		res.createAirport("DE"); //invalid
		res.createAirport("DEH");
		res.createAirport("DEN");
		res.createAirport("NCE");
		res.createAirport("TRIord9"); //invalid
		res.createAirport("DEN");
		//Create airlines
		res.createAirline("DELTA");
		res.createAirline("AMER");
		res.createAirline("JET");
		res.createAirline("DELTA");
		res.createAirline("SWEST");
		res.createAirline("AMER");
		res.createAirline("FRONT");
		res.createAirline("FRONTIER"); //invalid
		//Create flights
		res.createFlight("DELTA", "DEN", "LON", 2014, 10, 10, "123");
		res.createFlight("DELTA", "DEN", "DEH", 2014, 8, 8, "567");
		res.createFlight("DELTA", "DEN", "NCE", 2014, 9, 8, "567");
		//invalid
		res.createFlight("JET", "LON", "DEN", 2014, 5, 7, "123");
		res.createFlight("AMER", "DEN","LON", 2014, 10, 1, "123");
		res.createFlight("JET", "DEN", "LON", 2014, 6, 10, "786");
		res.createFlight("JET", "DEN", "LON", 2014, 1, 12, "909");
		//Create sections
		res.createSection("JET","123", 2, 2, SeatClass.economy);
		res.createSection("JET","123", 1, 3, SeatClass.economy);
		res.createSection("JET","123", 2, 3, SeatClass.first);
		res.createSection("DELTA","123", 1, 1, SeatClass.business);
		res.createSection("DELTA","123", 1, 2, SeatClass.economy);
		res.createSection("SWSERTT","123", 5, 5, SeatClass.economy);
		//invalid
		res.displaySystemDetails();
		res.findAvailableFlights("DEN", "LON");
		res.bookSeat("DELTA", "123", SeatClass.business, 1, 'A');
		res.bookSeat("DELTA", "123", SeatClass.economy, 1, 'A');
		res.bookSeat("DELTA", "123", SeatClass.economy, 1, 'B');
		res.bookSeat("DELTA", "123", SeatClass.business, 1, 'A');
		//already booked
		res.displaySystemDetails();
		res.findAvailableFlights("DEN", "LON"); */

			
		String command = "";
		do {
			
			System.out.print("Enter a command or 'help': ");
			command = getInput();			
			//System.out.println(command);

			Scanner commParse = new Scanner(command);
			String opt = commParse.next();

			if (opt.equalsIgnoreCase("help"))
			{	
				if (!commParse.hasNext())
					printUsage("");
				else
					printUsage(commParse.next());
			}
			else if (opt.equalsIgnoreCase("create"))
			{
				if (!commParse.hasNext())
					printUsage("-c");
				else
				{
					String createWhat = commParse.next();
					if (createWhat.equalsIgnoreCase(location) )
					{
						String loc = ""; 
						//no location entered so work with the user
						if (!commParse.hasNext())
						{
							System.out.print("Enter the three letter location code: ");
							loc = getInput();			
						}
						else
							loc = commParse.next();
						res.createAirport(loc);
					}
					if (createWhat.equalsIgnoreCase(company))
					{
						String name = "";
						if (!commParse.hasNext())
						{
							System.out.print("Enter the five letter company name: ");
							name = getInput();
						}
						else
							name = commParse.next();
						res.createAirline(name);
					} 
					if (createWhat.equalsIgnoreCase(transport))
					{
						// <airline/cruisline>
						String line = "";
						if (!commParse.hasNext())
						{
							System.out.print("Enter the " + transport + " company: ");
							line = getInput();
						} 
						else
						{
							line = commParse.next();
						}

						//origin
						String origin = "";
						if (!commParse.hasNext())
						{
							System.out.print("Enter the " + transport + " origin: ");
							origin = getInput();
						} 
						else
						{
							origin = commParse.next();
						}						

						boolean end;
						do {	

							//<destination>
							String dest = "";
							if (!commParse.hasNext())
							{
								System.out.print("Enter the next stop: ");
								dest = getInput();
							} 
							else
							{
								dest = commParse.next();
							}

							// <year>
							String year = "";
							if (!commParse.hasNext())
							{
								System.out.print("Enter the " + transport + " year: ");
								year = getInput();
							} 
							else
							{
								year = commParse.next();
							}

							// <month>
							String month = "";
							if (!commParse.hasNext())
							{
								System.out.print("Enter the " + transport + " month: ");
								month = getInput();
							} 
							else
							{
								month = commParse.next();
							}

							// <day> 
							String day = "";
							if (!commParse.hasNext())
							{
								System.out.print("Enter the " + transport + " day: ");
								day = getInput();
							} 
							else
							{
								day = commParse.next();
							}
						
							end  = false;
							if (transport.equalsIgnoreCase("Cruise"))
							{
								System.out.print("Is this the final port? (yes/no): ");
								String ans = getInput();
								if (ans.equalsIgnoreCase("yes") ||
									ans.equalsIgnoreCase("y") )
									end = true; 						
							}

						}while(transport.equalsIgnoreCase("Cruise") && end == false);
						
						// <flightID>
						String name = "";
						if (!commParse.hasNext())
						{
							System.out.print("Enter the " + transport + " name: ");
							name = getInput();
						} 
						else
						{
							name = commParse.next();
						}

						 
					}
				}
			}
			else if (opt.equalsIgnoreCase("book"))
			{	

			}
			else if (opt.equalsIgnoreCase("display"))
			{
				res.displaySystemDetails();
			}
				
		}while(!command.equalsIgnoreCase("quit") &&
               !command.equalsIgnoreCase("q") );

				
			
	}
	
	static String getInput() {
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		String command;
		try {
			command = cin.readLine();
		}
		catch (IOException e) {
			System.out.println("IOException: error reading input from the command line.");
			command = "";
		}
		
		return command;
	}

	static void printUsage(String o) {
		
		
		if (o.equalsIgnoreCase("-c"))
		{
			System.out.println("\nCreate:");
			System.out.print("Create can be used to add Airports, Ports ");
			System.out.print("Airlines, Cruiselines, Flights, Cruises, ");
			System.out.println(" flight sections, and Cruise Cabins.");
			System.out.print("\n The system can guide you through ");
			System.out.print("creating a record, but the create command ");
			System.out.println("must contain the object type to create.");
			System.out.println("\nExamples:");
			System.out.println(" create Airport");
			System.out.println(" create Port");
			System.out.println(" create Airline");
			System.out.println(" create Cruiseline");
			System.out.println(" create Flight");
			System.out.println(" create Cruise");
			System.out.println(" create Section");
			System.out.println(" create Cabin");
			
			System.out.print("\n If all of the required information is");
			System.out.print("available, it can be entered in one line.");
			System.out.println("The details must be in the correct order.");
			System.out.println("\nExamples:");
			System.out.println(" create Airport <name>");
			System.out.println(" create Port <name>");
			System.out.println(" create Airline <name>");
			System.out.println(" create Cruiseline <name>");
			System.out.print(" create Flight <name> <origin> ");
			System.out.print("<destination> <year> <month> <day> ");
			System.out.println("<flightID> ");
			System.out.println(" create Cruise LOLOLOL");
			System.out.print(" create Section <Airline> <flightID> ");
			System.out.println("<#rows> <#columns> <class>");
			System.out.println(" create Cabin <LOLOLOLOLOLOLOLOLOL>");		
		} 
		else if (o.equalsIgnoreCase("-b"))
		{
			System.out.println("\nhelp specific to book.  syntax ect...");
		}
		else if (o.equalsIgnoreCase("-d"))
		{
			System.out.println("\nhelp specific to display.  syntax ect...");
		}
		else
		{
			System.out.println("\n\n\t\t**Welcome to the Travel Manager!**");
			System.out.println("You can use the travel manager to store and retrive Flight and Cruise information.");
			System.out.println("Here are some of the ways the system can be used: \n");
			System.out.println("\t 'Create' <Location/Company/Trip/Section> ...\n");
			System.out.print("The 'create' command can be used to make a new record ");
			System.out.print("for a location, company, trip, or section.  ");
			System.out.print("It is important to have all of the required information ");
	    	System.out.print("before creating a record.  For help with 'create' enter ");
			System.out.print("'help -c'.\n\n" );
		
			System.out.println("\t 'Book' <Flight/Cruise> ...\n");
			System.out.print("The 'book' command allows you to reserve passage on a flight or cruise.  ");
		    System.out.print("It is important to have all of the required information when booking a trip.  ");
			System.out.print("For help with 'book' enter 'help -b'.\n\n");

			System.out.println("\t'Display' <Flight/Cruise>\n");
			System.out.print("The 'display' command allows you to view the current records of Flights or Cruises.  Display can be useful for planning a reservation. For help with Display enter 'help -d'.\n\n");
		}
	}	

}
