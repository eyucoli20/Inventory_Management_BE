package InventoryManagement.repository;

import InventoryManagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductID(String productID);

    @Query("SELECT p FROM Product p WHERE p.expiryDate <= :currentDate")
    List<Product> findExpiredProducts(LocalDate currentDate);

    @Query("SELECT p FROM Product p WHERE p.expiryDate BETWEEN :today AND :markdownDate")
    List<Product> findProductsFoMarkDown(LocalDate today, LocalDate markdownDate);
}

