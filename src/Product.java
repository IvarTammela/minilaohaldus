/**
 * Üldine toode. Klass on abstraktne: päris toode on alati mingit kindlat liiki
 * (toidukaup, elektroonika), seega otse "new Product(...)" teha ei saa.
 */
public abstract class Product {

    private final String name;
    private double price;
    private int quantity;

    protected Product(String name, double price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Toote nimi ei tohi olla tühi.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Hind ei tohi olla negatiivne.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Kogus ei tohi olla negatiivne.");
        }
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Hind ei tohi olla negatiivne.");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Lisatav kogus peab olema positiivne.");
        }
        quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Eemaldatav kogus peab olema positiivne.");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Laos on ainult " + quantity + " tk.");
        }
        quantity -= amount;
    }

    public boolean isOutOfStock() {
        return quantity == 0;
    }

    /** Selle toote kogu laoseisu väärtus: hind korda kogus. */
    public double getTotalValue() {
        return price * quantity;
    }

    /** Iga alamklass ütleb ise, mis kategooriasse ta kuulub. */
    public abstract String getCategory();

    /** Iga alamklass kuvab oma lisainfot omal moel. */
    public abstract String getExtraInfo();

    @Override
    public String toString() {
        return String.format("%-13s %-22s %9.2f € %5d tk   %s",
                "[" + getCategory() + "]", name, price, quantity, getExtraInfo());
    }
}
