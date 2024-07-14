//package com.petadoptionapp.petadoptionapp.service;
//
//
//import com.petadoptionapp.petadoptionapp.bean.dao.User;
//import com.petadoptionapp.petadoptionapp.repository.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//
//    private UserRepository userRepository = mock(UserRepository.class);
//    private UserService userService;
//    private User user;
//
//    @BeforeEach
//    public void setUp() {
//        userService = new UserService(userRepository);
//        user = new User();
//        user.setFirstName("Zbyszek");
//    }
//
//    @Test
//    public void testSaveUser() {
//        when(userService.save(any(User.class))).thenReturn(user);
//
//        User saveUser = userService.save(user);
//
//        assertEquals("Zbyszek", saveUser.getFirstName());
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    public void testGetUserById() {
//        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
//
//        User resultUser = userService.getUserById(1l);
//
//        assertEquals(user, resultUser);
//    }
//
//
//    @Test
//    public void testGetUserById_NotFound() {
//        when(userRepository.findById(1l)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1l));
//    }
//
//
//    @Test
//    public void testDeleteUserById() {
//        userService.deleteById(1L);
//
//        verify(userRepository, times(1)).deleteById(1L);
//    }
//
//
//    @Test
//    public void testUpdateUserById() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        User userUpdate = new User();
//        userUpdate.setFirstName("Zdzisiek");
//
//        assertEquals("Zbyszek", user.getFirstName());
//
//        userService.updateById(userUpdate, 1l);
//
//        assertEquals("Zdzisiek", user.getFirstName());
//    }
//
//
//    @Test
//    public void testGetPageUser() {
//        User user2 = new User();
//        user2.setFirstName("Jadzia");
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        Page<User> userPage = new PageImpl<>(Arrays.asList(user, user2), pageRequest, 2);
//
//        when(userRepository.findAll(pageRequest)).thenReturn(userPage);
//
//        Page<User> page = userService.getPage(0, 10);
//
//        assertFalse(page.isEmpty());
//        assertEquals(2, page.getTotalElements());
//        assertEquals(user.getFirstName(), page.getContent().get(0).getFirstName());
//        assertEquals(user2.getFirstName(), page.getContent().get(1).getFirstName());
//
//    }
//
//
//}
