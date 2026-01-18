package sp25_bcs_133;

public class CityCinema{
	private Cinema cinemas[];
	private String cityName;
	private int cinemaNumber;


	CityCinema(String cityName, int cinemaNumber){
		this.cityName = cityName;
		this.cinemaNumber = cinemaNumber;
		cinemas = new Cinema[cinemaNumber];
		for(int i = 0; i < cinemas.length; i++){
			cinemas[i] = new Cinema("CINEMA-" + (i + 1), 5);
		}
	}

	public void showAllCinemas(){
		System.out.println("\n==================================================");
		System.out.println("                   CITY: " + cityName.toUpperCase());
		System.out.println("==================================================");
		
		for (int i = 0; i < cinemas.length; i++){
			System.out.printf("                 CINEMA #%d: %-15s%n", (i + 1), cinemas[i].getName());
			cinemas[i].welcomeScreen();
		}

		System.out.println("\n================= END OF CITY REPORT ==============\n");
	}


	public void findFirstAvailableVIPSeat() {
		System.out.println("\nSearching for first available VIP seat in " + cityName + "...");

		for (int i = 0; i < cinemas.length; i++) {
			Cinema cinema = cinemas[i];
			for (int j = 0; j < cinema.getScreens().length; j++) {
				Screen screen = cinema.getScreen(j);
				Seat vipSeat = screen.findFirstAvailableSeatByType(Type.VIP);

				if (vipSeat != null) {
					System.out.printf("Found in %s | %s | Seat: %s | Price: %.2f\n", cityName, screen.getName(), vipSeat.getID(), vipSeat.getPrice());
					return;
				}
			}
		}
		System.out.println("No available VIP seat found in " + cityName + ".");
	}


	public void addCinema(String cinemaName, int totalScreens) {
		Cinema[] newCinemas = new Cinema[cinemas.length + 1];
		for (int i = 0; i < cinemas.length; i++) {
			newCinemas[i] = cinemas[i];
		}

		newCinemas[cinemas.length] = new Cinema(cinemaName, totalScreens);
		cinemas = newCinemas;
		System.out.println("Cinema added successfully: " + cinemaName);
	}


	public void removeCinema(String cinemaName) {
		int indexToRemove = -1;
		for (int i = 0; i < cinemas.length; i++) {
			if (cinemas[i].getName().equalsIgnoreCase(cinemaName)) {
				indexToRemove = i;
				break;
			}
		}

		if (indexToRemove == -1) {
			System.out.println("Cinema not found: " + cinemaName);
			return;
		}

		Cinema[] newCinemas = new Cinema[cinemas.length - 1];
		int newIndex = 0;
		for (int i = 0; i < cinemas.length; i++) {
			if (i != indexToRemove) {
				newCinemas[newIndex++] = cinemas[i];
			}
		}

		cinemas = newCinemas;
		System.out.println("Cinema removed: " + cinemaName);
	}


	public Cinema findCinema(String name) {
		for (int i = 0; i < cinemas.length; i++) {
			if (cinemas[i].getName().equalsIgnoreCase(name)) {
				System.out.println("Cinema found: " + cinemas[i].getName());
				return cinemas[i];
			}
		}
		System.out.println("Cinema not found: " + name);
		return null;
	}


	public Cinema getCinema(int index) {
		if (index >= 0 && index < cinemas.length) {
			return cinemas[index];
		}
		return null;
	}

/*
	public CityCinema getUnion(CityCinema a, CityCinema b){
		CityCinema union = new CityCinema();
		for(int i = 0; i < )
	}
*/
}