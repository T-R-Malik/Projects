public class Shop{
	private String shopID;
	private PlotType type;
	private boolean available;

	public Shop(String shopID){
		this.shopID = shopID;
		this.type = PlotType.COMM_SHOP;
		this.available = true;
	}
	
	public boolean isAvailable(){return available;}

	public void bookShop(){
		if(!available){System.out.println(shopID + "is already booked!");}
		else{available = false;System.out.println(shopID + "has been booked!");}
	}

	public void cancelBooking(){available = true;}

	public void shopDetails(){
		System.out.printf("Shop %s - %s (%.0f PKR)\n", shopID, available ? "Available" : "Booked", getPrice());
	}

	public String getID(){return shopID;}
	public double getPrice(){return type.getBasePrice();}
}