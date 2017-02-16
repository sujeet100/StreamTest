package com.collections;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by kamthes on 12/02/17.
 */
public class CollectionOperationsTest {

    List<Book> books;

    @Before
    public void beforeEach() {
        books = new ArrayList<Book>() {
            {
                add(new Book("Clean Code", "Bob", 100));
                add(new Book("Refactoring", "Martin", 300));
                add(new Book("Extreme Programming", "Bob", 200));
                add(new Book("TDD", "Kent", 250));
            }
        };
    }

    @Test
    public void javaStreamOperationsTest() {


        //findAll or filter: Find all books written by 'Bob'
        List<Book> booksByBob = Arrays.asList(
                new Book("Clean Code", "Bob", 100),
                new Book("Extreme Programming", "Bob", 200)
        );

        assertThat(
                books.stream()
                        .filter(book -> book.getAuthor().equals("Bob"))
                        .collect(Collectors.toList())
                , is(booksByBob));

        assertThat(books.stream()
                        .filter((Book book) -> book.getAuthor().equals("Bob"))
                        .collect(Collectors.toList()),
                is(booksByBob));


        //find: Find a book with name "TDD"
        assertThat(books.stream()
                        .filter(book -> book.getName().equals("TDD"))
                        .findFirst(),
                is(Optional.of(new Book("TDD", "Kent", 250))));

        //limit: Find 2 books with page numbers greater than 100
        assertThat(books.stream()
                        .filter(book -> book.getNumberOfPages() > 100)
                        .limit(2)
                        .collect(Collectors.toList())
                , is(Arrays.asList(new Book("Refactoring", "Martin", 300), new Book("Extreme Programming", "Bob", 200))));

        //find last element in stream
        assertThat(books.stream()
                .filter((Book book) -> book.getAuthor().equals("Bob"))
                .reduce((a, b) -> b).get(), is(new Book("Extreme Programming", "Bob", 200)));

        assertThat(books.stream()
                .filter((Book book) -> book.getAuthor().equals("Bob"))
                .sorted((a, b) -> -1)
                .findFirst().get(), is(new Book("Extreme Programming", "Bob", 200)));


        //forEach: Print the name of each book
        books.stream()
                .map(Book::getName)
                .forEach(System.out::println);
        books.stream()
                .forEach(book -> System.out.println(book.getName()));


        //collect or map: Get all the authors
        assertThat(books.stream()
                        .map(Book::getAuthor)
                        .collect(Collectors.toList()),
                is(Arrays.asList("Bob", "Martin", "Bob", "Kent")));

        //distinct: Get all distinct authors
        assertThat(books.stream()
                        .map(Book::getAuthor)
                        .distinct()
                        .collect(Collectors.toList()),
                is(Arrays.asList("Bob", "Martin", "Kent")));


        //any: is any book written by 'Bob'
        assertTrue(books.stream()
                .anyMatch(book -> book.getAuthor().equals("Bob")));

        assertFalse(books.stream()
                .anyMatch(book -> book.getAuthor().equals("Robert")));

        //all or every: if every book has at least 50 pages
        assertTrue(books.stream()
                .allMatch(book -> book.getNumberOfPages() > 50));

        assertFalse(books.stream()
                .allMatch(book -> book.getNumberOfPages() > 200));

        //inject or fold or reduce: Get the sum of number of pages of all books
        assertThat(books.stream()
                .map(Book::getNumberOfPages)
                .reduce(0, (a, b) -> a + b), is(850));

        assertThat(books.stream()
                        .reduce(0, (a, book) -> a + book.getNumberOfPages(), (a, b) -> a + b),
                is(850));

        //fold: Get comma separated string of book names
        assertThat(books.stream()
                        .reduce("", (String a, Book b) -> a + ", " + b.getName(), (String a, String b) -> a + ", " + b)
                        .replaceFirst(", ", ""),
                is("Clean Code, Refactoring, Extreme Programming, TDD"));

        assertThat(books.stream()
                        .map(Book::getName)
                        .reduce((a, b) -> a + ", " + b).get(),
                is("Clean Code, Refactoring, Extreme Programming, TDD"));


        //Sum: Total number of pages
        assertThat(books.stream()
                .mapToInt(Book::getNumberOfPages)
                .sum(), is(850));


        //Max: Which book has maximum number of pages
        assertThat(books.stream()
                        .max(Comparator.comparingInt(Book::getNumberOfPages)).get(),
                is(new Book("Refactoring", "Martin", 300)));

        //Avg: Find average number of pages
        assertThat(books.stream()
                .mapToInt(Book::getNumberOfPages)
                .average()
                .getAsDouble(), is(212.5));


        //Print names of only 2 authors in capital who have written book of at least 100 pages
        books.stream()
                .filter(book -> book.getNumberOfPages() >= 200)
                .map(Book::getAuthor)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        books.stream()
                .filter(book -> book.getNumberOfPages() >= 200)
                .peek(e -> System.out.println("Filtered book: " + e))
                .map(Book::getAuthor)
                .peek(e -> System.out.println("Mapped author: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped to upper case: " + e))
                .forEach(System.out::println);

    }

}