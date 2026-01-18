public class Block{
	private String name;
	private Plot plots[][];
	private Park parks[];
	private CommercialMarket market;

	private static final int DEFAULT_STREETS = 5;
	private static final int FIRST_STREET_LENGTH = 10;

	// Constructor where plot types assigned.
	public Block(String name){
		this.name = name;
		buildPlots();
		buildAmenities();
	}	

	// Corner Plot -> every 4th plot.
	// Parking -> every 5th plot.

	public void buildPlots(){
		plots = new Plot[DEFAULT_STREETS][];
		for(int i = 0; i < DEFAULT_STREETS; i++){
			int length = FIRST_STREET_LENGTH + i;
			plots[i] = new Plot[length];

			for(int j = 0; j < length; j++){
				String plotID = name + "-S" + (i + 1) + "-P" + (j + 1);

				PlotType type;
				ShapeType shape;
				double[] dimensions;

				int streetNumber = i + 1;
				switch(streetNumber){
					case 1:
						type = PlotType.RES_5_MARLA;
						shape = ShapeType.RECTANGLE;
						dimensions = new double[] {10.0, 20.0};
						break;
					case 2:
						type = PlotType.RES_10_MARLA;
						shape = ShapeType.RECTANGLE;
						dimensions = new double[] {20.0, 40.0};
						break;
					case 3:
						type = PlotType.RES_1_KANAL;
						shape = ShapeType.TRAPEZOID;
						dimensions = new double[] {30.0, 35.0, 50.0};
						break;
					case 4:
						type = PlotType.COMM_SHOP;
						shape = ShapeType.RECTANGLE;
						dimensions = new double[] {5.0, 15.0};
						break;
					case 5:
						type = PlotType.COMM_OFFICE;
						shape = ShapeType.RECTANGLE;
						dimensions = new double[] {10.0, 15.0};
						break;
					default:
						type = PlotType.RES_10_MARLA;
						shape = ShapeType.RECTANGLE;
						dimensions = new double[] {10.0, 20.0};
						break;
				}
				
				// Parking -> every 5th plot
				if ((j + 1) % 5 == 0){
					type = PlotType.PARKING;
					shape = ShapeType.RECTANGLE;
					dimensions = new double[] {2.0, 7.0};
				}

				boolean isResidentialStreet = (streetNumber >= 1 && streetNumber <= 3);
				if(isResidentialStreet && ((j + 1) % 4 == 0) && type != PlotType.PARKING){
					double width1 = (dimensions.length >= 1) ? dimensions[0] : 20.0;
					double width2 = width1 * 0.9;
					double depth = (dimensions.length >= 2) ? dimensions[dimensions.length - 1] : 40.0;
					double cornerDimensions[] = new double[]{width1, width2, depth};
					plots[i][j] = new CornerPlot(plotID, type, shape, cornerDimensions);
				}
				else{plots[i][j] = new Plot(plotID, type, shape, dimensions);}

			}
		}
	}


	public void buildAmenities(){
		parks = new Park[2];
		parks[0] = new Park(name + "-PARK-1", ShapeType.RECTANGLE, new double[]{20.0, 30.0});
		parks[1] = new Park(name + "-PARK-2", ShapeType.RECTANGLE, new double[]{25.0, 20.0});

		// A Commercial Market.
		market = new CommercialMarket(15);
	}



	// Show layout, boking, cancellation, first available by type, largest available residential, print amenities.

	public void showLayout(){
		System.out.println("\n==== " + name + " Layout ====");
		for(int i = 0; i < plots.length; i++){
			System.out.println("Street " + (i + 1) + ": ");
			for(int j = 0; j < plots[i].length; j++){
				String available = (plots[i][j].isAvailable() ? "A" : "NA");
				System.out.print("[" + plots[i][j].getID() + ":" + available + "]");
			}
			System.out.println();
		}
	}

	public void showDetailedPlots(){
		System.out.println("\n--- Detailed Plots for " + name + " ---");
		for(int i = 0; i < plots.length; i++){
			System.out.println("Street " + (i + 1) + ": ");
			for(int j = 0; j < plots[i].length; j++){
				System.out.println(" " + plots[i][j].toString());
			}
		}
	}


	public void showAmenities(){
		System.out.println("\n--- Amenities for " + name + " ---");
		System.out.println("Parks:");
		for(Park p : parks){
			System.out.printf(" %s [Area: %.2f]\n", p.toString(), p.getArea());
		}
		System.out.println("Market:");
		market.showAllShops();
	}

	public boolean bookPlot(String plotID){
		Plot p = findPlotByID(plotID);
		if(p == null){
			System.out.println("Plot " + plotID + " not found in " + name);
			return false;
		}
		boolean ok = p.bookPlot();
		System.out.println(ok ? "Booked " + plotID : "Booking Failed: " + plotID + "(already booked)");
		return ok;
	}

	public boolean cancelPlot(String plotID){
		Plot p = findPlotByID(plotID);
		if(p == null){
			System.out.println("Plot " + plotID + " not found in " + name);
			return false;
		}
		boolean ok = p.cancelBooking();
		System.out.println(ok ? "Cancelled Booking " + plotID : "Cancellation Failed for: " + plotID);
		return ok;
	}

	public Plot findPlotByID(String id){
		for(int i = 0; i < plots.length; i++){
			for(int j = 0; j < plots[i].length; j++){
				if(plots[i][j].getID().equals(id)){return plots[i][j];}
			}
		}
		return null;
	}

	// Extra functions for practice
	public Plot[] getAvailablePlotsByType(PlotType type){
		int count = 0;
		for(int i = 0; i < plots.length; i++){
			for(int j = 0; j < plots[i].length; j++){
				if(plots[i][j].getType() == type && plots[i][j].isAvailable()){count++;}
			}
		}
		Plot[] result = new Plot[count];
		int index = 0;
		for(int i = 0; i < plots.length; i++){
			for(int j = 0; j < plots[i].length; j++){
				if(plots[i][j].getType() == type && plots[i][j].isAvailable()){
					result[index++] = plots[i][j];
				}
			}
		}
		return result;
	}

	public Plot findFirstAvailablePlotByType(PlotType type){
		for(int i = 0; i < plots.length; i++){
			for(int j = 0; j < plots[i].length; j++){
				if(plots[i][j].getType() == type && plots[i][j].isAvailable()){
					return plots[i][j];
				}
			}
		}
		return null;
	}

	public Plot getLargestAvailableResidentialPlot(){
		Plot best = null;
		double bestArea = -1;
		for(int i = 0; i < plots.length; i++){
			for(int j = 0; j < plots[i].length; j++){
				Plot p1 = plots[i][j];
				PlotType pType = p1.getType();
				if((pType == PlotType.RES_5_MARLA || pType == PlotType.RES_10_MARLA || pType == PlotType.RES_1_KANAL) && p1.isAvailable()){
					double area = p1.getArea();
					if(area > bestArea){
						bestArea = area;
						best = p1;
					}
				}
			}
		}
		return best;
	}

	public int totalPlots(){
		int counter = 0;
		for(int i = 0; i < plots.length; i++){counter += plots[i].length;}
		return counter;
	}

	public int availablePlots(){
		int counter = 0;
		for(int i = 0; i < plots.length; i++){
			for(int j = 0; j < plots[i].length; j++){
				if(plots[i][j].isAvailable()){
					counter++;
				}
			}				
		}
		return counter;
	}

	public String summary(){
		return String.format("%s: total = %d | available = %d", name, totalPlots(), availablePlots());
	}

	public Shop[] getFirstNAvailableShops(int n){
		Shop[] all = market.getShops();
		int counter = 0;
		for(int i = 0; i < all.length; i++){counter++;}
		if(counter == 0){return new Shop[0];}

		int outSize = Math.min(n, counter);
		Shop[] out = new Shop[outSize];
		int index = 0;
		for(int i = 0; i < all.length && index < outSize; i++){
			if(all[i].isAvailable()){out[index++] = all[i];}
		}
		return out;
	}

	public String getName(){return name;}
	public Plot[][] getPlots(){return plots;}
	public Park[] getParks(){return parks;}
	public CommercialMarket getMarket(){return market;}
	
}





