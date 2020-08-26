package com.github.lmartinez84.librolive.cart;

import java.util.HashSet;
import java.util.Set;

public class Cart {
    private final BookCatalog catalog;
    private final Set<CartItem> items;

    private Cart(BookCatalog catalog) {
        this.items = new HashSet<>();
        this.catalog = catalog;
    }

    public static Cart newCartWithCatalog(BookCatalog catalog) {
        return new Cart(catalog);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addToCart(CartItem item) {
        ensureBookIsInCatalog(item);
        items.add(item);
    }

    private void ensureBookIsInCatalog(CartItem item) {
        if (!catalog.hasBook(item.book())) {
            throw new AddBookToCartException("Book " + item.book() + " is not in catalog.");
        }
    }


    public boolean hasItem(CartItem item) {
        return items.contains(item);
    }

    public int quantityOf(CartItem item) {
        return items.stream()
                    .filter(stored -> stored.equals(item))
                    .mapToInt(CartItem::quantity)
                    .findFirst().orElse(0);
    }
}
