package com.minipro.cashnotes.service;

import com.minipro.cashnotes.dto.UserRequestDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.entity.Users;
import com.minipro.cashnotes.repository.UserRepository;
import com.minipro.cashnotes.util.CustomResponseEntity;
import com.minipro.cashnotes.util.PasswordUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    ModelMapper modelMapper = new ModelMapper();

    private static final String USER_DELETE_MSG_SUCCESS = "User Successfully Deleted!";

    public UserResponseDto createUser(UserRequestDto requestDto) {

        String hashedPassword = PasswordUtil.hashPassword(requestDto.getPassword());
        requestDto.setPassword(hashedPassword);

        Users usersEntity = userRepository.save( modelMapper.map(requestDto, Users.class));

        return modelMapper.map(usersEntity, UserResponseDto.class);
    }

    public Page<UserResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Users> usersPage = userRepository.findAll(pageable);

        return usersPage.map(users -> modelMapper.map(users, UserResponseDto.class));
    }

    public UserResponseDto getById(UUID uuid) {
        return modelMapper.map( userRepository.findById(uuid).get(), UserResponseDto.class );
    }

    public UserResponseDto update(UserRequestDto requestDto, UUID uuid) throws NotFoundException {
        Users usersEntity = userRepository.findById(uuid).orElseThrow(() -> new NotFoundException());
        usersEntity.setEmail(requestDto.getEmail());

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
