package com.erich.factura.Services.Impl;

import com.erich.factura.Entity.Invoice;
import com.erich.factura.Entity.InvoiceDetail;
import com.erich.factura.Entity.Product;
import com.erich.factura.Repository.ProductRepository;
import com.erich.factura.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    @Override
    public List<Product> findByName(String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product No eixste"));

    }
}
