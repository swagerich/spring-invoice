package com.erich.factura.Repository;

import com.erich.factura.Entity.Client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends CrudRepository<Client,Long>, PagingAndSortingRepository<Client,Long> {

    @Query("select c from Client c left join fetch c.invoices i where c.id =?1")
    Client fetchByIdWithInvoces(Long id);
}
