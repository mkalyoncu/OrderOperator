package com.orderoperator.apps.repository.es;

import com.orderoperator.apps.entity.Customer;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerESRepository extends ElasticsearchRepository<Customer, Integer> {
    List<Customer> findAll();
    List<Customer> findAllByAgeIsGreaterThanEqual(int age);
    List<Customer> findAllByAgeIsLessThanEqual(int age);
}
