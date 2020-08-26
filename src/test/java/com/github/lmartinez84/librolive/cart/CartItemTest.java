package com.github.lmartinez84.librolive.cart;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CartItemTest {
    public static final String ISBN = "0-1826-2928-7";

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2})
    void cannot_add_a_book_with_zero_or_negative_quantity(int quantity) {
        Book bookInCatalog = Book.aBook().withIsbn(ISBN).createBook();

        assertThatExceptionOfType(AddBookToCartException.class)
                .isThrownBy(() -> createItem(quantity, bookInCatalog))
                .withMessage("Book quantity should be greater than zero.");

    }

    private CartItem createItem(int quantity, Book bookInCatalog) {
        return CartItem.aNewItem()
                       .withBook(bookInCatalog)
                       .andQuantityOf(quantity);
    }
}
