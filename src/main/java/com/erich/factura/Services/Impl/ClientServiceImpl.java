package com.erich.factura.Services.Impl;

import com.erich.factura.Entity.Client;
import com.erich.factura.Exception.ClientServiceExepction;
import com.erich.factura.Repository.ClientRepository;
import com.erich.factura.Services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.erich.factura.Util.FileUtil.UPLOAD_FOLDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return (List<Client>) clientRepo.findAll();
    }


    @Transactional
    @Override
    public void save(Client client, MultipartFile foto) {
        if (!foto.isEmpty()) {
            if (client.getId() != null && client.getId() > 0 && client.getFoto() != null && client.getFoto().length() > 0) {
                Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(client.getFoto()).toAbsolutePath();
                File file = rootPath.toFile();

                if (file.exists() && file.canRead()) {
                    FileSystemUtils.deleteRecursively(file);
                }
            }
            String uniqueFileName = UUID.randomUUID() + "_" + foto.getOriginalFilename();
            Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(uniqueFileName);
            Path rootAbsolute = rootPath.toAbsolutePath();
            log.info("rootPath : " + rootPath);
            log.info("rootAbsolute : " + rootAbsolute);

            try {
                Files.copy(foto.getInputStream(), rootAbsolute);//otra alternativa

                /*byte[] bytes = foto.getBytes();
                Path rutaCompleta = Paths.get(rotPath + "//" + UUID.randomUUID() + "_" + foto.getOriginalFilename());
                Files.write(rutaCompleta, bytes);*/

                client.setFoto(uniqueFileName);
            } catch (IOException e) {
                log.debug("Ocurrio un error al insertar la foto" + e.getMessage());
            }
        }

        clientRepo.save(client);
    }

    @Transactional(readOnly = true)
    @Override
    public Client update(Long id) {
        return clientRepo.findById(id).orElseThrow(() -> new ClientServiceExepction("Cliente no encontrado"));

    }

    @Transactional
    @Override
    public void delete(Long id) {
        Client client = update(id);
        if (client.getId() > 0) {
            clientRepo.delete(client);
            Path rootPath = Paths.get("upload").resolve(client.getFoto()).toAbsolutePath();
            File file = rootPath.toFile();
            if (file.exists() && file.canRead()) {
                if (file.delete()) {
                    log.info("Foto eliminada con exito");
                }
            }
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Page<Client> page(Pageable pageable) {
        return clientRepo.findAll(pageable);
    }
}
