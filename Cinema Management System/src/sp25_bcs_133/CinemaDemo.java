package sp25_bcs_133;

public class CinemaDemo{
	public static void main(String args[]){
		CityCinema lahoreCinemas = new CityCinema("LAHORE", 2);
		CityCinema karachiCinemas = new CityCinema("KARACHI", 2);

		lahoreCinemas.getCinema(0).getScreen(0).bookSeat("1-001");
		lahoreCinemas.getCinema(0).getScreen(0).bookSeat("1-002");
		karachiCinemas.getCinema(0).getScreen(1).bookSeat("1-001");
		karachiCinemas.getCinema(0).getScreen(1).bookSeat("1-002");
	}
}