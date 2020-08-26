package com.github.lmartinez84.librolive.cart;

import java.util.Map;

public class BookNotInCatalogueException extends RuntimeException {
    public BookNotInCatalogueException(Map.Entry<String, Integer> item) {
        super("Book " + item.getKey() + " is not in catalog");
    }
}
