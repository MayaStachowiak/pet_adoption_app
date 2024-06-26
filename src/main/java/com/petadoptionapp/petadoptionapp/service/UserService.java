package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> getPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public User updateById(User user, Long id) {
        User userDb = getUserById(id);
        Optional.ofNullable(user.getFirstName()).ifPresent(userDb::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(userDb::setLastName);
        Optional.ofNullable(user.getEmail()).ifPresent(userDb::setEmail);
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(userDb::setPhoneNumber);
        Optional.ofNullable(user.getPassword()).ifPresent(userDb::setPassword);
        Optional.ofNullable(user.getAdoptions()).ifPresent(userDb::setAdoptions);
        Optional.ofNullable(user.getPreferences()).ifPresent(userDb::setPreferences);
        // notifications todo
        return userDb;
    }
}
