package sp25_bcs_133;

public class Screen{
	Seat seats[][];
	private String screenName;
	private int totalRows;
	Seat reservedPremiumSeats[][] = new Seat[10][];

	public Screen(String screenName, int totalRows){
		this.screenName = screenName;
		this.totalRows = totalRows;
		seats = new Seat[totalRows][];

		for(int i = 0; i < totalRows; i++){
			seats[i] = new Seat[i + 5];
			Type type;
			double price;

			if (i < totalRows / 3) {
				type = Type.REGULAR;
				price = 500;
			} else if (i < (2 * totalRows) / 3) {
				type = Type.PREMIUM;
				price = 750;
			} else if (i == totalRows - 2) {
                		type = Type.VIP;
				price = 1000;
			} else {
				type = Type.RECLINER;
				price = 1200;
			}

			for(int j = 0; j < seats[i].length; j++){
				String id = (i + 1) + "-" + String.format("%03d", j + 1);
				seats[i][j] = new Seat(id, type, price, true);
			}
		}
	}

	public Seat bookSeat(String id){
		for(int i = 0; i < seats.length; i++){
			for(int j = 0; j < seats[i].length; j++)
				if(id.equals(seats[i][j].getID()))
					if(seats[i][j].getAvailability() == true){
						seats[i][j].setBoolAvailability(false);
						seats[i][j].setAvailability("NA");
						System.out.println("");
						System.out.printf("You have booked seat %s at $%.2f.",id, seats[i][j].getPrice());
						System.out.println("");
						return seats[i][j];
					}
					else{
						System.out.println("");
						System.out.printf("Sorry, seat %s is already booked!", id);
						System.out.println("");
						return null;}
		}
		System.out.println("");
		System.out.printf("Seat %s not found.", id);
		System.out.println("");
		return null;
	}


	public void cancelBooking(String id){
		for(int i = 0; i < seats.length; i++){
			for(int j = 0; j < seats[i].length; j++)
				if(id.equals(seats[i][j].getID()))
					if(seats[i][j].getAvailability() == false){
						seats[i][j].setBoolAvailability(true);
						seats[i][j].setAvailability("A");
						System.out.println("");
						System.out.printf("Seat %s has been unbooked.",id);
						System.out.println("");
						return;
					}
					else{
						System.out.println("");
						System.out.printf("Seat %s is not booked!", id);
						System.out.println("");
						return;}
		}
		System.out.println("");
		System.out.printf("Seat %s not found.", id);
		System.out.println("");
		return;
	}


	public int getTotalSeats(){
		int counter = 0;
		for(int i = 0; i < seats.length; i++){counter += seats[i].length;}
		return counter;
	}

	public int getAvailableSeats(){
		int counter = 0;
		for(int i = 0; i < seats.length; i++)
			for(int j = 0; j < seats[i].length; j++)
				if(seats[i][j].getAvailability() == true)
					counter++;
		return counter;
	}

	
	public int getTotalSeatsByType(Type type){
		int counter = 0;
		for(int i = 0; i < seats.length; i++)
			for(int j = 0; j < seats[i].length; j++)
				if(seats[i][j].getType() == type)
					counter++;
		return counter;
	}

	public int getAvailableSeatsByType(Type type){
		int counter = 0;
		for(int i = 0; i < seats.length; i++)
			for(int j = 0; j < seats[i].length; j++)
				if(seats[i][j].getAvailability() == true && seats[i][j].getType() == type)
					counter++;
		return counter;
	}


	public void showScreen() {
		System.out.println("\n=============== " + screenName + " | Layouts ===============");
		displaySeats();
		
		System.out.println("\nTotal seats: " + getTotalSeats() + ", Available seats: " + getAvailableSeats());
		System.out.println();
		for (Type t : Type.values()) {
			System.out.printf("%-10s => Total: %-3d | Available: %-3d\n", t, getTotalSeatsByType(t), getAvailableSeatsByType(t));
		}

		displaySeatDetails();
	}

	public void displaySeatDetails(){
		System.out.println("\n=================== SEAT DETAILS ==================");
		System.out.printf("%-10s %-12s %-10s %-10s\n", "Seat ID", "Type", "Price", "Available");
		System.out.println("---------------------------------------------------");
		
		for(int i = 0; i < seats.length; i++){
			for(int j = 0; j < seats[i].length; j++){
				Seat s = seats[i][j];
				System.out.printf("%-10s %-12s %-10.2f %-10s\n", s.getID(), s.getType(), s.getPrice(), s.getAvailability() ? "Yes" : "No");
		}
	}
	System.out.println("==================================================");
}


	public void displaySeats(){
		System.out.println("");
		for(int i = 0; i < seats.length; i++){
			for(int j = 0; j < seats[i].length; j++){
				System.out.print(seats[i][j].displaySeating() + " ");
			}
			System.out.println();
		}
	}


	public Seat findFirstAvailableSeatByType(Type type) {
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				if (seats[i][j].getType() == type && seats[i][j].getAvailability()) {
					return seats[i][j];
				}
			}
		}
		return null; // none found
	}

/*
	public Seat[] getReservedPremiumSeats(){
		for(int i = 0; i < seats.length; i++){
			for(int j = 0; j < seats[i].length; j++){
				if(seats[i][j].getType() == Type.PREMIUM && seats[i][j].getAvailability() == false){
					seats[i][j] = reservedPremiumSeats[i];
				}
			}
		}
		return reservedPremiumSeats;
	}

	public void displayTask(){
		for(int i = 0; i < reservedPremiumSeats.length; i++){
			for(int j = 0; j < reservedPremiumSeats[i].length; j++)
				System.out.println(reservedPremiumSeats[i][j]);
		}
	}		

*/
	
	

	public String getName(){return screenName;}
}