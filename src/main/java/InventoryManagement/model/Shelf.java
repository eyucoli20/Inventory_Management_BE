package InventoryManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shelves")
public class Shelf implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantityOnShelf;

    private String numericOrWeightValue;

    @Column(name = "min_threshold")
    private int minThreshold;

    @Column(name = "max_threshold")
    private int maxThreshold;

    @UpdateTimestamp
    @Column(name = "last_replenishment_timestamp")
    private LocalDateTime lastReplenishmentTimestamp;
}