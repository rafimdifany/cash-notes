package com.minipro.cashnotes.service;

import com.minipro.cashnotes.dto.UserRequestDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.entity.Users;
import com.minipro.cashnotes.repository.UserRepository;
import com.minipro.cashnotes.util.CustomResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    ModelMapper modelMapper = new ModelMapper();

    private static final String USER_DELETE_MSG_SUCCESS = "User Successfully Deleted!";

    public List<UserResponseDto> getAll() {
        List<Users> usersEntity = userRepository.findAll();
        return usersEntity.stream().map(users -> modelMapper.map(users, UserResponseDto.class)).collect(Collectors.toList());
    }

    public UserResponseDto getById(UUID uuid) {
        return modelMapper.map( userRepository.findById(uuid).get(), UserResponseDto.class );
    }

    public UserResponseDto update(UserRequestDto requestDto, UUID uuid) {
        Users usersEntity = userRepository.findById(uuid).get();

        usersEntity = modelMapper.map(requestDto, Users.class);

        return modelMapper.map(usersEntity, UserResponseDto.class);
    }

    public CustomResponseEntity<UserResponseDto> deleteById(UUID uuid) {
        Users usersEntity = userRepository.findById(uuid).get();
        userRepository.deleteById(uuid);

        return CustomResponseEntity.<UserResponseDto>builder()
                .data(modelMapper.map(usersEntity, UserResponseDto.class))
                .message(USER_DELETE_MSG_SUCCESS)
                .build();
    }
}
