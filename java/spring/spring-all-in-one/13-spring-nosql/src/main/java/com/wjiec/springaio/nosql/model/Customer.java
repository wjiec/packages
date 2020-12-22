package com.wjiec.springaio.nosql.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Data
@Node
@Builder
public class Customer {

    @Id
    private Long id;

    private String name;

    @Relationship(type = "HAS_ITEMS")
    private Set<Item> items;

}
