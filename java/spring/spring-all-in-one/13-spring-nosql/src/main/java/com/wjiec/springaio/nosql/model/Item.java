package com.wjiec.springaio.nosql.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
@Builder
public class Item {

    @Id
    private Long id;

    private String title;

}
