import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Trip extends Transportation {
	
	private LinkedList<Cabins> cabins;
	
	public Trip(Cruise ownerArg, Cruise originArg, Cruise destinationArg, Calendar dateArg, String idArg) throws ManagementException
	{
		super(ownerArg, originArg, destinationArg, dateArg, idArg);
		
		cabins = new LinkedList<Cabins>();
		((Cruise)owner).addTrip(this);
	
	}
}
