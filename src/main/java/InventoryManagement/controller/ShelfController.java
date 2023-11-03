package InventoryManagement.controller;

import InventoryManagement.dto.ShelfDTO;
import InventoryManagement.dto.ShelfResponse;
import InventoryManagement.service.ShelfService;
import InventoryManagement.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelves")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    // section A
    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProductToShelf(@RequestBody @Valid ShelfDTO shelfDTO) {
        return shelfService.addProductToShelf(shelfDTO);
    }

    @GetMapping("/products-to-refill")
    public ResponseEntity getProductsToRefill() {
        Object productsToReplenish = shelfService.findAllProductsToRefill();
        return ResponseEntity.ok(productsToReplenish);
    }

    @GetMapping("/products-to-refill/{productId}")
    public ResponseEntity getProductToRefill(@PathVariable String productId) {
        Object product = shelfService.findProductToRefill(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products-count")
    public ResponseEntity<List<ShelfResponse>> getProductsCount() {
        List<ShelfResponse> productsToReplenish = shelfService.findAllProductsCount();
        return ResponseEntity.ok(productsToReplenish);
    }

    @GetMapping("/products-count/{productId}")
    public ResponseEntity<ShelfResponse> getProductCount(@PathVariable String productId) {
        ShelfResponse shelfResponse = shelfService.findProductCount(productId);
        return ResponseEntity.ok(shelfResponse);
    }
}
