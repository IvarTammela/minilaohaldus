public class FoodProduct extends Product {

    private final String bestBefore;

    public FoodProduct(String name, double price, int quantity, String bestBefore) {
        super(name, price, quantity);
        this.bestBefore = bestBefore;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    @Override
    public String getCategory() {
        return "Toit";
    }

    @Override
    public String getExtraInfo() {
        return "parim enne: " + bestBefore;
    }
}
