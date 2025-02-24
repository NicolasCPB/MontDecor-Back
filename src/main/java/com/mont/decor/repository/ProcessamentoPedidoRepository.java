package com.mont.decor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mont.decor.model.ProcessamentoPedido;

@Repository
public interface ProcessamentoPedidoRepository extends JpaRepository<ProcessamentoPedido, Long>{

}
