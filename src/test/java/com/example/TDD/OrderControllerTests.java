package com.example.TDD;

import com.example.TDD.controller.OrderController;
import com.example.TDD.model.Order;
import com.example.TDD.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order();
        order.setCustomerName("Jo Jo");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("3124 Main St");
        order.setTotal(100.0);

        String orderDate = order.getOrderDate().toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"John Doe\",\"orderDate\":\"" + orderDate + "\",\"shippingAddress\":\"123 Main St\",\"total\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOrderById() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                new Order(1L, "John Doe", LocalDate.now(), "123 Main St", 100.0),
                new Order(2L, "Jane Smith", LocalDate.now(), "456 Oak St", 200.0)
        );

        Mockito.when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerName("Jane Smith");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("456 Oak St");
        order.setTotal(200.0);

        String orderDate = order.getOrderDate().toString();

        mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"customerName\":\"Jane Smith\",\"orderDate\":\"" + orderDate + "\",\"shippingAddress\":\"456 Oak St\",\"total\":200.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    public void testDeleteOrder() throws Exception {
//        Long orderId = 1L;
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", orderId))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    @Test
    public void testDeleteOrder() throws Exception {
        Long orderId = 1L;

        Mockito.when(orderService.deleteOrder(orderId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }




    @Test
    public void testCreateOrder_InvalidCustomerName() throws Exception {
        Order order = new Order();
        order.setCustomerName("");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("123 Apple Ave");
        order.setTotal(100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("customerName: must not be blank"));
    }

    @Test
    public void testCreateOrder_InvalidOrderDate() throws Exception {
        Order order = new Order();
        order.setCustomerName("Pippi Longstocking");
        order.setOrderDate(null);
        order.setShippingAddress("4934 North St");
        order.setTotal(89.00);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("orderDate: must not be null"));
    }

    @Test
    public void testCreateOrder_InvalidShippingAddress() throws Exception {
        Order order = new Order();
        order.setCustomerName("Pippi Longstocking");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("");
        order.setTotal(89.00);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("shippingAddress: must not be blank"));
    }

    @Test
    public void testCreateOrder_InvalidTotal() throws Exception {
        Order order = new Order();
        order.setCustomerName("Pippi Longstocking");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("4934 North St");
        order.setTotal(-100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("total: must be greater than 0"));
    }




}



