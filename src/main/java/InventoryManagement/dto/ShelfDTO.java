package InventoryManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShelfDTO {

    @NotBlank(message = "ProductID cannot be blank")
    @Size(max = 255, message = "ProductID must not exceed 255 characters")
    private String productID;

    @NotNull(message = "Quantity On Shelf cannot be null")
    private Integer quantityOnShelf;

    @NotNull(message = "Min Threshold cannot be null")
    private Integer minThreshold;

    @NotNull(message = "Max Threshold cannot be null")
    private Integer maxThreshold;

    @Pattern(regexp = "^(?i)(lbs|number)$", message = "Must be 'lbs' or 'number'")
    @NotBlank(message = "Unit Of Measurement cannot be blank")
    private String numericOrWeightValue;

}
