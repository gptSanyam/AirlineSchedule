
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AirlinePath {

    static int count = 0;
    Map<String, Map<String, List<Flight>>> cityFlightMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        AirlinePath aviation = new AirlinePath();
        aviation.init("/Users/sanyamgupta/IdeaProjects/AirlineSchedule/src/Flights.csv");
        aviation.getFlightsBetween("X12", "X22");
    }

    public void init(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.nextLine(); //skip the header line
        while (sc.hasNext()) {

            String flightDetails = sc.nextLine();

            if (!flightDetails.isBlank()) {
                Flight flight = new Flight(flightDetails);

                //flightNumberToFlightMap.put(flight.getFlight_number(), flight);

                //add to airport map
                if (!cityFlightMap.containsKey(flight.getOrig_airport())) {
                    Map<String, List<Flight>> flightMap = new HashMap<>();
                    flightMap.put(flight.getDest_airport(), new ArrayList<>());
                    cityFlightMap.put(flight.getOrig_airport(), flightMap);
                }

                if (!cityFlightMap.get(flight.getOrig_airport()).containsKey(flight.getDest_airport())) {
                    cityFlightMap.get(flight.getOrig_airport()).put(flight.getDest_airport(), new ArrayList<>());
                }


                cityFlightMap.get(flight.getOrig_airport()).get(flight.getDest_airport()).add(flight);

            }

        }
    }

    void getFlightsBetween(String orig, String dest) {
        getPaths(orig, dest, 0, new Path(orig));
    }

    //recursive backtracking algo to get all the paths
    void getPaths(String orig, String dest, int arrivalTime, Path visited) {
        if (orig.equals(dest)) {
            visited.printPath();
            return;
        }
        //check if there are direct flights

        Map<String, List<Flight>> entryMap = cityFlightMap.get(orig);
        if (entryMap == null) {
            return;
        }

        for (Map.Entry<String, List<Flight>> flightsTo : entryMap.entrySet()) {
            String destCity = flightsTo.getKey();
            if (!visited.isVisited(destCity)) {
                Flight f = getEarliestFlight(flightsTo.getValue(), arrivalTime);
                if (f != null) {
                    //add flight to the visited path and recursively call getPaths
                    boolean successfultAddition = visited.addFlightToPath(f);
                    if (successfultAddition) {
                        getPaths(f.dest_airport, dest, f.getArrival(), visited);
                        visited.remove();
                    }
                }
            }
        }


    }

    private Flight getEarliestFlight(List<Flight> flights, int arrivalTime) {
        for (Flight fl : flights) {
            if (fl.getDeparture() >= arrivalTime) {
                return fl;
            }
        }
        return null;
    }

    class Path {
        final Deque<Flight> flightsTaken;
        final SortedSet<String> visitedCities;

        Path(String orig) {
            flightsTaken = new ArrayDeque<>();
            visitedCities = new TreeSet<>();
            visitedCities.add(orig);
        }

        public boolean addFlightToPath(Flight flight) {
            if (!isValid(flight)) {
                System.out.println("Trying to add invalid flight");
                return false;
            }
            flightsTaken.add(flight);
            visitedCities.add(flight.getDest_airport());
            return true;
            //System.out.println(visitedCities);
            //printInOneLine();
        }

        private boolean isValid(Flight flight) {
            return flightsTaken.size() == 0 || flightsTaken.peekLast().getDest_airport().equals(flight.getOrig_airport());
        }

        public void remove() {
            Flight flight = flightsTaken.removeLast();
            visitedCities.remove(flight.getDest_airport());
            //System.out.println(visitedCities);
            //printInOneLine();
        }

        public boolean isVisited(String city) {
            return visitedCities.contains(city);
        }

        public void printInOneLine() {
            StringBuffer bf = new StringBuffer();
            for (Flight fl : flightsTaken) {
                bf.append(fl);
                bf.append("--");
            }
            System.out.println(bf);
        }

        public void printPath() {
            System.out.println("Possible trip:");
            for (Flight fl : flightsTaken) {
                System.out.println(fl);
            }
            System.out.println("Count: " + ++count);
        }

    }

}
