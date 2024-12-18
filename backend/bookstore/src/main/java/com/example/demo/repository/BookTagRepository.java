package com.example.demo.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.example.demo.entity.BookTag;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface BookTagRepository extends Neo4jRepository<BookTag, Long> {
    @Query("MATCH (t:Tag)-[:BELONGS_TO]->(t1:Tag)<-[:BELONGS_TO]-(t2:Tag) WHERE t.name = $tagName RETURN t2.name")
    List<String> findRelatedTags(@Param("tagName") String tagName);
}
