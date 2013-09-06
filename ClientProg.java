public class ClientProg {
	public static void main(String args[]){
	SystemManager res = new SystemManager();
	//Create airports
	res.createAirport("DEN");
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
	res.findAvailableFlights("DEN", "LON");
	}
}
