package com.minipro.cashnotes.controller;

import com.minipro.cashnotes.dto.UserRequestDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.service.UserService;
import com.minipro.cashnotes.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    private Page<UserResponseDto> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return userService.getAll(page, size);
    }

    @GetMapping("{id}")
    private UserResponseDto getById(@PathVariable UUID id) {
        return userService.getById(id);
    }


    @PostMapping
    private UserResponseDto createUsers(
           @RequestBody @Valid UserRequestDto userRequestDto
    ) {
        return userService.createUser(userRequestDto);
    }

    @PutMapping("{id}")
    private UserResponseDto update(
        @RequestBody UserRequestDto requestDto,
        @PathVariable UUID id
    ) throws NotFoundException {
        return userService.update(requestDto, id);
    }

    @DeleteMapping("{id}")
    private CustomResponseEntity<UserResponseDto> delete(@PathVariable UUID id) {
        return userService.deleteById(id);
    }
}
