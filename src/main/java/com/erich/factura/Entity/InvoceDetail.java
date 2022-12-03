package com.erich.factura.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "invoices_details")
public class InvoceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public BigDecimal calculateAmount() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }
}
