package com.mont.decor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mont.decor.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long>{

}
