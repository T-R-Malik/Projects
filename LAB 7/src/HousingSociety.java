public class HousingSociety{
	private String name;
	private Block[] blocks;
	private int blockCount;
	private static final int INITIAL_CAPACITY = 3;

	// Preload blocks, add/remove blocks, find block, booking/cancellation, totals, availability, full report.

	public HousingSociety(String name) {
		this.name = name;
		this.blocks = new Block[INITIAL_CAPACITY];
		this.blockCount = 0;
	}

	public void addBlock(Block b){
		if(blockCount == blocks.length){
			Block[] bigger = new Block[blocks.length * 2];
			for(int i = 0; i < blockCount; i++){bigger[i] = blocks[i];}
			blocks = bigger;
		}
		blocks[blockCount++] = b;
	}

	public Block findBlock(String name){
		for(int i = 0; i < blockCount; i++){
			if(blocks[i].getName().equalsIgnoreCase(name)){return blocks[i];}
		}
		return null;
	}

	public boolean bookPlot(String blockName, String plotID){
		Block b = findBlock(blockName);
		if(b != null){return b.bookPlot(plotID);}
		System.out.println("Block not found: " + blockName);
		return false;
	}

	public boolean cancelPlot(String blockName, String plotID){
		Block b = findBlock(blockName);
		if(b != null){return b.cancelPlot(plotID);}
		System.out.println("Block not found: " + blockName);
		return false;
	}

	public int totalPlots() {
		int total = 0;
		for(int i = 0; i < blockCount; i++){total += blocks[i].totalPlots();}
		return total;
	}

	public int availablePlots(){
		int total = 0;
		for(int i = 0; i < blockCount; i++){total += blocks[i].availablePlots();}
		return total;
	}

	public void showAllBlocks(){
		System.out.println("\n=== " + name + " ===");
		for(int i = 0; i < blockCount; i++){System.out.println(blocks[i].summary());}
	}

	public Plot findFirstAvailableCornerPlot(PlotType type){
		for(int i = 0; i < blockCount; i++){
			Block b = blocks[i];
			Plot[][] plots = b.getPlots();
			for(int s = 0; s < plots.length; s++){
				for (int p = 0; p < plots[s].length; p++){
					Plot current = plots[s][p];
					if(current instanceof CornerPlot && current.getType() == type && current.isAvailable()){return current;}
				}
			}

		}
		return null;
	}

	public String summary(){
		return String.format("%s | Blocks: %d | Total Plots: %d | Available: %d", name, blockCount, totalPlots(), availablePlots());
	}

	public String getName(){return name;}
	public Block[] getBlocks(){return blocks;}
	public int getBlockCount(){return blockCount;}
}






