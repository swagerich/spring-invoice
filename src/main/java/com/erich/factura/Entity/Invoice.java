package com.erich.factura.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the description cannot be empty")
    private String description;

    private String observation;

    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceDetail> details;

    @PrePersist
    public void prePersist() {
        this.date = new Date();
    }

    public Invoice() {
        this.details = new ArrayList<>();
    }

    public void addDetailInvoce(InvoiceDetail line) {
        this.details.add(line);
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(0.0);
        for (int i = 0; i < details.size(); i++) {
            total = total.add(details.get(i).calculateAmount());
        }
        return total;
    }

}
