package com.orderoperator.apps.service;

import com.orderoperator.apps.entity.Customer;
import com.orderoperator.apps.repository.es.CustomerESRepository;
import com.orderoperator.apps.repository.jpa.CustomerRepository;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerESRepository customerESRepository;
    private final CustomerRepository customerRepository;
    private final ElasticsearchRestTemplate restTemplate;

    @Autowired
    public CustomerService(CustomerESRepository customerESRepository,
                           CustomerRepository customerRepository,
                           ElasticsearchRestTemplate restTemplate) {
        this.customerESRepository = customerESRepository;
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
    }

    public List<Customer> findAll() {
        return customerESRepository.findAll();
    }

    public Customer save(final Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        return customerESRepository.save(savedCustomer);
    }

    public Optional<Customer> findById(final int id) {
        return customerESRepository.findById(id);
    }

    public void deleteById(final int id) {
        customerRepository.deleteById(id);
        customerESRepository.deleteById(id);
    }

    public List<Customer> findAllByAgeIsGreaterThanEqual(final int age) {
        return customerESRepository.findAllByAgeIsGreaterThanEqual(age);
    }

    public List<Customer> findAllByAgeIsLessThanEqual(final int age) {
        return customerESRepository.findAllByAgeIsLessThanEqual(age);
    }

    public List<Customer> findAllByAgeIsLessThanEqualAndNameContains(final int age, final String name) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(new RangeQueryBuilder("age").lte(age))
                .must(new WildcardQueryBuilder("name", "*" + name + "*"));

        return searchWithQuery(queryBuilder);
    }

    public List<Customer> findAllByAgeIsGreaterThanAndNameContains(final int age, final String name) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("age").gt(age))
                .must(new WildcardQueryBuilder("name", "*" + name + "*"));

        return searchWithQuery(queryBuilder);
    }

    public List<Customer> findAllByNameNotContains(final String name) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .mustNot(new WildcardQueryBuilder("name", "*" + name + "*"));

        return searchWithQuery(queryBuilder);
    }

    public List<Customer> findAllByNameNotContainsAndAgeIsBetween(final String name, final int minAge, final int maxAge) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .mustNot(new WildcardQueryBuilder("name", "*" + name + "*"))
                .must(new RangeQueryBuilder("age").gte(minAge).lte(maxAge));

        return searchWithQuery(queryBuilder);
    }

    public List<Customer> findAllByNameNotContainsAndAgeIsLessThanEqual(final String name, final int age) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .mustNot(new WildcardQueryBuilder("name", "*" + name + "*"))
                .must(new RangeQueryBuilder("age").lte(age));

        return searchWithQuery(queryBuilder);
    }

    public List<Customer> findByNameContains(final String name) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(new WildcardQueryBuilder("name", "*" + name + "*"));

        return searchWithQuery(queryBuilder);
    }

    private List<Customer> searchWithQuery(QueryBuilder queryBuilder) {
        Query query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        SearchHits<Customer> customerHits = restTemplate
                .search(query,
                        Customer.class,
                        IndexCoordinates.of("customer"));

        List<Customer> result = new ArrayList<>();
        customerHits.forEach(customerHit -> result.add(customerHit.getContent()));

        return result;
    }
}
