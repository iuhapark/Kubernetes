package com.example.kubernetes.user.service;

import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.common.service.CommandService;
import com.example.kubernetes.common.service.QueryService;
import com.example.kubernetes.user.model.User;
import com.example.kubernetes.user.model.UserDto;

import java.util.*;

public interface UserService extends CommandService<UserDto>, QueryService<UserDto> {
    Messenger modify(UserDto user);
    Optional<User> findByUsername(String username);
    List<UserDto> findByName(String name);
    List<UserDto> findByEmail(String email);
    List<UserDto> findByJob(String job);

    default User dtoToEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .job(dto.getJob())
                .build();
    }

    default UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .job(user.getJob())
                .build();
    }

    Messenger login(UserDto param);

    Boolean logout(String accessToken);

    Boolean existsUsername(String username);

}
