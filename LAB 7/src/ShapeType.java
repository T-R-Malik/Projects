public enum ShapeType{
	RECTANGLE, TRAPEZOID, L_SHAPE;

	// Calculate area
	public double calculateArea(double dimensions[]){
		switch(this){
			case RECTANGLE:
				return dimensions[0] * dimensions[1];
			case TRAPEZOID:
				return ((dimensions[0] + dimensions[1])/ 2) * dimensions[2];
			case L_SHAPE:
				return (dimensions[0] * dimensions[1]) + (dimensions[2] * dimensions[3]);
			default:
				return 0;
		}
	}
}