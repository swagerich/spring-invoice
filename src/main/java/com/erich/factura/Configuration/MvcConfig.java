package com.erich.factura.Configuration;


import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Utitlizamos WebMvcConfigurer  esta configuracion para guardar en el directorio de wndows o linux
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        String resourcePath = Paths.get("upload").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(resourcePath);
    }

    //UPLOAD DE MANERA PROGRAMATICA
    /*@SneakyThrows
    @GetMapping("/upload/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){
        Path pathFoto = Paths.get("upload").resolve(filename).toAbsolutePath();
        Resource recurso = new UrlResource(pathFoto.toUri());
        if(!recurso.exists() && !recurso.isReadable()){
            throw  new RuntimeException("No se peude cargar la imagen" + pathFoto);
        }
        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ recurso.getFilename() +"\"")
                .body(recurso);
    }*/


    /**
     * UTILIZAMOS EL ViewControllerRegistry PARA MANEJAR LOS PERMISOS
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/error_403").setViewName("error_403");
    }
}
