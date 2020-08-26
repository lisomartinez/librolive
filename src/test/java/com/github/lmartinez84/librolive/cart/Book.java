package com.github.lmartinez84.librolive.cart;

import java.util.Objects;

public class Book {
    private final String isbn;

    public Book(String isbn) {
        this.isbn = isbn;
    }

    public static BookBuilder aBook() {
        return new BookBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    public static class BookBuilder {
        private String isbn;

        public BookBuilder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Book createBook() {
            return new Book(isbn);
        }
    }
}
