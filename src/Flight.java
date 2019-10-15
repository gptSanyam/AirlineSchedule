public class Flight {

    private final String airline;
    private final int flight_number;
    private final String tail_number;
    private final String orig_airport;
    private final String dest_airport;
    private final int departure;
    private final int time;
    private final int distance;
    private final int arrival;

    ////AIRLINE	FLIGHT_NUMBER	TAIL_NUMBER	ORIGIN_AIRPORT	DESTINATION_AIRPORT	SCHEDULED_DEPARTURE	SCHEDULED_TIME	DISTANCE	SCHEDULED_ARRIVAL
    //    //US	840	N171US	SFO	CLT	20	286	2296	806
    Flight(String rawFlightDetail) {
        String[] flightDetails = rawFlightDetail.split(",");
        this.airline = flightDetails[0];
        this.flight_number = Integer.parseInt(flightDetails[1]);
        this.tail_number = flightDetails[2];
        this.orig_airport = flightDetails[3];
        this.dest_airport = flightDetails[4];
        this.departure = Integer.parseInt(flightDetails[5]);
        this.time = Integer.parseInt(flightDetails[6]);
        this.distance = Integer.parseInt(flightDetails[7]);
        this.arrival = Integer.parseInt(flightDetails[8]);
    }

    public String getAirline() {
        return airline;
    }

    public int getFlight_number() {
        return flight_number;
    }

    public String getTail_number() {
        return tail_number;
    }

    public String getOrig_airport() {
        return orig_airport;
    }

    public String getDest_airport() {
        return dest_airport;
    }

    public int getDeparture() {
        return departure;
    }

    public int getTime() {
        return time;
    }

    public int getDistance() {
        return distance;
    }

    public int getArrival() {
        return arrival;
    }

    @Override
    public String toString() {
        //DL904 06:00 LGA ==> 08:39 ATL
        //todo correct the time format
        //return flight_number+" "+departure+" "+orig_airport+" ==> "+arrival+" "+dest_airport;
        return departure + " " + orig_airport + " ==> " + arrival + " " + dest_airport;
    }
}
