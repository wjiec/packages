package com.wjiec.springaio.nosql.repository.book;

import com.wjiec.springaio.nosql.model.Book;

public interface BookOperations {

    Book findByAuthorName(String firstName);

}
