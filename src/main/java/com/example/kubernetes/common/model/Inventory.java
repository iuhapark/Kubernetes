package com.example.kubernetes.common.model;

import java.util.ArrayList;

public class Inventory<T> {

    private ArrayList<T> inventory;

    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public void add(T t) {
        inventory.add(t);
    }

    public T remove(int i) {
        return inventory.remove(i);
    }

    public boolean contains(T t) {
        return inventory.contains(t);
    }

    public T get(int i) {
        return inventory.get(i);
    }

    public T clear() {
        return clear();
    }

}
