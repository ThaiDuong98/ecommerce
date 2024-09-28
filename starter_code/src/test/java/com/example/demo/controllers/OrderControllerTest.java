package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.demo.utils.TestUtils.*;
import static junit.framework.TestCase.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void testSubmitOrderSuccess() {
        User user = createUser(USERNAME, PASSWORD);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit(USERNAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testSubmitOrderUserNotFound() {
        when(userRepository.findByUsername("sun")).thenReturn(null);

        ResponseEntity<UserOrder> response = orderController.submit("sun");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUserSuccess() {
        User user = createUser(USERNAME, PASSWORD);
        List<UserOrder> orders = createOrders(USERNAME, PASSWORD);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(orders);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(USERNAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUserNotFound() {
        when(userRepository.findByUsername("sun")).thenReturn(null);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("sun");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

}
