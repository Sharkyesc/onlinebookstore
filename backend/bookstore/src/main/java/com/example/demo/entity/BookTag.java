package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class BookTag {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private BookTag(){}

    public BookTag(String name){this.name = name;}

    @Relationship(type = "relateBooks")
    public Set<BookTag> relateBookTags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}