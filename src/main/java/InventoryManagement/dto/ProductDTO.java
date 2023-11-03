package InventoryManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ProductDTO {

    @NotBlank(message = "ProductID cannot be blank")
    @Size(max = 255, message = "ProductID must not exceed 255 characters")
    private String productID;

    @NotBlank(message = "ProductName cannot be blank")
    @Size(max = 255, message = "ProductName must not exceed 255 characters")
    private String productName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    private Integer timeDurationForMarkDown;

}
