package com.orderoperator.apps.controller;

import com.orderoperator.apps.entity.Customer;
import com.orderoperator.apps.exception.CustomerNotFoundException;
import com.orderoperator.apps.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@RequestMapping("/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    List<Customer> listAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping("/add")
    Customer addCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @GetMapping("/get")
    Customer getCustomer(@RequestParam(name = "id") int id) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException(id);
        }

        return customerOptional.get();
    }

    @DeleteMapping("/delete")
    void deleteCustomer(@RequestParam(name = "id") int id) {
        customerService.deleteById(id);
    }

    @PutMapping("/update")
    Customer updateCustomer(@RequestBody Customer updatedCustomer, @RequestParam(name = "id") int id) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setName(updatedCustomer.getName());
            customer.setAge(updatedCustomer.getAge());

            return customerService.save(customer);
        } else {
            updatedCustomer.setId(id);
            return customerService.save(updatedCustomer);
        }
    }

    @GetMapping("/getByMinAge")
    List<Customer> getByMinAge(@RequestParam(name = "minAge") int age) {
        return customerService.findAllByAgeIsGreaterThanEqual(age);
    }

    @GetMapping("/getByMaxAge")
    List<Customer> getByMaxAge(@RequestParam(name = "maxAge") int age) {
        return customerService.findAllByAgeIsLessThanEqual(age);
    }

    @GetMapping("/getByMaxAgeAndName")
    List<Customer> getByMaxAgeAndName(@RequestParam(name = "maxAge") int age, @RequestParam(name = "name") String name) {
        return customerService.findAllByAgeIsLessThanEqualAndNameContains(age, name);
    }

    @GetMapping("/getByMinAgeAndName")
    List<Customer> getByMinAgeAndName(@RequestParam(name = "minAge") int age,
                                      @RequestParam(name = "name") String name) {
        return customerService.findAllByAgeIsGreaterThanAndNameContains(age, name);
    }

    @GetMapping("/getByNameNotContains")
    List<Customer> getByNameNotContains(@RequestParam(name = "name") String name) {
        return customerService.findAllByNameNotContains(name);
    }

    @GetMapping("/getByNameNotContainsAndAgeIsBetween")
    List<Customer> getByNameNotContainsAndAgeIsBetween(@RequestParam(name = "name") String name,
                                                       @RequestParam(name = "minAge") int minAge,
                                                       @RequestParam(name = "maxAge") int maxAge) {
        return customerService.findAllByNameNotContainsAndAgeIsBetween(name, minAge, maxAge);
    }

    @GetMapping("/getByNameNotContainsAndAgeIsLessThanEqual")
    List<Customer> getByNameNotContainsAndAgeIsLessThanEqual(@RequestParam(name = "name") String name,
                                                             @RequestParam(name = "maxAge") int age) {
        return customerService.findAllByNameNotContainsAndAgeIsLessThanEqual(name, age);
    }

    @GetMapping("/getByNameContains")
    List<Customer> getByNameContains(@RequestParam(name = "name") String name) {
        return customerService.findByNameContains(name);
    }
}
