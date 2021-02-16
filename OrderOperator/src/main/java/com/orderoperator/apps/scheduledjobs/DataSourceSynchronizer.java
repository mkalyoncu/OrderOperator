package com.orderoperator.apps.scheduledjobs;

import com.orderoperator.apps.entity.Customer;
import com.orderoperator.apps.entity.Order;
import com.orderoperator.apps.repository.es.CustomerESRepository;
import com.orderoperator.apps.repository.es.OrderESRepository;
import com.orderoperator.apps.repository.jpa.CustomerRepository;
import com.orderoperator.apps.repository.jpa.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSourceSynchronizer {

    private final CustomerESRepository customerESRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderESRepository orderESRepository;

    @Autowired
    public DataSourceSynchronizer(CustomerESRepository customerESRepository,
                                  CustomerRepository customerRepository,
                                  OrderRepository orderRepository,
                                  OrderESRepository orderESRepository) {
        this.customerESRepository = customerESRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderESRepository = orderESRepository;
    }

    @Scheduled(cron = "${cron.expression.for.customer}", zone = "Europe/Istanbul")
    private void synchronizeCustomers() {
        List<Customer> customerList = customerESRepository.findAll();
        customerRepository.saveAll(customerList);
    }

    @Scheduled(cron = "${cron.expression.for.order}", zone = "Europe/Istanbul")
    private void synchronizeOrders() {
        List<Order> orderList = orderESRepository.findAll();
        orderRepository.saveAll(orderList);
    }
}
