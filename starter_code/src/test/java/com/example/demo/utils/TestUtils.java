package com.example.demo.utils;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.ModifyCartRequest;

import java.math.BigDecimal;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static final String USERNAME = "duongpham";
    public static final String PASSWORD = "12345678";
    public static final String PASSWORD_HASHED = "hash";
    public static void injectObjects(Object target, String fieldName, Object objectToInject) {
        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            if (!f.isEnumConstant()) {
                // isAccessible is deprecated
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, objectToInject);
            if (wasPrivate) {
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static User createUser(String username, String password) {
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(password);
        user.setCart(createCart(user));
        return user;
    }

    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setId(1L);
        List<Item> items = createItems();
        cart.setItems(createItems());
        cart.setTotal(items.stream().map(Item::getPrice).reduce(BigDecimal::add).get());
        return cart;
    }

    public static List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            items.add(createItem(i));
        }
        return items;
    }

    public static Item createItem(long id) {
        Item item = new Item();
        item.setId(id);
        item.setPrice(BigDecimal.valueOf(id * 3));
        item.setName("newItem");
        item.setDescription("New item: " + item.getId());
        return item;
    }

    public static ModifyCartRequest createNewModifyCartRequest(){
        ModifyCartRequest newRequest = new ModifyCartRequest();
        newRequest.setUsername(USERNAME);
        newRequest.setItemId(1L);
        newRequest.setQuantity(5);

        return newRequest;
    }

    public static List<UserOrder> createOrders(String username, String password) {
        List<UserOrder> orders = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            UserOrder userOrder = new UserOrder();
            Cart cart = createCart(createUser(username, password));
            userOrder.setUser(createUser(username, password));
            userOrder.setId((long) i);
            userOrder.setItems(cart.getItems());
            userOrder.setTotal(cart.getTotal());
            orders.add(userOrder);
        }
        return orders;
    }
}
