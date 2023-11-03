package InventoryManagement.repository;

import InventoryManagement.model.Product;
import InventoryManagement.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Shelf findByProductProductID(String productID);
}
