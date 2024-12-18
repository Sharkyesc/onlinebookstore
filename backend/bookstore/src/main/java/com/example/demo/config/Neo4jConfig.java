package com.example.demo.config;

import org.neo4j.driver.Driver;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.example.demo.repository") // 指定 Neo4j Repository 的包路径
public class Neo4jConfig {

    @Bean
    public Driver driver() {
        return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "luoyiyu599"));
    }

    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }

    @Bean
    public Neo4jTransactionManager neo4jTransactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }

    @Bean("neo4jTemplate")
    public Neo4jTemplate neo4jTemplate(
            Neo4jClient neo4jClient,
            Neo4jMappingContext neo4jMappingContext,
            Neo4jTransactionManager transactionManager) {
        return new Neo4jTemplate(neo4jClient, neo4jMappingContext, transactionManager);
    }
}
