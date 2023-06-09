package com.minipro.cashnotes.controller;

import com.minipro.cashnotes.dto.UserDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.service.UserService;
import com.minipro.cashnotes.util.CustomResponseEntity;
import jakarta.validation.Valid;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("users")
@Validated
@CrossOrigin(value = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Page<UserResponseDto> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return userService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) throws NotFoundException {
        return userService.getById(id);
    }


    @PostMapping("/register")
    public UserResponseDto createUsers(
           @RequestBody @Valid UserDto userDto
    ) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(
        @RequestBody UserDto requestDto,
        @PathVariable Long id
    ) throws NotFoundException {
        return userService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    public CustomResponseEntity<UserResponseDto> delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @PatchMapping("/{id}/verify")
    public UserResponseDto verifyUser(
            @PathVariable Long id
    ) throws NotFoundException {
        return userService.verifyUser(id);
    }


}
