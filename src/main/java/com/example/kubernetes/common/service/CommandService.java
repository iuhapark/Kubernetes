package com.example.kubernetes.common.service;

import com.example.kubernetes.common.component.Messenger;

public interface CommandService<T> {
    Messenger save(T t);
    Messenger deleteById(Long id);
    Messenger modify(T t);
}
