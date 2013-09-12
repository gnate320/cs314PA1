import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
public class ClientProg {
	public static void main(String args[]){

		//TODO: Establish Customer Vs Admin....
		//    Air vs Sea.....

		
		SystemManager flightMan = new SystemManager(false);	
		SystemManager cruiseMan = new SystemManager(true);		

		String user = "";
		String pass = "";
		String option = "";
		boolean admin = false;
		boolean crTr = false;
		boolean again = false;
		do {
			
			admin = false;	
			System.out.print("Please enter your name: ");
			user = getInput();
			
			if (user.equalsIgnoreCase("admin"))
			{
				do {
					System.out.print("password: ");
					pass = getInput();
				
					if (pass.equals("admin123"))
						admin = true;
			
				}while (!admin);
				
			}		
			
			do {
				System.out.print("Would you like to use the Cruise system? (yes/no): ");
				option = getInput();
			} while ( !option.equalsIgnoreCase("no") && 
					  !option.equalsIgnoreCase("yes") &&
					  !option.equalsIgnoreCase("y") &&
                      !option.equalsIgnoreCase("n") );

			SystemManager man;
			if (option.equalsIgnoreCase("yes") ||
				option.equalsIgnoreCase("y") )
			{
				man = cruiseMan;
				crTr = true;
			}
			else 
			{
				man = flightMan;
				crTr = false;	
			}
			
			Client(man, admin, crTr );
			
			do {
				System.out.print("Would you like to login again? (yes/no): ");
				option = getInput();
			} while ( !option.equalsIgnoreCase("no") && 
					  !option.equalsIgnoreCase("yes") &&
					  !option.equalsIgnoreCase("y") &&
                      !option.equalsIgnoreCase("n") );

			if (option.equalsIgnoreCase("yes") ||
				option.equalsIgnoreCase("y") )
				again = true;
			else
				again = false;
			
		}while(again);
				
		

		System.out.println("Thank You, have a wonderful trip!");
	}

	static void Client(SystemManager res, boolean admin, boolean crTravel) {

		String location, company, transport;

		if (crTravel)
		{
			location = "Port";
			company = "Cruiseline";
			transport = "Cruise";
		}
		else
		{
			location = "Airport";
			company = "Airline";
			transport = "Flight";
		}

			
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
			else if (opt.equalsIgnoreCase("create") && admin)
			{
				ArrayList<String> args = new ArrayList<String>();
				if (!commParse.hasNext())
					printUsage("-c");
				else
				{
					String createWhat = commParse.next();
					args.add(createWhat);
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
						args.add(loc);
						//res.createAirport(loc);
						
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
						args.add(name);
						//res.createAirline(name);
					} 
					if (createWhat.equalsIgnoreCase(transport))
					{
						// <airline/cruiseline>
						Integer count = 1;
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
						args.add(line);
						
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
						args.add(origin);
						boolean end;
						do {	

							count++;
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
							args.add(dest);
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
							args.add(year);

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
							args.add(month);

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
							args.add(day);
						
							end  = false;
							if (crTravel)
							{
								//TODO add the port to a list of ports.
								System.out.print("Is this the final port? (yes/no): ");
								String ans = getInput();
								if (ans.equalsIgnoreCase("yes") ||
									ans.equalsIgnoreCase("y") )
									end = true; 						
							}

						}while( crTravel && !end);
						
						// <flightID//Cruise name>
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
						args.add(name);

						//attach the number of stops
						args.add(count.toString());
					}
					if (createWhat.equalsIgnoreCase("Section") && !crTravel )
					{
						String airline = "";
						if (!commParse.hasNext())
						{
							System.out.print("Enter the Airline: ");
							airline = getInput();
						}
						else
						{
							airline = commParse.next();
						}
						args.add(airline);

						String fID = "";
						if (!commParse.hasNext())
						{
							System.out.print("Enter the FlightID: ");
							fID = getInput();
						}
						else
						{
							fID = commParse.next();
						}
						args.add(fID);
							
						String rows,cols;
						if (!commParse.hasNext())
						{
							System.out.print("Enter the number of rows: ");
							rows = getInput();
						}
						else
						{
							rows = commParse.next();
						}
						args.add(rows);	
					
						if (!commParse.hasNext())
						{
							System.out.print("Enter the number of columns: ");
							cols = getInput();
						}
						else
						{
							cols = commParse.next();
						}
						args.add(cols);
						
						String secType;
						if (!commParse.hasNext())
						{
							System.out.print("Enter the section's class: ");
							secType = getInput();
						}
						else
						{
							secType = commParse.next();
						}
						args.add(secType);

					//	res.createSection(airline, fID, rows, cols, secType);
					}
					else
					{
						System.out.println("Sorry you can not create a "+opt);
					}

					res.create(args);
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

	static int getInputInt() {
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		String command;
		int num;
		try {
			command = cin.readLine();
			Scanner cscan = new Scanner(command);
			num = cscan.nextInt();
		}
		catch (Exception e) {
			System.out.println("IOException: error reading input from the command line.");
			return -1;
		}
		
		
		return num;
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
			
			System.out.print("\n With the exception of cruises, if all of the ");
			System.out.print("required information is ");
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
			//System.out.println(" create Cruise LOLOLOL");
			System.out.print(" create Section <Airline> <flightID> ");
			System.out.println("<#rows> <#columns> <class>");
			System.out.println(" create Cabin <LOLOLOLOLOLOLOLOLOL>");		
		} 
		else if (o.equalsIgnoreCase("-b"))
		{
			System.out.println("\nhelp specific to book.  syntax ect...");
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

			System.out.println("\t'Display'\n");
			System.out.print("The 'display' command allows you to view the current records of Flights or Cruises.  Display can be useful for planning a reservation.\n\n");
		}
	}	

}
