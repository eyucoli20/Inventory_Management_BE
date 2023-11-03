package InventoryManagement.controller;

import InventoryManagement.dto.ProductDTO;
import InventoryManagement.model.Product;
import InventoryManagement.service.ProductService;
import InventoryManagement.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // section A
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }


    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productId,productDTO));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        Product product = productService.findProductByProductId(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<Product>> getAllExpiredProducts() {
        List<Product> products = productService.findAllExpiredProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/in-mark-down")
    public ResponseEntity<List<Product>> getAllProductsInMarkDown() {
        List<Product> products = productService.findAllProductsInMarkDown();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/for-mark-down")
    public ResponseEntity<List<Product>> getAllProductsForMarkDown() {
        List<Product> products = productService.findAllProductsForMarkDown();
        return ResponseEntity.ok(products);
    }
}
