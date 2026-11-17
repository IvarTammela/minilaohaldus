import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Hoiab poe tooteid ja teeb nendega laotoiminguid.
 *
 * Tooted on LinkedHashMap-is, kus võti on toote nimi väiketähtedega:
 * - nime järgi leidmine on kiire (ei pea kõiki tooteid läbi käima);
 * - sama nimega toodet ei saa kaks korda lisada;
 * - "Linked" hoiab lisamise järjekorra, nii et nimekiri kuvatakse alati samas järjekorras.
 */
public class Inventory {

    private final Map<String, Product> products = new LinkedHashMap<>();

    private String keyOf(String name) {
        return name.trim().toLowerCase();
    }

    /** Tagastab false, kui sellise nimega toode on juba laos. */
    public boolean addProduct(Product product) {
        String key = keyOf(product.getName());
        if (products.containsKey(key)) {
            return false;
        }
        products.put(key, product);
        return true;
    }

    /** Täpne otsing nime järgi. Tagastab null, kui toodet pole. */
    public Product findByName(String name) {
        return products.get(keyOf(name));
    }

    /** Osaline otsing: kõik tooted, mille nimes otsitav tekst sisaldub. */
    public List<Product> searchByName(String text) {
        String searched = keyOf(text);
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getName().toLowerCase().contains(searched)) {
                result.add(product);
            }
        }
        return result;
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public double getTotalValue() {
        double total = 0;
        for (Product product : products.values()) {
            total += product.getTotalValue();
        }
        return total;
    }

    public int size() {
        return products.size();
    }
}
