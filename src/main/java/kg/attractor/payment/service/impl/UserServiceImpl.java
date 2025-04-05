package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.UserDao;
import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.exception.UserAlreadyExistsException;
import kg.attractor.payment.exception.UserNotFoundException;
import kg.attractor.payment.mapper.UserMapper;
import kg.attractor.payment.model.User;
import kg.attractor.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public Long register(UserDto userDto) {
        log.info("Registering user: {}", userDto);

        validateUser(userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String name  =  StringUtils.capitalize(userDto.getName().toLowerCase());
        userDto.setName(name);

        return dao.register(userMapper.toEntity(userDto));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = dao.getUserByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        log.info("Retrieved user by email : {}", email);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto blockUser(Long id){
        getUserById(id);
        dao.blockUser(id);
        log.info("Blocked user: {}", id);
        return getUserById(id);
    }

    public UserDto getUserById(Long id){
        User user = dao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        log.info("Retrieved user by id : {}", user.getId());
        return userMapper.toDto(user);
    }

    private void validateUser(String email) {
        Boolean isEmailValid = dao.existsUserByEmail(email);
        if(Boolean.TRUE.equals(isEmailValid)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
    }

    public UserDto getAuthUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = getUserByEmail(username);

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return user;
    }

    @Override
    public Long getAuthId(){
        return getAuthUser().getId();
    }
}
