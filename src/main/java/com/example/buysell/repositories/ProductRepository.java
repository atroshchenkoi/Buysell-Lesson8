package com.example.buysell.repositories;

import com.example.buysell.models.Product;
import com.example.buysell.models.enums.ProductCity;
import com.example.buysell.models.enums.ProductHealth;
import com.example.buysell.models.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long id);

    @Override
    void delete(Product entity);

    List<Product> findByCityInAndTypeInAndHealthIn(Set<ProductCity> cities, Set<ProductType> types, Set<ProductHealth> healths);
}
