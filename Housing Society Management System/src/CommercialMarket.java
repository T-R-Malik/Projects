public class CommercialMarket{
	private Shop shops[];

	public CommercialMarket(int shopCount){
		shops = new Shop[shopCount];
		for(int i = 0; i < shopCount; i++){
			shops[i] = new Shop("SHOP-" + i + 1);
		}
	}

	public void showAllShops(){
		System.out.println("=== Market Shops ===");
		for(Shop s : shops){s.shopDetails();}
	}

	public Shop[] getShops(){return shops;}

	// Booking, Cancellation

	// Number of available shops
}