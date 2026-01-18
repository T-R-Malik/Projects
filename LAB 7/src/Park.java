public class Park{
	private String parkID;
	private ShapeType shape;
	private double[] dimensions;
	private double area;

	public Park(String parkID, ShapeType shape, double[] dimensions){
		this.parkID = parkID;
		this.shape = shape;
		this.dimensions = dimensions;
		this.area = shape.calculateArea(dimensions);
		
	}

	public double getArea(){return area;}
	public void showDetails(){System.out.printf("Park %s [%s, Area: %.2f su]\n", parkID, shape, area);}
}