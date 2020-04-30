package com.quan.nguyen.demo.replication.repo.read;

import com.quan.nguyen.demo.replication.core.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReadRepository extends JpaRepository<Product,Long> {
}