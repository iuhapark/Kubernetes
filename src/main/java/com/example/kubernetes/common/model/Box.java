package com.example.kubernetes.common.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Box<T> {

    private Map<String, T> box;

    public Box() {
        this.box = new HashMap<>();
    }

    public T put(String str, T t) {
        return box.put(str, t);
    }

    public T get(String str) {
        return box.get(str);
    }

    public int size() {
        return box.size();
    }

    public T clear() {
        return clear();
    }

    public void put(List<String> list, Inventory<T> inventory) {
        box = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            box.put(list.get(i), inventory.get(i));
        }
        box.forEach(((str, t) -> System.out.println(String.format("%s : %s", str, t))));
    }
}
