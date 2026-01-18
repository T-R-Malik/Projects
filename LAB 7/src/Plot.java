public class Plot{
	private String plotID;
	private PlotType type;
	private ShapeType shape;
	private double[] dimensions;
	private double area;
	private double price;
	private boolean available;

	public Plot(String plotID, PlotType plotType, ShapeType shape, double[] dimensions){
		this.plotID = plotID;
		this.type = plotType;
		this.shape = shape;
		this.dimensions = dimensions;
		this.available = true;

		// Compute price and area.
		this.area = shape.calculateArea(dimensions);
		this.price = type.getBasePrice();
	}

	// Booking, Cancellation, Availability
	public boolean bookPlot(){
		if(available){
			available = false;
			return true;
		}
		return false;	
	}

	public boolean cancelBooking(){available = true; return available;}
	
	public String getID(){return plotID;}
	public PlotType getType(){return type;}
	public double[] getDimensions(){return dimensions;}
	public double getShape(){return shape.calculateArea(dimensions);}
	public double getArea(){return area;}
	public double getPrice(){return price;}
	public boolean isAvailable(){return available;}

	// toString
	@Override
	public String toString(){
		return String.format("%s [%s, %.1f su, %.0f PKR, %s]", plotID, type, area, price, available ? "Available" : "Booked");
	}
}