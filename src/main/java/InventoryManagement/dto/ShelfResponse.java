package InventoryManagement.dto;

import InventoryManagement.model.Product;
import InventoryManagement.model.Shelf;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShelfResponse {

    private Product product;
    private int quantityOnShelf;
    private int minThreshold;
    private int maxThreshold;
    private String amountToReplenish;
    private LocalDateTime lastReplenishmentTimestamp;

    public static ShelfResponse toResponse(Shelf shelf) {

        int amount = shelf.getMaxThreshold() - shelf.getQuantityOnShelf();

        String amountToReplenish = String.valueOf(amount);
        if (shelf.getNumericOrWeightValue().equalsIgnoreCase("lbs"))
            amountToReplenish = amount + " lbs";

        ShelfResponse shelfResponse = new ShelfResponse();

        shelfResponse.setProduct(shelf.getProduct());
        shelfResponse.setQuantityOnShelf(shelf.getQuantityOnShelf());
        shelfResponse.setAmountToReplenish(amountToReplenish);
        shelfResponse.setMinThreshold(shelf.getMinThreshold());
        shelfResponse.setMaxThreshold(shelf.getMaxThreshold());
        shelfResponse.setLastReplenishmentTimestamp(shelf.getLastReplenishmentTimestamp());

        return shelfResponse;
    }
}
