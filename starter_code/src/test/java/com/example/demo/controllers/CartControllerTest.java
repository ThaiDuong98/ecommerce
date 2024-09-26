package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static com.example.demo.utils.TestUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private CartRepository cartRepository = mock(CartRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void tesAddToCart() throws Exception{
        User user = createUser(USERNAME, PASSWORD);
        Cart cart = TestUtils.createCart(user);
        user.setCart(cart);

        ModifyCartRequest newRequest = new ModifyCartRequest();

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        ResponseEntity<Cart> response = cartController.addTocart(newRequest);
        assertNotNull(response);
    }

    @Test
    public void tesRemoveFromCart() throws Exception{
        User user = createUser(USERNAME, PASSWORD);
        Cart cart = TestUtils.createCart(user);
        user.setCart(cart);

        ModifyCartRequest newRequest = new ModifyCartRequest();

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        ResponseEntity<Cart> response = cartController.removeFromcart(newRequest);
        assertNotNull(response);
    }
}
