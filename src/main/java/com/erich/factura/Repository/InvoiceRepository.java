package com.erich.factura.Repository;

import com.erich.factura.Entity.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice,Long> {

    @Query("select i from Invoice i join fetch i.client c join fetch i.details d join d.product where i.id = ?1")
    Invoice fetchByIdWithClientWhitInvoiceDetailWithProduct(Long id);
}
