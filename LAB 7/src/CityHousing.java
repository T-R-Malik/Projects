public class CityHousing{
	private String cityName;
	private HousingSociety[] societies;
	private int societyCount;

	private static final int INITIAL_CAPACITY = 2;

	public CityHousing(String name){
		this.cityName = name;
		societies = new HousingSociety[INITIAL_CAPACITY];
		societyCount = 0;
	}

	public void addSociety(HousingSociety s){
		if (societyCount == societies.length){
			HousingSociety[] bigger = new HousingSociety[societies.length * 2];
			for(int i = 0; i < societyCount; i++){bigger[i] = societies[i];}
			societies = bigger;
		}
		societies[societyCount++] = s;
	}

	// Add/remove/find societies, find plots, print summary.

	public HousingSociety findSociety(String name){
		for (int i = 0; i < societyCount; i++){
			if(societies[i].getName().equalsIgnoreCase(name)){return societies[i];}
		}
		return null;
	}


	public boolean bookPlot(String societyName, String blockName, String plotID){
		HousingSociety s = findSociety(societyName);
		if(s != null){return s.bookPlot(blockName, plotID);}
		System.out.println("Society not found: " + societyName);
		return false;
	}

	public boolean cancelPlot(String societyName, String blockName, String plotID){
		HousingSociety s = findSociety(societyName);
		if(s != null){return s.cancelPlot(blockName, plotID);}
		System.out.println("Society not found: " + societyName);
		return false;
	}

	public Plot findFirstAvailableKanalCornerPlot(){
		for(int i = 0; i < societyCount; i++){
			Plot p = societies[i].findFirstAvailableCornerPlot(PlotType.RES_1_KANAL);
			if(p != null){
				System.out.println("Found in " + societies[i].getName());
				return p;
			}
		}
		return null;
	}

	public void showSummary(){
		System.out.println("\n=== " + cityName + " Summary ===");
		for(int i = 0; i < societyCount; i++){System.out.println(societies[i].summary());}
	}

	public int totalAvailablePlots(){
		int total = 0;
		for(int i = 0; i < societyCount; i++){total += societies[i].availablePlots();}
		return total;
	}	
}