package com.orderoperator.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.orderoperator.apps.entity")
@EnableJpaRepositories("com.orderoperator.apps.repository.jpa")
@EnableElasticsearchRepositories("com.orderoperator.apps.repository.es")
@SpringBootApplication
public class OrderOperatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderOperatorApplication.class, args);
    }
}
