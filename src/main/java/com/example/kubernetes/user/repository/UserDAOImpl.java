package com.example.kubernetes.user.repository;

import com.example.kubernetes.user.model.UserDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO{

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }
}
