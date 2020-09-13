package com.github.lmartinez84.librolive.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private static final int ZERO = 0;
    private final Set<String> catalog;
    private final Map<String, Integer> content;

    private Cart(Set<String> catalog) {
        this.content = new HashMap<>();
        this.catalog = catalog;
    }

    public static Cart newCartWithCatalog(Set<String> catalog) {
        return new Cart(catalog);
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public void addToCart(String item, Integer quantity) {
        ensureBookIsInCatalog(item);
        ensureQuantityIsPositive(item, quantity);
        content.put(item, quantity);
    }

    private void ensureQuantityIsPositive(String item, Integer quantity) {
        if (quantity <= 0) {
            throw new AddBookToCartException("Quantity of Book " + item + " should be positive.");
        }
    }

    private void ensureBookIsInCatalog(String item) {
        if (!catalog.contains(item)) {
            throw new AddBookToCartException("Book " + item + " is not in catalog.");
        }
    }

    public boolean hasContent(String item) {
        return content.containsKey(item);
    }

    public int quantityOf(String item) {
        return content.getOrDefault(item, ZERO);
    }

    public int itemsSellByStore() {
        return content.values()
                      .stream()
                      .reduce(ZERO, Integer::sum);
    }
}
