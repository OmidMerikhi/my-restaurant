package com.omid.food_service.food;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface EntityMapper<V, E> {
    V toViewModel(E entity);

    E toEntity(V viewModel);

    List<V> toViewModel(List<E> entity);

    List<E> toEntity(List<V> viewModel);
}
