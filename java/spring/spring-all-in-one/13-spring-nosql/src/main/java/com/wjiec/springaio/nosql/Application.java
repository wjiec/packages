package com.wjiec.springaio.nosql;

import com.mongodb.client.MongoClient;
import com.wjiec.springaio.nosql.model.Book;
import com.wjiec.springaio.nosql.repository.BookRepository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.List;

@ComponentScan
@Configuration
@EnableMongoRepositories
@EnableNeo4jRepositories
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        MongoOperations mongoTemplate = context.getBean(MongoOperations.class);
        System.out.println(mongoTemplate);

        System.out.println(mongoTemplate.getCollection("book").countDocuments());
        Book book = Book.builder().isbn("978-7-115-41730-5").title("Spring in action").authors(List.of(
            Book.Author.builder().firstName("Craig").lastName("Walls").build(),
            Book.Author.builder().firstName("Zhang").lastName("Weibing").build()
        )).build();
        System.out.println(mongoTemplate.save(book));

        List<Book> books = mongoTemplate.find(Query.query(Criteria.where("authors.first_name").is("Craig")), Book.class);
        System.out.println(books);

        BookRepository bookRepository = context.getBean(BookRepository.class);
        System.out.println(bookRepository);
        System.out.println(bookRepository.findAll());
        System.out.println(bookRepository.findByAuthorName("Craig"));

        if (books.size() > 1) {
            for (int i = 0; i < books.size() - 1; i++) {
                mongoTemplate.remove(books.get(i));
            }
        }
    }

    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean() {
        MongoClientFactoryBean factoryBean = new MongoClientFactoryBean();

        return factoryBean;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "spring");
    }

    @Bean
    public Driver driver() {
        return GraphDatabase.driver("bolt://localhost:7687");
    }

}
