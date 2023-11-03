package InventoryManagement.service;

import InventoryManagement.dto.ProductDTO;
import InventoryManagement.exception_handling.DuplicateProductException;
import InventoryManagement.exception_handling.ProductNotFoundException;
import InventoryManagement.model.Product;
import InventoryManagement.repository.ProductRepository;
import InventoryManagement.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<ApiResponse> createProduct(ProductDTO productDTO) {
        // Check if a product with the same ProductID already exists
        if (productRepository.findByProductID(productDTO.getProductID()) != null)
            throw new DuplicateProductException("ProductName should have a uniqueID, the ProductName already exists with the same uniqueID");

        LocalDate expiryDate = productDTO.getExpiryDate() != null ?
                productDTO.getExpiryDate() : LocalDate.now().plusMonths(3);

        int timeDurationForMarkDown = productDTO.getTimeDurationForMarkDown() != null ?
                productDTO.getTimeDurationForMarkDown() : 6;

        Product product = new Product();

        product.setProductID(productDTO.getProductID());
        product.setProductName(productDTO.getProductName());
        product.setExpiryDate(expiryDate);
        product.setTimeDurationForMarkDown(timeDurationForMarkDown);

        productRepository.save(product);

        return ApiResponse.success("ProductName with the ProductID created successfully");
    }

    public Product findProductByProductId(String productId) {
        Product product = productRepository.findByProductID(productId);
        if (product == null)
            throw new ProductNotFoundException("ProductName/ProductID not found");

        return product;
    }

    public Product updateProduct(String productId, ProductDTO productDTO) {
        Product product = productRepository.findByProductID(productId);

        if (productDTO.getProductName() != null)
            product.setProductName(productDTO.getProductName());

        if (productDTO.getExpiryDate() != null)
            product.setExpiryDate(productDTO.getExpiryDate());

        if (productDTO.getTimeDurationForMarkDown() != null)
            product.setTimeDurationForMarkDown(productDTO.getTimeDurationForMarkDown());

        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty())
            throw new ProductNotFoundException("No products were found.");

        return products;
    }

    public List<Product> findAllExpiredProducts() {
        List<Product> products = productRepository.findExpiredProducts(LocalDate.now());
        if (products.isEmpty())
            throw new ProductNotFoundException("No Expired products were found.");

        return products;
    }

    public List<Product> findAllProductsInMarkDown() {

        List<Product> products = findAllProducts();
        List<Product> productsInMarkDown = new ArrayList<>();

        LocalDate now = LocalDate.now();

        for (Product product : products) {
            int days = (int) ChronoUnit.DAYS.between(now, product.getExpiryDate());
            if (days <= product.getTimeDurationForMarkDown())
                productsInMarkDown.add(product);
        }

        if (productsInMarkDown.isEmpty())
            throw new ProductNotFoundException("No In Mark Down products were found.");

        return productsInMarkDown;
    }

    public List<Product> findAllProductsForMarkDown() {
        LocalDate today = LocalDate.now();
        LocalDate markdownDate = today.plusDays(7); // A week from now

        List<Product> productsForMarkDown = productRepository.findProductsFoMarkDown(today, markdownDate);

        if (productsForMarkDown.isEmpty())
            throw new ProductNotFoundException("No products to be marked down a week from now.");

        return productsForMarkDown;
    }

}
