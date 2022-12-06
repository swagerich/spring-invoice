package com.erich.factura.Repository;

import com.erich.factura.Entity.Product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
//    @Query("select p from Product  p where p.name like %?1%")
    List<Product> findByNameContainingIgnoreCase(String name);
}
