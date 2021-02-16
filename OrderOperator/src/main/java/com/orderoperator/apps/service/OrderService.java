package com.orderoperator.apps.service;

import com.orderoperator.apps.entity.Order;
import com.orderoperator.apps.entity.OrderType;
import com.orderoperator.apps.repository.es.OrderESRepository;
import com.orderoperator.apps.repository.jpa.OrderRepository;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

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
public class OrderService {

    private final OrderESRepository orderESRepository;
    private final OrderRepository orderRepository;
    private final ElasticsearchRestTemplate restTemplate;

    @Autowired
    public OrderService(OrderESRepository orderESRepository,
                        OrderRepository orderRepository,
                        ElasticsearchRestTemplate restTemplate) {
        this.orderESRepository = orderESRepository;
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order save(final Order order) {
        Order savedOrder = orderRepository.save(order);

        return orderESRepository.save(savedOrder);
    }

    public List<Order> findAll() {
        return orderESRepository.findAll();
    }

    public Optional<Order> findById(final int id) {
        return orderESRepository.findById(id);
    }

    public List<Order> findByCustomerId(final int customerId) {
        return orderESRepository.findAllByCustomerId(customerId);
    }

    public void deleteById(final int id) {
        orderRepository.deleteById(id);
        orderESRepository.deleteById(id);
    }

    public List<Order> findAllByType(final OrderType orderType) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("type", orderType.name()));

        Query query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        SearchHits<Order> customerHits = restTemplate
                .search(query,
                        Order.class,
                        IndexCoordinates.of("order"));

        List<Order> result = new ArrayList<>();
        customerHits.forEach(orderHit -> result.add(orderHit.getContent()));

        return result;
    }

    public List<Order> findAllByTypeAndCustomerId(final int id, final OrderType orderType) {
        return orderESRepository.findAllByTypeAndCustomerId(orderType, id);
    }
}
