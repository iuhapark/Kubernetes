package com.example.kubernetes.user.service;

import com.example.kubernetes.common.component.security.JwtProvider;
import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.user.model.User;
import com.example.kubernetes.user.model.UserDto;
import com.example.kubernetes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public Messenger save(UserDto dto) {
        entityToDto((repository.save(dtoToEntity(dto))));
        System.out.println(" ============ UserServiceImpl save instanceof =========== ");
        return Messenger.builder()
                .message(repository.existsById(dto.getId()) ? "SUCCESS" : "FAILURE")
                .build();
    }

    @Transactional
    @Override
    public Messenger deleteById(Long id) {
        repository.deleteById(id);
        return Messenger.builder()
                .message(repository.findById(id).isPresent() ? "FAILURE" : "SUCCESS")
                .build();
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAllByOrderByIdDesc().stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return repository.findById(id).map(i -> entityToDto(i));
    }

    @Override
    public Messenger count() {
        return Messenger.builder().message(repository.count() + "").build();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public Messenger modify(UserDto dto) {
        Optional<User> optionalUser = repository.findById(dto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            User modifyUser = user.toBuilder()
                    .password(dto.getPassword())
                    .job(dto.getJob())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .build();
            Long updateUserId = repository.save(modifyUser).getId();

            return Messenger.builder()
                    .message("SUCCESS ID" + updateUserId)
                    .build();
        } else {
            return Messenger.builder()
                    .message("FAIL")
                    .build();
        }
    }

    @Override
    public List<UserDto> findByName(String name) {
        return repository.findByName(name).stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    public List<UserDto> findByEmail(String email) {
        return repository.findByEmail(email).stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    public List<UserDto> findByJob(String job) {
        return repository.findByJob(job).stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    //SRP 에 따라 아이디 존재여부를 프론트에서 먼저 판단하고, 넘어옴(시큐리티)
    @Transactional
    @Override
    public Messenger login(UserDto dto) {
        log.info("Parameters received through login service" + dto);
        User user = repository.findByUsername(dto.getUsername()).get();
        String accessToken = jwtProvider.createToken(entityToDto(user));
        boolean flag = user.getPassword().equals(dto.getPassword());
        if (flag) {
            repository.modifyTokenById(user.getId(), accessToken);
        }
        jwtProvider.printPayload(accessToken);
        return Messenger.builder()
                .message(flag ? "SUCCESS" : "FAILURE")
                .accessToken(flag ? accessToken : "None")
                .build();
    }

    @Transactional
    @Override
    public Boolean logout(String accessToken) {
        Long id = jwtProvider.getPayload(accessToken.substring(7)).get("id",Long.class);
        repository.modifyTokenById(id, "");
        return repository.findById(id).get().getToken().isEmpty();
    }

    @Override
    public Boolean existsUsername(String username) {
        Integer count = repository.existsByUsername(username);
        return count == 1;
    }


}
