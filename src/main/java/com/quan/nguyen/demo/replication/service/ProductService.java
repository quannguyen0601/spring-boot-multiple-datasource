package com.quan.nguyen.demo.replication.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.quan.nguyen.demo.replication.core.model.Product;
import com.quan.nguyen.demo.replication.repo.read.ProductReadRepository;
import com.quan.nguyen.demo.replication.repo.write.ProductWriteRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductReadRepository rProductRepo;
    private final ProductWriteRepository wProductRepo;
    
    public ProductService(ProductReadRepository rProductRepo, ProductWriteRepository wProductRepo){
        this.rProductRepo = rProductRepo;
        this.wProductRepo = wProductRepo;
    }

    public Optional<Product> getCurrentProductById(Long id) {
        return rProductRepo.findById(id);
    }

    public List<Product> getAllProduct() {
        return rProductRepo.findAll();
    }

    public Product saveOrUpdate(Product product){
        return wProductRepo.save(product);
    }

    public void delete(Long productId){
         wProductRepo.deleteById(productId);
    }
}