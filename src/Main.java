import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();

    public static void main(String[] args) {
        addSampleProducts();
        ConsoleUi.banner();

        boolean running = true;
        while (running) {
            ConsoleUi.menu();
            ConsoleUi.prompt("Vali tegevus");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showAllProducts();
                case "2" -> addProduct();
                case "3" -> changeQuantity();
                case "4" -> searchProduct();
                case "5" -> showTotalValue();
                case "0" -> running = false;
                default -> ConsoleUi.error("Tundmatu valik. Sisesta number 0–5.");
            }
        }
        ConsoleUi.info("Head aega!");
    }

    private static void addSampleProducts() {
        inventory.addProduct(new FoodProduct("Piim 2,5%", 1.19, 24, "2026-11-25"));
        inventory.addProduct(new FoodProduct("Rukkileib", 2.49, 15, "2026-11-20"));
        inventory.addProduct(new ElectronicProduct("Juhtmevabad kõrvaklapid", 39.90, 6, 24));
        inventory.addProduct(new ElectronicProduct("USB-C laadija", 14.99, 0, 12));
    }

    private static void showAllProducts() {
        ConsoleUi.section("Kõik tooted");
        ConsoleUi.productTable(inventory.getAllProducts());
    }

    private static void addProduct() {
        ConsoleUi.section("Uus toode");
        System.out.println("   1 = toidukaup, 2 = elektroonika");
        int type = readInt("Toote liik", 1, 2);
        String name = readText("Nimi");
        if (inventory.findByName(name) != null) {
            ConsoleUi.error("Selle nimega toode on juba laos. Kasuta koguse muutmist.");
            return;
        }
        double price = readDouble("Hind (€)");
        int quantity = readInt("Kogus", 0, Integer.MAX_VALUE);

        Product product;
        if (type == 1) {
            String bestBefore = readText("Parim enne (nt 2026-12-31)");
            product = new FoodProduct(name, price, quantity, bestBefore);
        } else {
            int warranty = readInt("Garantii (kuudes)", 0, 120);
            product = new ElectronicProduct(name, price, quantity, warranty);
        }

        inventory.addProduct(product);
        ConsoleUi.success("Toode \"" + product.getName() + "\" lisatud.");
    }

    private static void changeQuantity() {
        ConsoleUi.section("Koguse muutmine");
        String name = readText("Toote täpne nimi");
        Product product = inventory.findByName(name);
        if (product == null) {
            ConsoleUi.error("Toodet \"" + name + "\" ei leitud.");
            return;
        }
        ConsoleUi.info(product.getName() + ": laos praegu " + product.getQuantity() + " tk");
        int change = readInt("Muutus (nt 5 lisab, -3 eemaldab)", Integer.MIN_VALUE, Integer.MAX_VALUE);

        try {
            if (change > 0) {
                product.increaseQuantity(change);
            } else if (change < 0) {
                product.decreaseQuantity(-change);
            }
            ConsoleUi.success("Uus kogus: " + product.getQuantity() + " tk");
        } catch (IllegalArgumentException e) {
            ConsoleUi.error(e.getMessage());
        }
    }

    private static void searchProduct() {
        ConsoleUi.section("Otsing");
        String text = readText("Otsitav nimi või nime osa");
        List<Product> found = inventory.searchByName(text);
        if (found.isEmpty()) {
            ConsoleUi.error("Ühtegi toodet ei leitud.");
            return;
        }
        ConsoleUi.success("Leiti " + found.size() + " toode(t):");
        ConsoleUi.productTable(found);
    }

    private static void showTotalValue() {
        ConsoleUi.section("Koguväärtus");
        ConsoleUi.productTable(inventory.getAllProducts());
        ConsoleUi.totalBox(inventory.getTotalValue(), inventory.size());
    }

    // --- Sisendi lugemine: küsib uuesti, kuni sisend on korrektne ---

    private static String readText(String label) {
        while (true) {
            ConsoleUi.prompt(label);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            ConsoleUi.error("Väli ei tohi olla tühi.");
        }
    }

    private static int readInt(String label, int min, int max) {
        while (true) {
            ConsoleUi.prompt(label);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                ConsoleUi.error("Arv peab olema vahemikus " + min + "–" + max + ".");
            } catch (NumberFormatException e) {
                ConsoleUi.error("Sisesta täisarv.");
            }
        }
    }

    private static double readDouble(String label) {
        while (true) {
            ConsoleUi.prompt(label);
            try {
                // Lubame ka eestipärast komakohta: 2,49
                double value = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (value >= 0) {
                    return value;
                }
                ConsoleUi.error("Hind ei tohi olla negatiivne.");
            } catch (NumberFormatException e) {
                ConsoleUi.error("Sisesta arv, näiteks 2.49");
            }
        }
    }
}
