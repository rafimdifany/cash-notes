package com.minipro.cashnotes.controller;

import com.minipro.cashnotes.dto.UserRequestDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.service.UserService;
import com.minipro.cashnotes.util.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("users")
@Validated
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
    public UserResponseDto getById(@PathVariable UUID id) {
        return userService.getById(id);
    }


    @PostMapping("/register")
    public UserResponseDto createUsers(
           @RequestBody @Valid UserRequestDto userRequestDto
    ) {
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(
        @RequestBody UserRequestDto requestDto,
        @PathVariable UUID id
    ) throws NotFoundException {
        return userService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    public CustomResponseEntity<UserResponseDto> delete(@PathVariable UUID id) {
        return userService.deleteById(id);
    }

    @PatchMapping("/{id}/verify")
    public UserResponseDto verifyUser(
            @PathVariable UUID id
    ) throws NotFoundException {
        return userService.verifyUser(id);
    }


}
