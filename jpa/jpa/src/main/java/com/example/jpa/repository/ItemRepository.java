package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    // SELECT count(ji.ID), SUM(ji.PRICE), AVG(ji.PRICE), MAX(ji.PRICE),
    // MIN(ji.PRICE) FROM JPA_ITEM ji;

    @Query("SELECT count(ji.ID), SUM(ji.price), AVG(ji.price), MAX(ji.price), MIN(ji.price) FROM Item ji")
    List<Object[]> aggreate();
}
