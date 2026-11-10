package com.pedidos.infrastructure.adapter.out.persistence.repository;

import com.pedidos.infrastructure.adapter.out.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findAllByOrderByIdDesc();
}
