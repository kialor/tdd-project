package com.example.TDD;

import com.example.TDD.model.Order;
import com.example.TDD.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OrderServiceTests {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("findOrder-read test")
    @Test
    public void testFindOrderById() {
        Order order = new Order();
        order.setCustomerName("Coco Chanel");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("648 Fashion Ave");
        order.setTotal(5000.0);
        Order savedOrder = orderRepository.save(order);


        Optional<Order> fetchedOrder = orderRepository.findById(savedOrder.getId());

        Assertions.assertTrue(fetchedOrder.isPresent());
        Assertions.assertEquals(savedOrder.getId(), fetchedOrder.get().getId());
    }

    @DisplayName("updateOrder-test")
    @Test
    public void testUpdateOrder() {
        Order order = new Order();
        order.setCustomerName("Bob Pants");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("4252 Bikini Bottom");
        order.setTotal(15.65);
        Order savedOrder = orderRepository.save(order);


        Optional<Order> fetchedOrder = orderRepository.findById(savedOrder.getId());
        Assertions.assertTrue(fetchedOrder.isPresent());


        Order updatedOrder = fetchedOrder.get();
        updatedOrder.setCustomerName("Patrick Star");
        updatedOrder.setShippingAddress("4250 Bikini Bottom");
        updatedOrder.setTotal(99.00);


        Order savedUpdatedOrder = orderRepository.save(updatedOrder);


        Optional<Order> fetchedUpdatedOrder = orderRepository.findById(savedUpdatedOrder.getId());
        Assertions.assertTrue(fetchedUpdatedOrder.isPresent());


        Assertions.assertEquals("Patrick Star", fetchedUpdatedOrder.get().getCustomerName());
        Assertions.assertEquals("4250 Bikini Bottom", fetchedUpdatedOrder.get().getShippingAddress());
        Assertions.assertEquals(99.00, fetchedUpdatedOrder.get().getTotal());
    }

    @DisplayName("deleteOrder-test")
    @Test
    public void testDeleteOrder() {

        Order order = new Order();
        order.setCustomerName("Sandy Cheeks");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("4213 Bikini Bottom");
        order.setTotal(100.0);
        Order savedOrder = orderRepository.save(order);


        Optional<Order> fetchedOrder = orderRepository.findById(savedOrder.getId());
        Assertions.assertTrue(fetchedOrder.isPresent());

        orderRepository.delete(fetchedOrder.get());
        Optional<Order> deletedOrder = orderRepository.findById(savedOrder.getId());
        Assertions.assertFalse(deletedOrder.isPresent());
    }

    @DisplayName("displayAll-test")
    @Test
    public void testGetAllOrders() {

        Order order1 = new Order();
        order1.setCustomerName("Sandy Cheeks");
        order1.setOrderDate(LocalDate.now());
        order1.setShippingAddress("4213 Bikini Bottom");
        order1.setTotal(100.0);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setCustomerName("Pearl Krabs");
        order2.setOrderDate(LocalDate.now());
        order2.setShippingAddress("2142 Bikini Bottom");
        order2.setTotal(200.0);
        orderRepository.save(order2);


        List<Order> allOrders = orderRepository.findAll();

        Assertions.assertFalse(allOrders.isEmpty());
        Assertions.assertTrue(allOrders.contains(order1));
        Assertions.assertTrue(allOrders.contains(order2));
    }
}
