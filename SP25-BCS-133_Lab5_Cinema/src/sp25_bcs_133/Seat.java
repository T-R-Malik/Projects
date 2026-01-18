package sp25_bcs_133;

public class Seat{
	private String id;
	private Type type;
	private double price;
	private boolean isAvailable;
	private String availability;

	Seat(String id, Type type, double price, boolean isAvailable){
		this.id = id;
		this.type = type;
		this.price = price;
		this.isAvailable = isAvailable;

		if(isAvailable = true){availability = "A";}
		else{availability = "NA";}
	}

	Seat(){}

	public String getID(){return id;}
	public double getPrice(){return price;}
	public boolean getAvailability(){return isAvailable;}
	public Type getType(){return type;}

	public void setBoolAvailability(boolean isAvailable){
		this.isAvailable = isAvailable;
	}

	public void setAvailability(String availability){
		this.availability = availability;
	}

	public String displaySeating(){
		return String.format("[%s:%s]", id, availability);
	}

	@Override
	public String toString(){
		return String.format("[%s:%s:%.2f:%s]", id, type, price, availability);
	}
}