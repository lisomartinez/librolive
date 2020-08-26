package com.github.lmartinez84.librolive.cart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BookCatalogTest {
    @Test
    void when_created_catalog_is_empty() {
        BookCatalog catalog = new BookCatalog();
        assertThat(catalog.isEmpty()).isTrue();
    }

    @Test
    void can_add_a_book_to_catalog() {
        BookCatalog catalog = new BookCatalog();
        catalog.add(Book.aBook().withIsbn("isbn").createBook());
        assertThat(catalog.isEmpty()).isFalse();
    }

    @Test
    void knows_its_books() {
        BookCatalog catalog = new BookCatalog();
        catalog.add(Book.aBook().withIsbn("isbn").createBook());
        assertThat(catalog.hasBook(Book.aBook().withIsbn("isbn").createBook())).isTrue();
    }
}
