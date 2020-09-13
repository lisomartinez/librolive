package com.github.lmartinez84.librolive.cart.cart;

import com.github.lmartinez84.librolive.cart.AddBookToCartException;
import com.github.lmartinez84.librolive.cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Execution(ExecutionMode.CONCURRENT)
class CartTest {
    public static final String BOOK = "0-1826-2928-7";
    public static final String BOOK_NOT_IN_CATALOG = "0-1111-1111-1";
    public static final int BOOK_QUANTITY = 2;
    ;
    private static final String OTHER_BOOK = "0-2222-2222-2";
    private Cart cart;
    private Set<String> catalog;

    @BeforeEach
    void setUp() {
        catalog = new HashSet<>();
        catalog.add(BOOK);
        catalog.add(OTHER_BOOK);
        cart = Cart.newCartWithCatalog(catalog);

    }

    @Test
    void when_created_is_empty() {
        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void when_item_added_is_not_empty() {
        cart.addToCart(BOOK, BOOK_QUANTITY);
        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void can_know_its_items() {
        cart.addToCart(BOOK, BOOK_QUANTITY);
        assertThat(cart.hasContent(BOOK)).isTrue();
    }

    @Test
    void can_know_quantity_of_an_item() {
        cart.addToCart(BOOK, BOOK_QUANTITY);
        assertThat(cart.quantityOf(BOOK)).isEqualTo(BOOK_QUANTITY);
    }

    @Test
    void cannot_add_item_with_book_not_in_catalogue() {

        assertThatExceptionOfType(AddBookToCartException.class)
                .isThrownBy(() -> cart.addToCart(BOOK_NOT_IN_CATALOG, BOOK_QUANTITY))
                .withMessage("Book " + BOOK_NOT_IN_CATALOG + " is not in catalog.");
        assertThat(cart.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2})
    void cannot_add_a_book_with_zero_or_negative_quantity(int quantity) {
        assertThatExceptionOfType(AddBookToCartException.class)
                .isThrownBy(() -> cart.addToCart(BOOK, quantity))
                .withMessage("Quantity of Book " + BOOK + " should be positive.");

    }

    @Test
    void doesnt_hold_not_added_items() {
        assertThatExceptionOfType(AddBookToCartException.class)
                .isThrownBy(() -> cart.addToCart(BOOK_NOT_IN_CATALOG, BOOK_QUANTITY))
                .withMessage("Book " + BOOK_NOT_IN_CATALOG + " is not in catalog.");

        assertThatExceptionOfType(AddBookToCartException.class)
                .isThrownBy(() -> cart.addToCart(BOOK, -1))
                .withMessage("Quantity of Book " + BOOK + " should be positive.");

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void remember_the_number_of_add_items() {
        final int bookQuantity = 2;
        final int otherBookQuantity = 3;
        cart.addToCart(BOOK, bookQuantity);
        cart.addToCart(OTHER_BOOK, otherBookQuantity);
        assertThat(cart.itemsSellByStore()).isEqualTo(bookQuantity + otherBookQuantity);
    }
}
