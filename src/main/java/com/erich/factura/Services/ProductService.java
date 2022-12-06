package com.erich.factura.Services;

import com.erich.factura.Entity.Product;


import java.util.List;

public interface ProductService {

    List<Product> findByName(String name);
}
