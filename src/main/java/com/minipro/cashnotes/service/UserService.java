package com.minipro.cashnotes.service;

import com.minipro.cashnotes.dto.UserDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.entity.Users;
import com.minipro.cashnotes.repository.UserRepository;
import com.minipro.cashnotes.util.CustomResponseEntity;
import com.minipro.cashnotes.util.PasswordUtil;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    private ModelMapper modelMapper;

    private static final String USER_DELETE_MSG_SUCCESS = "User Successfully Deleted!";
    private static final String USER_NOT_FOUND_MSG = "User Not Found";
    private static final String EMAIL_CONFIRMATION = "Konfirmasi Email";

    private static final boolean TOGGLE_SEND_EMAIL_VERIFICATION = false;

    public UserResponseDto createUser(UserDto requestDto) {

        String hashedPassword = PasswordUtil.hashPassword(requestDto.getPassword());
        requestDto.setPassword(hashedPassword);

        Users usersEntity = userRepository.save( modelMapper.map(requestDto, Users.class));

        if(TOGGLE_SEND_EMAIL_VERIFICATION) CompletableFuture.runAsync(() -> emailService.sendEmail(usersEntity.getEmail(), EMAIL_CONFIRMATION, usersEntity.getId()));

        return modelMapper.map(usersEntity, UserResponseDto.class);
    }

    public Page<UserResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Users> usersPage = userRepository.findAll(pageable);

        return usersPage.map(users -> modelMapper.map(users, UserResponseDto.class));
    }

    public UserResponseDto getById(Long id) throws NotFoundException {
        Users userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
        return modelMapper.map(userEntity, UserResponseDto.class);
    }

    public UserResponseDto update(UserDto requestDto, Long id) throws NotFoundException {
        Users usersEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
        usersEntity.setEmail(requestDto.getEmail());

        return modelMapper.map(usersEntity, UserResponseDto.class);
    }

    public CustomResponseEntity<UserResponseDto> deleteById(Long id) {
        Users usersEntity = userRepository.findById(id).get();
        userRepository.deleteById(id);

        return CustomResponseEntity.<UserResponseDto>builder()
                .data(modelMapper.map(usersEntity, UserResponseDto.class))
                .message(USER_DELETE_MSG_SUCCESS)
                .build();
    }

    public Users getUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
    }

    public UserResponseDto verifyUser(Long id) throws NotFoundException {
        Users userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
        userEntity.setIsVerified(true);
        return modelMapper.map(userRepository.save(userEntity), UserResponseDto.class);
    }
}
