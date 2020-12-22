package com.wjiec.springaio.nosql.repository.book;

import com.wjiec.springaio.nosql.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookOperationsImpl implements BookOperations {

    private final MongoOperations mongoOperations;

    @Autowired
    public BookOperationsImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Book findByAuthorName(String firstName) {
        List<Book> books = mongoOperations.find(Query.query(
            Criteria.where("authors.first_name").is(firstName)), Book.class);
        return books.isEmpty() ? null : books.get(0);
    }
}
