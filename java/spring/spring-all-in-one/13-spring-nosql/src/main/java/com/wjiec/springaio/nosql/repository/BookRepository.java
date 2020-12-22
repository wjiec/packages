package com.wjiec.springaio.nosql.repository;

import com.wjiec.springaio.nosql.model.Book;
import com.wjiec.springaio.nosql.repository.book.BookOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookOperations {
}
