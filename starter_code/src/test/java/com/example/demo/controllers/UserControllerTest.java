package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.demo.utils.TestUtils.*;
import static junit.framework.TestCase.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @Mock
    private CartRepository cartRepository = mock(CartRepository.class);

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void testFindByIdSuccess() {
        User user = createUser(USERNAME, PASSWORD);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindByUserNameSuccess() {
        User user = createUser(USERNAME, PASSWORD);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<User> response = userController.findByUserName(USERNAME);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindByUserNameNotFound() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        ResponseEntity<User> response = userController.findByUserName(USERNAME);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateUserSuccess() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmPassword(PASSWORD);

        when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn("hashedPassword");

        ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
