package com.me.springapp;

import com.me.springapp.model.User;
import com.me.springapp.repository.RoleRepository;
import com.me.springapp.repository.UserRepository;
import com.me.springapp.service.UserService;
import com.me.springapp.service.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTests {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;

    @Before("")
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserServiceImpl();
        User user = User.builder()
            .id(1)
            .name("Gustavo")
            .lastName("Ponce")
            .email("test@test.com")
            .build();

        Mockito.when(mockUserRepository.save(any()))
            .thenReturn(user);
        Mockito.when(mockUserRepository.findByEmail(anyString()))
            .thenReturn(user);
    }

    private void initMocks(UserServiceTests userServiceTests) {
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        final User result = userServiceUnderTest.findUserByEmail(email);

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testSaveUser() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        User result = mockUserRepository.save(User.builder().build());

        // Verify the results
        assertEquals(email, result.getEmail());
    }
}
