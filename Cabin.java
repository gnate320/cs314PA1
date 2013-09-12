import java.util.LinkedList;
public class Cabin {
	private static final int FAMILY = 5;
	private static final int SINGLE = 2;
	
	private SeatClass type;
	private int openBeds;
	private Cruise cruise;
	private boolean isEmpty;
	private Bed[] bedArray;
	
	public Cabin (Cruise cruiseArg, SeatClass typeArg) throws ManagementException{
		if(cruiseArg == null){
			throw new ManagementException("You are attempting to create a new Cabin, but gave a null Cruise.  Cabins must belong to a Cruise");
		}
		cruise = cruiseArg;
		isEmpty = true;
		bedArray = new Bed[openBeds];
		for(int i=0; i < openBeds; ++i){
			bedArray[i] = new Bed(this, i+1);
		}
		
		// check to see if a proper seatClass type is passed in //
		switch(typeArg){
			case family:
				openBeds = FAMILY;
			case single:
				openBeds = SINGLE;
				break;
			default:
				throw new ManagementException("You are attempting to add a cabin type that is not allowed on cruises.  Cabin Type: " + typeArg);
		}
		type = typeArg;
		//Add this cabin to the cruise list of cabins
		LinkedList<Cabin> cabinList = cruise.getCabins();
		cabinList.add(this);
	}
	
	public boolean hasAvailableBed() {
		switch(type){
			case single:
				for(Bed currentBed : bedArray){
					if(!currentBed.isBooked()){
						return true;
					}
				}
				break;
			case family:
				return false;
		}
		return false;
	}
	
	public void bookBed(int bedNum) throws ManagementException {
		bedArray[bedNum].setBooked(true);
	}
	
	public void cancelBed(int bedNum) throws ManagementException {
		bedArray[bedNum].setBooked(false);
	}
			
	public Cruise getCruise(){
		return cruise;
	}
	
	public SeatClass getType(){
		return type;
	}
	
	public int getBedCount(){
		return openBeds;
	}

	public String toString(){
		switch(type){
			case single: return "Single Cabin";
			case family: return "Family Cabin";
			default: return "Unknown Cabin";
		}
	}
	
	public String bedDetails(int indents){
		StringBuilder sb = new StringBuilder();
		
		for(Bed currBed : bedArray){
			//Add indentation
			for(int i = 0; i < indents; ++i)
			{
				sb.append('\t');	
			}
			//Seat information
			sb.append(currBed.toString());
			if(currBed.isBooked())
			{
				sb.append(" is booked");
			}
			else
			{
				sb.append(" is available");
			}
			sb.append('\n');	
		}
	
		//remove the last new line
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	// Class to determine how many beds are created //
	protected class Bed {
		private int bedNumber;
		private boolean booked;
		
		Cabin cabin;
		
		//Add this bed into the Current Cabin
		public Bed (Cabin cabinArg, int bed) throws ManagementException {
			
			switch(cabinArg.getType()){
				case family:
					if( bed < 1 || bed > 5)
						throw new ManagementException ("You have attempted to create a bed that is either less than 1 or greater than 5.  Bed: " + bed);
					break;
				case single:
					if(bed < 1 || bed > 2)
						throw new ManagementException ("You have attempted to create a bed that is either less than 1 or greater than 2.  Bed: " + bed);
					break;
			}
			
			bedNumber = bed;
			booked = false;
			cabin = cabinArg;
		}
		
		public void setBooked(boolean soldArg) throws ManagementException {
			if(soldArg != booked){
				booked = soldArg;
			}else if(booked == true && soldArg == true){
				throw new ManagementException ("You are attempting to book a bed: " + getBed() + "in cabin: " + getCabin() + " on " + getCabin().getCruise()
											+ ", but this bed has already been booked.");
			}else if(booked == false && soldArg == false){
				throw new ManagementException ("You are attempting to cancel a bed: " + getBed() + "in cabin: " + getCabin() + " on " + getCabin().getCruise()
											+ ", but this bed is not currently booked.");
			}
		}
		
		public String toString(){
			return ("Bed " + getBed());
		}
		public Cabin getCabin(){
			return cabin;
		}
		
		public int getBed(){
			return bedNumber;
		}
		
		public boolean isBooked(){
			return booked;
		}
	}	
}
