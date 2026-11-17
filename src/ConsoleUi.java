import java.util.Collection;

/**
 * Kõik, mis puudutab konsooli välimust: värvid, päised, tabel ja teated.
 * Laoloogika (Product, Inventory) ei tea sellest klassist midagi.
 */
public final class ConsoleUi {

    // ANSI värvikoodid. Kui keskkonnamuutuja NO_COLOR on seatud, värve ei kasutata.
    private static final boolean COLORS = System.getenv("NO_COLOR") == null;
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String DIM = "\u001B[2m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";

    private static final int WIDTH = 92;

    private ConsoleUi() {
    }

    private static String paint(String text, String... codes) {
        if (!COLORS) {
            return text;
        }
        return String.join("", codes) + text + RESET;
    }

    public static void banner() {
        String title = "MINI LAOHALDUS";
        String subtitle = "väikese poe laoseis";
        System.out.println();
        System.out.println(paint("╔" + "═".repeat(WIDTH - 2) + "╗", CYAN));
        System.out.println(paint("║", CYAN) + center(title, WIDTH - 2, BOLD, CYAN) + paint("║", CYAN));
        System.out.println(paint("║", CYAN) + center(subtitle, WIDTH - 2, DIM) + paint("║", CYAN));
        System.out.println(paint("╚" + "═".repeat(WIDTH - 2) + "╝", CYAN));
    }

    public static void menu() {
        System.out.println();
        System.out.println(paint(" MENÜÜ", BOLD));
        menuItem("1", "Näita kõiki tooteid");
        menuItem("2", "Lisa uus toode");
        menuItem("3", "Muuda toote kogust");
        menuItem("4", "Otsi toodet nime järgi");
        menuItem("5", "Lao koguväärtus");
        menuItem("0", "Välju");
    }

    private static void menuItem(String key, String label) {
        System.out.println("  " + paint(" " + key + " ", BOLD, CYAN) + "  " + label);
    }

    public static void section(String title) {
        System.out.println();
        String line = "── " + title + " ";
        System.out.println(paint(line + "─".repeat(Math.max(0, WIDTH - line.length())), BOLD, CYAN));
    }

    public static void success(String message) {
        System.out.println(paint(" ✔ ", BOLD, GREEN) + message);
    }

    public static void error(String message) {
        System.out.println(paint(" ✖ ", BOLD, RED) + paint(message, RED));
    }

    public static void info(String message) {
        System.out.println(paint(" ℹ ", BOLD, YELLOW) + message);
    }

    public static void prompt(String label) {
        System.out.print(paint(" › ", BOLD, CYAN) + label + ": ");
    }

    public static void productTable(Collection<Product> products) {
        if (products.isEmpty()) {
            info("Tooteid ei ole.");
            return;
        }
        String border = "─".repeat(WIDTH);
        System.out.println(paint(border, DIM));
        System.out.println(paint(String.format(" %-13s %-22s %10s %7s  %-22s %11s",
                "KATEGOORIA", "NIMI", "HIND", "KOGUS", "LISAINFO", "VÄÄRTUS"), BOLD));
        System.out.println(paint(border, DIM));
        for (Product product : products) {
            printRow(product);
        }
        System.out.println(paint(border, DIM));
    }

    private static void printRow(Product product) {
        // Kõigepealt joondame teksti, alles siis värvime: värvikoodid rikuks muidu veergude laiuse.
        String category = paint(String.format("%-13s", product.getCategory()), categoryColor(product));
        String name = String.format("%-22s", shorten(product.getName(), 22));
        String price = String.format("%8.2f €", product.getPrice());
        String quantity = String.format("%7d", product.getQuantity());
        if (product.isOutOfStock()) {
            quantity = paint(quantity, BOLD, RED);
        }
        String extra = paint(String.format("%-22s", shorten(product.getExtraInfo(), 22)), DIM);
        String value = String.format("%9.2f €", product.getTotalValue());
        System.out.println(" " + category + " " + name + " " + price + " " + quantity + "  " + extra + " " + value);
    }

    private static String categoryColor(Product product) {
        return switch (product.getCategory()) {
            case "Toit" -> GREEN;
            case "Elektroonika" -> CYAN;
            default -> YELLOW;
        };
    }

    public static void totalBox(double total, int productCount) {
        String text = String.format("Lao koguväärtus: %.2f €   (%d toodet)", total, productCount);
        System.out.println(paint("┌" + "─".repeat(text.length() + 4) + "┐", GREEN));
        System.out.println(paint("│  ", GREEN) + paint(text, BOLD) + paint("  │", GREEN));
        System.out.println(paint("└" + "─".repeat(text.length() + 4) + "┘", GREEN));
    }

    private static String center(String text, int width, String... codes) {
        int left = (width - text.length()) / 2;
        int right = width - text.length() - left;
        return " ".repeat(left) + paint(text, codes) + " ".repeat(right);
    }

    private static String shorten(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 1) + "…";
    }
}
