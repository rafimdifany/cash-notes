package com.minipro.cashnotes.service;


import com.minipro.cashnotes.dto.UserBalanceDto;
import com.minipro.cashnotes.entity.UserBalance;
import com.minipro.cashnotes.repository.UserBalanceRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    @Autowired
    UserBalanceRepository userBalanceRepository;

    @Autowired
    ModelMapper modelMapper;


    public UserBalanceDto getAll() {
        return modelMapper.map(userBalanceRepository.findAll(), UserBalanceDto.class);
    }

    public UserBalanceDto getById(Long id) throws NotFoundException {
        return modelMapper.map(userBalanceRepository.findById(id).orElseThrow(() -> new NotFoundException("User Balance Not Found")), UserBalanceDto.class);
    }

    public UserBalanceDto create(UserBalanceDto request) {
        UserBalance savedUserBalance = userBalanceRepository.save(modelMapper.map(request, UserBalance.class));
        return modelMapper.map(savedUserBalance, UserBalanceDto.class);
    }
}
