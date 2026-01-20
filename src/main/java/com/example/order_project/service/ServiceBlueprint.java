package com.example.order_project.service;

import java.util.List;

public interface ServiceBlueprint<T,A>{
    void create(T t);
    T get(Long id);
    T update(A a);
    void delete(Long id);
}
