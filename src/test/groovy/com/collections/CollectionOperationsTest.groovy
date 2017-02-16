package com.collections

import spock.lang.Specification

/**
 * Created by kamthes on 12/02/17.
 */
public class CollectionOperationsSpec extends Specification {

    List<Book> books;

    def setup() {
        books = [
                new Book("Clean Code", "Bob", 100),
                new Book("Refactoring", "Martin", 300),
                new Book("Extreme Programming", "Bob", 200),
                new Book("TDD", "Kent", 250)
        ]
    }

    def "it should return books with author"() {
        def booksByBob = [new Book("Clean Code", "Bob", 100), new Book("Extreme Programming", "Bob", 200)]
        given:


        expect:

        //filter or findAll
        assert books.findAll { Book book -> book.author == "Bob" } == booksByBob
        assert books.findAll { book -> book.author == "Bob" } == booksByBob
        assert books.findAll { it.author == "Bob" } == booksByBob

        //find
        assert books.find { it.name == "TDD" } == new Book("TDD", "Kent", 250)

        //limit: Find 2 books with page numbers greater than 100
        assert books.findAll {
            it.numberOfPages > 100
        }.take(2) == [new Book("Refactoring", "Martin", 300), new Book("Extreme Programming", "Bob", 200)]

        //each
        books.each { println(it.name) }

        //collect or map
        assert books.collect { it.author } == ["Bob", "Martin", "Bob", "Kent"]

        //distinct
        assert books.collect { it.author }.unique() == ["Bob", "Martin", "Kent"]

        //any
        assert !books.any { it.author == "Robert" }
        assert books.any { it.author == "Bob" }

        //all
        assert books.every { it.numberOfPages > 50 }
        assert !books.every { it.numberOfPages > 200 }

        //inject or fold or reduce
        assert books.inject(0) { totalPages, book -> totalPages + book.numberOfPages } == 850

        assert books.collect {
            it.name
        }.inject { a, b -> a + ", " + b } == "Clean Code, Refactoring, Extreme Programming, TDD"

        //inject: Get comma separated book names as a string
        assert books.inject("") { a, b -> a + ", " + b.name }.replaceFirst(', ', '') == "Clean Code, Refactoring, Extreme Programming, TDD"

        //Max: Which book has maximum number of pages
        assert books.max { it.numberOfPages } == new Book("Refactoring", "Martin", 300)

        //Avg: Find avg no of pages
        assert books.sum { it.numberOfPages } / books.size() == 212.5

        //Sum
        assert books.sum { it.numberOfPages } == 850

    }

}