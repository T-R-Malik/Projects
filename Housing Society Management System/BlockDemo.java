public class BlockDemo{
	public static void main(String[] args){
		System.out.println("\n=== Housing Management System Demo ===");

		Block blockA = new Block("A");
		System.out.println("Created Block " + blockA.getName());
		System.out.println();

		blockA.showDetailedPlots();

		System.out.println("\n--- Booking Tests ---");
		blockA.bookPlot("A-S1-P1");
		blockA.bookPlot("A-S1-P4");
		blockA.bookPlot("A-S2-P2");
		blockA.bookPlot("A-S2-P5");

		blockA.bookPlot("A-S1-P1");

		System.out.println("\n--- Cancellation Tests ---");
		blockA.cancelPlot("A-S2-P2");
		blockA.cancelPlot("A-S2-P2");
		blockA.cancelPlot("A-S9-P1");

		System.out.println("\n--- Updated Plot Availability ---");
		blockA.showDetailedPlots();

		System.out.println("\n--- Block Summary ---");
		System.out.println(blockA.summary());
	}
}