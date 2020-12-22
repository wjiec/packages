package com.wjiec.springaio.nosql.repository;

import com.wjiec.springaio.nosql.model.Customer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends Neo4jRepository<Customer, Long> {
}
