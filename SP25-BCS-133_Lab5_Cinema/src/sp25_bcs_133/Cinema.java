package sp25_bcs_133;

public class Cinema{
	private String name;
	private int totalScreens;
	String moviesPlaying[] = {"Lion King", "Finding Nemo", "Kung-Fu Panda", "Spirited Away", "Harry Potter"};

	private Screen screens[];
	private String moviesForScreens[];

	public Cinema(String name, int totalScreens){
		this.name = name;
		this.totalScreens = totalScreens;
		screens = new Screen[totalScreens];
		moviesForScreens = new String[totalScreens];
		for(int i = 0; i < screens.length; i++){
			moviesForScreens[i] = moviesPlaying[i % moviesPlaying.length];
			screens[i] = new Screen("SCREEN-" + (i + 1), 5);
		}
	}

	public void welcomeScreen(){
		System.out.println("\n================ CINEMA DETAILS ==================");
		for(int i = 0; i < screens.length; i++){
			System.out.println();
			System.out.println(screens[i].getName() + " | NOW PLAYING: " + moviesForScreens[i]);
			screens[i].showScreen();
		}
	}

	public void findScreen(String name){
		for(int i = 0; i < screens.length; i++){
			if(screens[i].getName().equals(name)){
				System.out.println();
				System.out.println(screens[i].getName() + " | NOW PLAYING: " + moviesForScreens[i]);
				screens[i].showScreen();
				return;
			}
		}
	}

	public String getName(){return name;}

	public String getMoviePlaying(int screenIndex){
		if (screenIndex >= 0 && screenIndex < moviesForScreens.length) {
            		return moviesForScreens[screenIndex];
        	}
        return "Invalid screen index";
	}

	public int getTotalCinemaSeats(){
		int counter = 0;
		for(int i = 0; i < screens.length; i++){
			counter += screens[i].getTotalSeats();
		}
		return counter;
	}

	public int getAvailableCinemaSeats(){
		int counter = 0;
		for(int i = 0; i < screens.length; i++)
			counter += screens[i].getAvailableSeats();
		return counter;
	}

	public int getCinemaSeatsByType(){
		System.out.println("\n===== CINEMA SEATS BY TYPE =====");
		int counter = 0;
		for(int i = 0; i < screens.length; i++){
			System.out.println(screens[i].getName());
			for (Type type : Type.values()) {
				counter = screens[i].getTotalSeatsByType(type);
				System.out.printf("%-10s : %d\n", type, counter);
			}
		}
		return counter;
	}


	public Screen getScreen(int index) {
		if (index >= 0 && index < screens.length) {
			return screens[index];
		}
		return null;
	}

	public Screen[] getScreens() {return screens;}


	@Override
	public String toString(){
		return String.format("[%s %d %d %d]", name, totalScreens, getTotalCinemaSeats(), getAvailableCinemaSeats());
	}
}