package com.wjiec.springaio.nosql.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@Document
public class Book {

    @Id
    private String id;

    private String isbn;

    private String title;

    @Field("publication_time")
    private String publicationTime;

    private List<Author> authors;

    @Data
    @Builder
    public static class Author {
        @Field("first_name")
        private String firstName;

        @Field("last_name")
        private String lastName;
    }

}
