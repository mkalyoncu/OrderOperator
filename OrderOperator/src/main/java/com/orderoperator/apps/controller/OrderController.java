package com.orderoperator.apps.controller;

import com.orderoperator.apps.entity.Order;
import com.orderoperator.apps.entity.OrderType;
import com.orderoperator.apps.exception.OrderNotFoundException;
import com.orderoperator.apps.service.OrderService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@RestController("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    List<Order> listAllOrders() {
        return orderService.findAll();
    }

    @PostMapping("/add")
    Order addOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/get")
    Order getOrder(@RequestParam(name = "id") int id) {
        Optional<Order> orderOptional = orderService.findById(id);

        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(id);
        }

        return orderOptional.get();
    }

    @GetMapping("/getByCustomer")
    List<Order> getOrderByCustomer(@RequestParam(name = "customerId") int customerId) {
        List<Order> orderList;

        try {
            orderList = orderService.findByCustomerId(customerId);
        } catch (DataIntegrityViolationException exception) {
            throw new OrderNotFoundException("customer id: " + customerId);
        }

        return orderList;
    }

    @DeleteMapping("/delete")
    void deleteOrder(@RequestParam(name = "id") int id) {
        orderService.deleteById(id);
    }

    @PutMapping("/update")
    Order updateCustomer(@RequestBody Order updatedOrder, @RequestParam(name = "id") int id) {
        Optional<Order> orderOptional = orderService.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setCustomerId(updatedOrder.getCustomerId());
            order.setType(updatedOrder.getType());
            order.setCustomerId(updatedOrder.getCustomerId());

            return orderService.save(order);
        } else {
            updatedOrder.setId(id);
            return orderService.save(updatedOrder);
        }
    }

    @GetMapping("/getByType")
    List<Order> getByType(@RequestParam(name = "type") OrderType type) {
        return orderService.findAllByType(type);
    }

    @GetMapping("/getByTypeAndCustomer")
    List<Order> getByTypeAndCustomer(@RequestParam(name = "type") OrderType type,
                           @RequestParam(name = "customerId") int customerId) {
        return orderService.findAllByTypeAndCustomerId(customerId, type);
    }
}
