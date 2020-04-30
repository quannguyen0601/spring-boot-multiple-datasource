package com.quan.nguyen.demo.replication.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import com.quan.nguyen.demo.replication.core.model.Product;
import com.quan.nguyen.demo.replication.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping(value = "/api/product",consumes = "application/json")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        final var productSaved =  productService.saveOrUpdate(product);
        return ResponseEntity.ok().body(productSaved);
    }

    @GetMapping(value = "/api/product/{id}",consumes = "application/json")
    public ResponseEntity<Product> getById(@PathVariable("id") Long productId){
        final var productOption =  productService.getCurrentProductById(productId);
        return productOption.map(item -> ResponseEntity.ok().body(item))
                            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/api/products",consumes = "application/json")
    public ResponseEntity<List<Product>> getList(){
        final var productOption =  productService.getAllProduct();
        return ResponseEntity.ok().body(productOption);
    }
}