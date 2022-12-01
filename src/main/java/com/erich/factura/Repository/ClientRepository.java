package com.erich.factura.Repository;

import com.erich.factura.Entity.Client;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface ClientRepository extends PagingAndSortingRepository<Client,Long> {

}
