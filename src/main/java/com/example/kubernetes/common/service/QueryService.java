package com.example.kubernetes.common.service;
import com.example.kubernetes.common.component.Messenger;

import java.util.List;
import java.util.Optional;

public interface QueryService<T> {
    List<T> findAll() ;
    Optional<T> findById(Long id);
    Messenger count();
    boolean existsById(Long id);
}