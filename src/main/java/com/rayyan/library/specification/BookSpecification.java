package com.rayyan.library.specification;

import com.rayyan.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%");
    }

    public static Specification<Book> priceGreaterThanOrEqual(Double minPrice) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Book> priceLessThanOrEqual(Double maxPrice) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Book> isAvailable(Boolean available) {
        return (root, query, cb) ->
                cb.equal(root.get("available"), available);
    }
}