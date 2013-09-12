/* ASSIGNMENT 1
* File: FlightSection.java
* Date: 08/28/2012
*/

import java.util.LinkedList;

/*
 * The FlightSection class represents a group of seats within a Flight.
 * A FlightSection cannot exist without a parent Flight, and also must
 * have a particular section type (first, business, economy).
 * A FlightSection has a rectangular array of seats which is created at
 * initialization-time, so the FlightSection always has the right number of
 * seats (and there's no way to add more or to take them out without 
 * replacing the section).
 */

public class FlightSection extends Partition {
	
	private static final int MAXROWS = 100;
	private static final int MINROWS = 1;
	private static final int MAXCOLUMNS = 10;
	private static final int MINCOLUMNS = 1;
	
	private SeatClass type;
	private int rows;
	private int columns;
	private Flight flight;
	private boolean isEmpty;
	private Seat[][] seatArray;
	
	/*Constructor.
	 * IMPORTANT: When you instantiate a FlightSection, it automatically adds itself to the Flight that you specify.
	 * As a result, the Flight cannot be null. Also, a FlightSection is attached to the Flight you give it at initialization-time,
	 * it cannot be transfered to a new Flight. The FlightSection automatically replaced a previous FlightSection of the same
	 * type (first, business, economy), but only if the previous FlightSection has no booked seats.
	 * Any violations of the above throws a ManagementException.
	 */
	public FlightSection(Flight flightArg,  int rowsArg, int columnsArg, SeatClass typeArg) throws ManagementException
	{
		super();
		//Check to see if the flight is null
		if(flightArg == null)
		{
			throw new ManagementException("You are attempting to create a new FlightSection but gave it a null Flight. FlightSections must belong to a flight.");
		}
		//Check the bounds on seats
		if(rowsArg > MAXROWS || rowsArg < MINROWS)
		{
			throw new ManagementException("There must be at least " + MINROWS + " row and no more than " + MAXROWS + " rows in a section. You specified " 
					+ rowsArg + " rows of type " + typeArg + " on " + flightArg.getOwner() + " flight " + flightArg + ".");
		}
		
		if(columnsArg > MAXCOLUMNS || columnsArg < MINCOLUMNS)
		{
			throw new ManagementException("There must be at least " + MINCOLUMNS + " column and no more than " + MAXCOLUMNS + " columns in a section. You specified " 
					+ columnsArg + " column sof type " + typeArg + " on " + flightArg.getOwner() + " flight " + flightArg + ".");
		}
		
		//Assign variables
		flight = flightArg;
		type = typeArg;
		rows = rowsArg;
		columns = columnsArg;
		isEmpty = true;
		
		//Updates the parent Flight's sections.
		//Throws an error if you attempt to replace a section that already has booked seats.
		updateFlightSectionList();
		
		//Create array of seats and initialize them all
		seatArray = new Seat[rows][columns];
		for(int i = 0; i < rows; ++i)
		{
			for(int j = 0; j < columns; ++j)
			{
				seatArray[i][j] = new Seat(this, i+1, j+1);
			}
		}		
	}
	
	//Checks to see if a section of this type already exists in the parent flight and updates the flight's FlightSections appropriately
	public void updateFlightSectionList() throws ManagementException
	{
		boolean foundSameTypeSection = false;
		LinkedList<Partition> sectionList = flight.getPartitions(); //Just a shortcut so we don't have to keep calling the method on the right
				
		for(Partition currentSection : sectionList)
		{
			if(((FlightSection)currentSection).getType() == this.getType())
			{
				//A section of this type already exists. If it's empty, we can replace it. If it's not empty, then we can't replace it.
				foundSameTypeSection = true;
				if(((FlightSection)currentSection).isEmpty())
				{ 
					//It was empty, replace it.
					currentSection = this;
				}
				else
				{ 
					//It wasn't empty, throw an error.
					throw new ManagementException("You have attempted to replace " + this.toString() + " on flight " + ((FlightSection)currentSection).getFlight() 
							+ ", but at least one seat is already booked in that section.");
				}
			}
		}
		
		//A section of this type was not found, so we can just add this section to the list.
		if(!foundSameTypeSection)
		{
			sectionList.add(this);
		}		
	}
	
	//Returns true if at least one seat is not booked.
	public boolean hasAvailableSeat()
	{
		boolean available = false;
		
		for(Seat[] currentRow : seatArray)
		{
			for(Seat currentSeat : currentRow)
			{
				 //Check to see if current seat is booked
				if(!currentSeat.isBooked())
				{
					available = true;
					break;
				}
			}
		}
		return available;
	}
		
	//Books a seat in this section in a given row and column
	public void bookSeat(int row, char col) throws ManagementException
	{
		int x = rowToX(row);
		int y = colToY(col);
		
		seatArray[x][y].setBooked(true);
	}
	
	public void bookSeat(int row, int col) throws ManagementException
	{
		bookSeat(row,colToCol(col));
	}
	
	//toString returns Strings like "first class" or "economy class"
	public String toString()
	{
		return SeatClassToString(this.type);
	}
	
