package com.minipro.cashnotes.controller;

import com.minipro.cashnotes.dto.UserRequestDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.service.UserService;
import com.minipro.cashnotes.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    private List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    private UserResponseDto getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @PutMapping("{id}")
    private UserResponseDto update(
        @RequestBody UserRequestDto requestDto,
        @PathVariable UUID id
    ) {
        return userService.update(requestDto, id);
    }

    @DeleteMapping("{id}")
    private CustomResponseEntity<UserResponseDto> delete(@PathVariable UUID id) {
        return userService.deleteById(id);
    }
}
