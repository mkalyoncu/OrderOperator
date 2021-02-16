package com.orderoperator.apps.repository.es;

import com.orderoperator.apps.entity.Order;
import com.orderoperator.apps.entity.OrderType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OrderESRepository extends ElasticsearchRepository<Order, Integer> {
    List<Order> findAll();
    List<Order> findAllByCustomerId(int customerId);
    List<Order> findAllByTypeAndCustomerId(OrderType orderType, int customerId);
}
