package com.formacionbdi.springboot.app.productos.controllers;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    // Inyectar para poder leer propiedades del application.properties
    @Autowired
    private Environment env;

    // Value es para inyectar valores que tenemos configurados en los properties
    @Value("${server.port}")
    private Integer port;
    
    // Para obtener el puerto dinamico del servidor
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(producto -> {
            // producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            // producto.setPort(port);
            producto.setPort(webServerAppCtxt.getWebServer().getPort());
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) {

        Producto producto = productoService.findById(id);

        // Setear puerto
        // producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        // producto.setPort(port);
        producto.setPort(webServerAppCtxt.getWebServer().getPort());

        // Simular error
        boolean ok = false;

        // if(!ok) {
        //     throw new RuntimeException("No se pudo cargar el producto");
        // }

        return producto;
    }

}
