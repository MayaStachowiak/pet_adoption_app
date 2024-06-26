package com.petadoptionapp.petadoptionapp.controller;

import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.mapper.UserMapper;
import com.petadoptionapp.petadoptionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.UserDto;
import org.openapitools.model.UsersIdGet404ResponseDto;
import org.openapitools.model.UsersPost200ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsersPost200ResponseDto> saveUser(@RequestBody UserDto userDto) {
        User userToSave = userService.save(userMapper.map(userDto));
        UserDto userToSaveDto = userMapper.map(userToSave);
        UsersPost200ResponseDto responseDto = new UsersPost200ResponseDto().user(userToSaveDto);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDto userDto = userMapper.map(userService.getUserById(id));
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UsersIdGet404ResponseDto());
        }

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUserById(@RequestBody UserDto userDto, @PathVariable Long id) {
        return userMapper.map(userService.updateById(userMapper.map(userDto), id));
    }

    @GetMapping("/all")
    public Page<UserDto> getUserPage(@RequestParam int page, @RequestParam int size) {
        Page<User> userPage = userService.getPage(page, size);
        return  userPage.map(userMapper::map);
    }
}