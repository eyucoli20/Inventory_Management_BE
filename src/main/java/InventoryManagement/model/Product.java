package InventoryManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id; // primary key

    @Column(unique = true)
    private String productID; // Unique identifier for the product (e.g., SKU)

    @Column(name = "product_name")
    private String productName;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "time_duration_for_mark_down")
    private int timeDurationForMarkDown;
}
