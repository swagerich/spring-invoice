package com.erich.factura.Services;

import com.erich.factura.Entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    Page<Client> page(Pageable pageable);
    void save(Client client, MultipartFile foto);

    Client update(Long id);

    void delete(Long id);
}
