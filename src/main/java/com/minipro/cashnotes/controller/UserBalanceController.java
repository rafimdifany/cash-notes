package com.minipro.cashnotes.controller;

import com.minipro.cashnotes.dto.UserBalanceDto;
import com.minipro.cashnotes.service.UserBalanceService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-balances")
public class UserBalanceController {

    @Autowired
    private UserBalanceService userBalanceService;

    @GetMapping()
    public Page<UserBalanceDto> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return userBalanceService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public UserBalanceDto findById(@PathVariable Long id) throws NotFoundException {
        return userBalanceService.getById(id);
    }

    @PostMapping()
    public UserBalanceDto create(@RequestBody UserBalanceDto userBalanceDto) {
        return userBalanceService.create(userBalanceDto);
    }
}
