public class CornerPlot extends Plot{
	// Different Price +8%
	public CornerPlot(String plotID, PlotType plotType, ShapeType shape, double[] dimensions){
		super(plotID, plotType, shape, dimensions);

		double basePrice = plotType.getBasePrice();
		double premium = basePrice * 0.08;
		double finalPrice = basePrice + premium;
	}

	@Override
	public double getPrice(){
		return super.getPrice() * 1.08;
	}

	@Override
	public double getArea(){
		double dims[] = getDimensions();
		double width1 = dims[0];
		double width2 = dims[1];
		double depth = dims[2];
		return (width1 * depth) + (width2 * depth) / 2;
	}
}