import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Cruise extends Transportation {
	
	//private LinkedList<Cabin> cabins;
	private LinkedList<Port> STOPS;
	
	public Cruise(Cruiseline ownerArg, LinkedList<Port> stops, Calendar dateArg, String idArg) throws ManagementException
	{
		super(ownerArg, stops.getFirst(), stops.getLast(), dateArg, idArg);
		
		//cabins = new LinkedList<Cabin>();
		//((Cruiseline)owner).addTrip(this);
	
	}
	
	public boolean hasAvailableBed() {
		for(Partition currentCabin : myPartitions ){
			if(((Cabin)currentCabin).hasAvailableBed()) return true;
		}
		return false;
	}
	
	public void bookBed(SeatClass type, int bedNum) throws ManagementException {
		Cabin desiredCabin = null;
		for( Partition currCabin : myPartitions ){
			if( ((Cabin)currCabin).getType() == type && ((Cabin)currCabin).hasAvailableBed() ){
				desiredCabin = (Cabin)currCabin;
				break;
			}
		}
		if(desiredCabin == null){
			throw new ManagementException("You tried to book a bed on " + this.toString() + ", in a " + 
						type + " style cabin, but the Cabin you specified does not exist or all " + type + " cabins are full." );
		}
		
		desiredCabin.bookBed(bedNum);
	}
	
	//TODO create a method call that returns the list of stops, and one to return the list of cabins
	public LinkedList<Port> getStops(){
		return STOPS;
	}
	/*
	public LinkedList<Cabin> getCabins(){
		return cabins;
	} //*/
	
}
