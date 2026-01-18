public class SocietyDemo{
	public static void main(String args[]){
		System.out.println("=== CITY HOUSING SYSTEM DEMO ===\n");

		CityHousing city = new CityHousing("Lahore");
	
		HousingSociety society1 = new HousingSociety("LDA Avenue 1");
		HousingSociety society2 = new HousingSociety("Model Town");

		Block blockA = new Block("A");
		Block blockB = new Block("B");
		Block blockC = new Block("C");
		society1.addBlock(blockA);
		society1.addBlock(blockB);
		society2.addBlock(blockC);

		city.addSociety(society1);
		city.addSociety(society2);
		city.showSummary();

		society1.showAllBlocks();

		System.out.println("\n--- BOOKING TESTS ---");
		blockA.bookPlot("A-S1-P1");
		blockA.bookPlot("A-S1-P4");
		blockA.bookPlot("A-S2-P3");

		System.out.println("\n--- DOUBLE BOOKING TEST ---");
		blockA.bookPlot("A-S1-P1");

		System.out.println("\n--- CANCELLATION TESTS ---");
		blockA.cancelPlot("A-S1-P1");
		blockA.cancelPlot("A-S1-P1");

		System.out.println("\n--- UPDATED LAYOUT ---");
		society1.showAllBlocks();

		System.out.println("\n--- SOCIETY SUMMARIES ---");
		society1.showAllBlocks();
		society2.showAllBlocks();

		System.out.println("\n--- CORNER PLOT TEST ---");
		double[] dims = {40, 30, 20};
		CornerPlot corner = new CornerPlot("CORN-01", PlotType.RES_5_MARLA, ShapeType.RECTANGLE, dims);
		System.out.println(corner);

		System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");

	}
}