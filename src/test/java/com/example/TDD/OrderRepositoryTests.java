package com.example.TDD;

import com.example.TDD.model.Order;
import com.example.TDD.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("SaveOrder-create test")
    @Test
    public void testCreateOrder() {

        Order order = new Order();
        order.setCustomerName("Ken Nie");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("555 Maple St");
        order.setTotal(75.50);

        Order savedOrder = orderRepository.save(order);
        Assertions.assertNotNull(savedOrder.getId());
    }

    @DisplayName("FindOrder-read test")
    @Test
    public void testFindOrderById() {
        Order order = new Order();
        order.setCustomerName("El Boe");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("382 Main St");
        order.setTotal(150.0);
        Order savedOrder = orderRepository.save(order);


        Optional<Order> fetchedOrder = orderRepository.findById(savedOrder.getId());

        Assertions.assertTrue(fetchedOrder.isPresent());
        Assertions.assertEquals(savedOrder.getId(), fetchedOrder.get().getId());
    }
}