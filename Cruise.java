import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Cruise extends Transportation {
	
	private LinkedList<Cabin> cabins;
	private LinkedList<Port> STOPS;
	
	public Cruise(Cruiseline ownerArg, LinkedList<Port> stops, Calendar dateArg, String idArg) throws ManagementException
	{
		super(ownerArg, stops.getFirst(), stops.getLast(), dateArg, idArg);
		
		cabins = new LinkedList<Cabin>();
		((Cruiseline)owner).addTrip(this);
	
	}
	
	public boolean hasAvailableBed() {
		for(Cabin currentCabin : cabins){
			if(currentCabin.hasAvailableBed()) return true;
		}
		return false;
	}
	
	public void bookBed(SeatClass type, int bedNum) throws ManagementException {
		Cabin desiredCabin = null;
		for( Cabin currCabin : cabins ){
			if( currCabin.getType() == type && currCabin.hasAvailableBed() ){
				desiredCabin = currCabin;
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
	
	public LinkedList<Cabin> getCabins(){
		return cabins;
	}
	
}
