package com.minipro.cashnotes.service;

import com.minipro.cashnotes.dto.UserRequestDto;
import com.minipro.cashnotes.dto.UserResponseDto;
import com.minipro.cashnotes.entity.Users;
import com.minipro.cashnotes.repository.UserRepository;
import com.minipro.cashnotes.util.CustomResponseEntity;
import com.minipro.cashnotes.util.PasswordUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    private ModelMapper modelMapper;

    private static final String USER_DELETE_MSG_SUCCESS = "User Successfully Deleted!";

    public UserResponseDto createUser(UserRequestDto requestDto) {

        String hashedPassword = PasswordUtil.hashPassword(requestDto.getPassword());
        requestDto.setPassword(hashedPassword);

        Users usersEntity = userRepository.save( modelMapper.map(requestDto, Users.class));

        emailService.sendEmail(usersEntity.getEmail(), "Konfirmasi Email", usersEntity.getId());

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

    public Users getUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException());
    }

    public UserResponseDto verifyUser(UUID id) throws NotFoundException {
        Users userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        userEntity.setIsVerified(true);
        return modelMapper.map(userRepository.save(userEntity), UserResponseDto.class);
    }
}
