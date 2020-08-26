package com.github.lmartinez84.librolive.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Execution(ExecutionMode.CONCURRENT)
class CartTest {
    public static final String ISBN = "0-1826-2928-7";
    private Cart cart;
    private BookCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new BookCatalog();
        catalog.add(Book.aBook().withIsbn(ISBN).createBook());
    }

    @Test
    void when_created_is_empty() {
        cart = Cart.newCartWithCatalog(catalog);

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void cart_item_is_composed_by_a_book_and_quantity() {
        Book bookInCatalog = Book.aBook().withIsbn(ISBN).createBook();
        CartItem item = CartItem.aNewItem()
                                .withBook(bookInCatalog)
                                .andQuantityOf(2);

        assertThat(item.itsBookIs(bookInCatalog)).isTrue();
        assertThat(item.hasQuantity(2)).isTrue();
    }

    @Test
    void when_item_added_is_not_empty() {
        cart = Cart.newCartWithCatalog(catalog);
        Book bookInCatalog = Book.aBook().withIsbn(ISBN).createBook();
        CartItem item = CartItem.aNewItem()
                                .withBook(bookInCatalog)
                                .andQuantityOf(2);

        cart.addToCart(item);

        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void can_know_its_items() {
        cart = Cart.newCartWithCatalog(catalog);
        Book bookInCatalog = Book.aBook().withIsbn(ISBN).createBook();
        CartItem item = CartItem.aNewItem()
                                .withBook(bookInCatalog)
                                .andQuantityOf(2);

        cart.addToCart(item);

        assertThat(cart.hasItem(item)).isTrue();
    }

    @Test
    void can_know_quantity_of_an_item() {
        cart = Cart.newCartWithCatalog(catalog);
        Book bookInCatalog = Book.aBook().withIsbn(ISBN).createBook();

        CartItem item = CartItem.aNewItem()
                                .withBook(bookInCatalog)
                                .andQuantityOf(2);

        cart.addToCart(item);

        assertThat(cart.quantityOf(item)).isEqualTo(item.quantity());
    }

    @Test
    void cannot_add_item_with_book_not_in_catalogue() {
        cart = Cart.newCartWithCatalog(catalog);
        Book bookNotInCatalog = Book.aBook().withIsbn("0-8431-6503-0").createBook();

        CartItem item = CartItem.aNewItem()
                                .withBook(bookNotInCatalog)
                                .andQuantityOf(2);

        assertThatExceptionOfType(AddBookToCartException.class)
                .isThrownBy(() -> cart.addToCart(item))
                .withMessage("Book " + item.book() + " is not in catalog.");
        assertThat(cart.isEmpty()).isTrue();
    }
}
