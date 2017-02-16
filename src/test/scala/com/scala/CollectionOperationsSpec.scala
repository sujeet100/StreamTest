package com.scala

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner


/**
  * Created by kamthes on 12/02/17.
  */
@RunWith(classOf[JUnitRunner])
class CollectionOperationsSpec extends FlatSpec {

  case class Book(name: String, author: String, numberOfPages: Int)

  it should "return all books by an author" in {
    val books = List[Book](
      new Book("Clean Code", "Bob", 100),
      new Book("Refactoring", "Martin", 300),
      new Book("Extreme Programming", "Bob", 200),
      new Book("TDD", "Kent", 250)
    )

    //findAll or filter: Find all books written by 'Bob'
    val booksByBob = List(new Book("Clean Code", "Bob", 100), new Book("Extreme Programming", "Bob", 200))
    assert(books.filter((book: Book) => book.author == "Bob") == booksByBob)
    assert(books.filter(book => book.author == "Bob") == booksByBob)
    assert(books.filter(_.author == "Bob") == booksByBob)

    //find: Find a book with name "TDD"
    assert(books.find(_.name == "TDD") == Option(new Book("TDD", "Kent", 250)))

    //limit: Find 2 books with page numbers greater than 100
    assert(books.filter {
      _.numberOfPages > 100
    }.take(2) == List(new Book("Refactoring", "Martin", 300), new Book("Extreme Programming", "Bob", 200)))


    //forEach: Print the name of each book
    books.foreach(book => println(book.name))

    //collect or map: Get all the authors
    assert(books.map(_.author) == List("Bob", "Martin", "Bob", "Kent"))

    //distinct: Get all distinct authors
    assert(books.map(_.author).distinct == List("Bob", "Martin", "Kent"))

    //any: is any book written by 'Bob'
    assert(books.exists(_.author == "Bob"))

    //all or every: if every book has at least 50 pages
    assert(books.forall(_.numberOfPages > 50))

    //inject or fold or reduce: Get the sum of number of pages of all books
    assert(books.foldLeft(0)(_ + _.numberOfPages) == 850)
    assert(books.foldLeft(0)((a, b) => a + b.numberOfPages) == 850)
    assert(books.map(_.numberOfPages).reduce(_ + _) == 850)
    //comma separated string of books
    assert(books.foldLeft("")((a, b) => a + ", " + b.name).replaceFirst(", ", "") == "Clean Code, Refactoring, Extreme Programming, TDD")
    assert(books.map(_.name).reduce(_ + ", " + _) == "Clean Code, Refactoring, Extreme Programming, TDD")

    //Sum: Total number of pages
    assert(books.map(_.numberOfPages).sum == 850)

    //Max: Which book has maximum number of pages
    assert(books.maxBy(_.numberOfPages) == new Book("Refactoring", "Martin", 300))

    //head
    assert((1 to 5).toList.head == 1)

    //tail
    assert((1 to 5).toList.tail == (2 to 5).toList)


  }

}
