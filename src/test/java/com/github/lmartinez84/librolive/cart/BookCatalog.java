package com.github.lmartinez84.librolive.cart;

import java.util.HashSet;
import java.util.Set;

public class BookCatalog {
    private Set<Book> books;

    public BookCatalog() {
        books = new HashSet<>();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    public boolean hasBook(Book book) {
        return this.books.contains(book);
    }

    public void add(Book book) {
        books.add(book);
    }
}
