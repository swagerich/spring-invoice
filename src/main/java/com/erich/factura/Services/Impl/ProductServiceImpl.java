package com.erich.factura.Services.Impl;

import com.erich.factura.Entity.Product;
import com.erich.factura.Repository.ProductRepository;
import com.erich.factura.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
