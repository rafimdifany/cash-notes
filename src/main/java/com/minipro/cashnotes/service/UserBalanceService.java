package com.minipro.cashnotes.service;


import com.minipro.cashnotes.configuration.mapper.UserBalanceMapper;
import com.minipro.cashnotes.dto.UserBalanceDto;
import com.minipro.cashnotes.dto.UserDto;
import com.minipro.cashnotes.entity.UserBalance;
import com.minipro.cashnotes.repository.UserBalanceRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    @Autowired
    UserBalanceRepository userBalanceRepository;

    @Autowired
    ModelMapper modelMapper;


    public Page<UserBalanceDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserBalance> userBalancePage = userBalanceRepository.findAll(pageable);

        return userBalancePage.map(userBalance -> modelMapper.map(userBalance, UserBalanceDto.class));
    }

    public UserBalanceDto getById(Long id) throws NotFoundException {

        //TODO : Fix Model Mapper untuk case dimana mapping object yang memiliki field object entity lain (Long to UUID)
//        UserBalance userBalanceEntity = userBalanceRepository.findById(id).orElseThrow(() -> new NotFoundException("User Balance Not Found"));
//        UserBalanceDto userBalanceDto = UserBalanceDto.builder()
//                .id(userBalanceEntity.getId())
//                .balanceType(userBalanceEntity.getBalanceType())
//                .amount(userBalanceEntity.getAmount())
//                .createdAt(userBalanceEntity.getCreatedAt())
//                .updatedAt(userBalanceEntity.getUpdatedAt())
//                .user(modelMapper.map(userBalanceEntity.getUsers(), UserDto.class))
//                .build();
//        return userBalanceDto;
        return modelMapper.map(userBalanceRepository.findById(id).orElseThrow(() -> new NotFoundException("User Balance Not Found")), UserBalanceDto.class);
    }

    public UserBalanceDto create(UserBalanceDto request) {
        UserBalance savedUserBalance = userBalanceRepository.save(modelMapper.map(request, UserBalance.class));
        return modelMapper.map(savedUserBalance, UserBalanceDto.class);
    }
}
