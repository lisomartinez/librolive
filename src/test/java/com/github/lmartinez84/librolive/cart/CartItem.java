package com.github.lmartinez84.librolive.cart;

public class CartItem {
    private final Book book;
    private final int quantity;

    public CartItem(Book book, int quantity) {
        if (quantity <= 0) {
            throw new AddBookToCartException("Book quantity should be greater than zero.");
        }
        this.book = book;
        this.quantity = quantity;
    }

    public static CartItemBuilder aNewItem() {
        return new CartItemBuilder();
    }

    public Book book() {
        return book;
    }

    public int quantity() {
        return quantity;
    }

    public boolean hasQuantity(int quantity) {
        return this.quantity == quantity;
    }

    public boolean itsBookIs(Book book) {
        return this.book.equals(book);
    }

    public static class CartItemBuilder {
        private Book book;

        public CartItemBuilder withBook(Book isbn) {
            this.book = isbn;
            return this;
        }

        public CartItem andQuantityOf(int quantity) {
            return new CartItem(book, quantity);
        }
    }
}
