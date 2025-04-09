package com.mont.decor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mont.decor.model.ItemAluguel;

@Repository
public interface ItemAluguelRepository extends JpaRepository<ItemAluguel, Long> {
}
