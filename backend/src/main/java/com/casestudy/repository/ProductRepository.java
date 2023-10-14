package com.casestudy.repository;

import com.casestudy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByName(String name);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = 0")
    void resetProducts();
}
