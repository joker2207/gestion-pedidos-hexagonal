package com.pedidos.infrastructure.adapter.out.persistence.mapper;

import com.pedidos.domain.model.Producto;
import com.pedidos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toDomain(ProductoEntity entity) {
        return new Producto(entity.getId(), entity.getNombre(), entity.getPrecio(), entity.getStock());
    }

    public ProductoEntity toEntity(Producto producto) {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(producto.getId());
        entity.setNombre(producto.getNombre());
        entity.setPrecio(producto.getPrecio());
        entity.setStock(producto.getStock());
        return entity;
    }
}
