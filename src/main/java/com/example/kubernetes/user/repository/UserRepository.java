package com.example.kubernetes.user.repository;

import com.example.kubernetes.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    List<User> findByName(String name);
    List<User> findByJob(String job);
    List<User> findByEmail(String email);

    @Modifying
    @Query("update users a set a.token = :token where a.id = :id")
    void modifyTokenById(@Param("id") Long id, @Param("token") String token);

    @Query("select count(id) as count from users where username =:username")
    Integer existsByUsername(@Param("username") String username);
    //Query 문에서 Boolean 불가능, Integer count로 받아야 함

    List<User> findAllByOrderByIdDesc();
}
