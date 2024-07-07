package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.repository.AnimalRepository;
import com.petadoptionapp.petadoptionapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
//    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword())); todo
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
        Optional.ofNullable(user.getUsername()).ifPresent(userDb::setUsername);
        Optional.ofNullable(user.getFirstName()).ifPresent(userDb::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(userDb::setLastName);
        Optional.ofNullable(user.getEmail()).ifPresent(userDb::setEmail);
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(userDb::setPhoneNumber);
        Optional.ofNullable(user.getPassword()).ifPresent(userDb::setPassword);
//        Optional.ofNullable(user.getPassword()).ifPresent(pwd -> userDb.setPassword(passwordEncoder.encode(pwd)));
        Optional.ofNullable(user.getRole()).ifPresent(userDb::setRole);
        Optional.ofNullable(user.getAdoptions()).ifPresent(userDb::setAdoptions);
        Optional.ofNullable(user.getPreferences()).ifPresent(userDb::setPreferences);
        // notifications todo
        return userDb;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authority));
    }

    public Set<Animal> getUserFavorites(String username) {
        User user = findByUsername(username);
        System.out.println("LOg " + user);
        System.out.println("Log 2 " + animalRepository.findFavoriteAnimalsByUserId(user.getId()));
        return animalRepository.findFavoriteAnimalsByUserId(user.getId());
    }
}
