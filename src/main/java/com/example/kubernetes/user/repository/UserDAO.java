package com.example.kubernetes.user.repository;

import com.example.kubernetes.user.model.UserDto;

import java.util.List;

public interface UserDAO {
    List<UserDto> getAllUsers();

}