	public static String SeatClassToString(SeatClass type)
	{
		switch(type){
			case first: return "first class";
			case business: return "business class";
			case economy: return "economy class";
			default: return "unknown class";
		}
	}
	
	//Returns a string of the details for every seat
	public String seatDetails(int indents)
	{
		StringBuilder sb = new StringBuilder();
		
		for(Seat[] rowSeat : seatArray)
		{
			for(Seat colSeat : rowSeat)
			{
				//Add indentation
				for(int i = 0; i < indents; ++i)
				{
					sb.append('\t');	
				}
				//Seat information
				sb.append(colSeat.toString());
				if(colSeat.isBooked())
				{
					sb.append(" is booked");
				}
				else
				{
					sb.append(" is available");
				}
				sb.append('\n');	
			}
		}
		//remove the last new line
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	//Default indentation
	public String seatDetails()
	{
		return seatDetails(0);
	}
	
	/*====================
	   Getters & Converters
	  ====================*/
	
	public boolean isEmpty()
	{
		return isEmpty;
	}
	
	public Flight getFlight()
	{
		return flight;
	}
	
	public SeatClass getType()
	{
		return type;
	}

	public int getRows() 
	{
		return rows;
	}

	public int getColumns() 
	{
		return columns;
	}
	
	protected int rowToX(int row)
	{
		return row - 1;
	}
	
	protected int colToY(char col)
	{
		return col - 'A';
	}
	
	protected int colToY(int col)
	{
		return col - 1;
	}
	
	protected char colToCol(int col)
	{
		return (char)(col + 96);
	}
	
	protected int colToCol(char col)
	{
		return (col - 96);
	}
	
	//=====================================================================================================
	//=====================================================================================================
	//=====================================================================================================
	
	/*
	 * The Seat class represents a seat inside a FlightSection 
	 * Seats can only exist in a particular row and column of a particular flight section,
	 * it can't be instantiated without a parent FlightSection.
	 * The seat can reference its FlightSection, and through that, can know things about
	 * what kind of section (first, business, economy) and which Flight it's on.
	 */
	protected class Seat {
		/*
		The seat class tracks its X and Y position inside the FlightSection grid, which is zero based.
		Therefore a Seat's actual row is 1 more than its X and its column is 1 more than its Y (columns
		are also represented with characters). The Seat and FlightSection classes encapsulate this 
		behavior-- Just use their row and column methods to deal with rows and columns,	only interact 
		with X and Y if you know what you are doing. Note that the constructors do not take	X,Y 
		coordinates, only rows and columns.
		*/
		private int x; //row - 1
		private int y; //column - 1
		boolean booked;
		FlightSection section;

		//Constructors require that rows and columns are given as you would see them (one's based),
		//such as '1A' or '3C'
		public Seat(FlightSection sectionArg, int rowArg, char colArg) throws ManagementException
		{
			//Turn the column Char into an integer by subtracting A, then adding 1. This
			//yields the column number. If you don't add 1, you'd have the Y number.
			this(sectionArg, rowArg, colToCol(colArg));
		}
		
		//This constructor lets you give the columns as an integer, but the lowest column
		//you can give is 1.
		public Seat(FlightSection sectionArg, int rowArg, int colArg) throws ManagementException
		{
			if(rowArg < 1 || colArg < 1)
			{
				throw new ManagementException("You have attempted to create a seat with a row or column that is less than 1. "
						+ "row: " + rowArg + ", column: " + colArg);
			}
			
			x = rowToX(rowArg);
			y = colToY(colArg);
			booked = false;
			section = sectionArg;
		}

		/*Sets the booked state of the seat. Note that you must change the booking of the seat,
		you can't cancel a seat that is not booked, nor book a seat that is already booked,
		so both of those actions cause errors. There is intentionally no method to toggle the 
		booking between	available and taken, in order to reduce the possibility of making a mistake. */
		public void setBooked(boolean soldArg) throws ManagementException
		{
			if(soldArg != booked)
			{
				booked = soldArg;
			}
			else if(soldArg == true && booked == true)
			{
				throw new ManagementException("You have attempted to book seat " + getRow() + getCol() + " in " + getSection() 
						+ " on " + getSection().getFlight() + ", but this seat has already been booked.");
			}
			else if(soldArg == false && booked == false)
			{
				throw new ManagementException("You have attempted to cancel the booking on seat " + getRow() + getCol() + " in " + getSection() 
						+ " on " + getSection().getFlight() + ", but this seat is not currently booked.");
			}
		}
		
		//toString returns things like "1A" or "11C"
		public String toString()
		{
			return ( Integer.toString(getRow()) + getCol() );
		}
		
		/*==========
		   Getters
		  ==========*/
		
		public int getRow() 
		{
			return x + 1;
		}

		public char getCol() 
		{
			char letter = 'A';
			letter += y;
			return letter;
		}
		
		public int getX() 
		{
			return x;
		}

		public int getY() 
		{
			return y;
		}

		public boolean isBooked() 
		{
			return booked;
		}

		public FlightSection getSection() 
		{
			return section;
		}	
	}
}
