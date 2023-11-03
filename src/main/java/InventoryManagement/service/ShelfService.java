package InventoryManagement.service;

import InventoryManagement.dto.ShelfDTO;
import InventoryManagement.dto.ShelfResponse;
import InventoryManagement.exception_handling.BadRequestException;
import InventoryManagement.exception_handling.DuplicateProductException;
import InventoryManagement.exception_handling.ProductNotFoundException;
import InventoryManagement.model.Product;
import InventoryManagement.model.Shelf;
import InventoryManagement.repository.ShelfRepository;
import InventoryManagement.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShelfService {

    private final ProductService productService;
    private final ShelfRepository shelfRepository;

    public ShelfService(ProductService productService, ShelfRepository shelfRepository) {
        this.productService = productService;
        this.shelfRepository = shelfRepository;
    }

    public ResponseEntity<ApiResponse> addProductToShelf(ShelfDTO shelfDTO) {
        String productId = shelfDTO.getProductID();

        int quantityOnShelf = shelfDTO.getQuantityOnShelf();
        int minThreshold = shelfDTO.getMinThreshold();
        int maxThreshold = shelfDTO.getMaxThreshold();

        if (minThreshold >= maxThreshold)
            throw new BadRequestException("Max threshold should exceed Min threshold.");

        if (quantityOnShelf > maxThreshold)
            throw new BadRequestException("Exceeding the Max threshold is not permitted for adding quantity to the shelf.");

        Shelf existingProductOnShelf = shelfRepository.findByProductProductID(productId);
        if (existingProductOnShelf != null)
            throw new DuplicateProductException("Product with this productId already on the shelf.");

        Product product = productService.findProductByProductId(productId);

        Shelf shelf = new Shelf();
        shelf.setProduct(product);
        shelf.setQuantityOnShelf(quantityOnShelf);
        shelf.setNumericOrWeightValue(shelfDTO.getNumericOrWeightValue());
        shelf.setMinThreshold(minThreshold);
        shelf.setMaxThreshold(maxThreshold);

        shelfRepository.save(shelf);
        return ApiResponse.success("Product added to shelf successfully");
    }

    public List<ShelfResponse> findAllProductsToRefill() {
        List<Shelf> shelves = shelfRepository.findAll();
        List<ShelfResponse> productsToReplenish = new ArrayList<>();

        for (Shelf shelve : shelves) {
            if (shelve.getQuantityOnShelf() < shelve.getMinThreshold() || shelve.getQuantityOnShelf() > shelve.getMaxThreshold()) {
                ShelfResponse productResponse = ShelfResponse.toResponse(shelve);
                productsToReplenish.add(productResponse);
            }
        }

        if (productsToReplenish.isEmpty())
            ApiResponse.success("There are no products to replenish.");

        return productsToReplenish;
    }

    public Object findProductToRefill(String productId) {
        Shelf shelf = shelf(productId);
        if (shelf.getQuantityOnShelf() < shelf.getMinThreshold() || shelf.getQuantityOnShelf() > shelf.getMaxThreshold())
            return ShelfResponse.toResponse(shelf);

        return ApiResponse.success("This product is adequately stocked and does not require replenishment.");
    }

    public List<ShelfResponse> findAllProductsCount() {
        List<Shelf> shelves = shelfRepository.findAll();

        if (shelves.isEmpty())
            ApiResponse.success("There are no products on the shelf.");

        List<ShelfResponse> productsToReplenish = new ArrayList<>();
        for (int i = 0; i < shelves.size(); i++) {
            Shelf shelve = shelves.get(i);
            ShelfResponse productResponse = ShelfResponse.toResponse(shelve);
            productsToReplenish.add(productResponse);
        }

        return productsToReplenish;
    }

    public ShelfResponse findProductCount(String productId) {
        Shelf shelf = shelf(productId);
        return ShelfResponse.toResponse(shelf);
    }


    private Shelf shelf(String productId) {
        Shelf shelf = shelfRepository.findByProductProductID(productId);

        if (shelf == null)
            throw new ProductNotFoundException("Product with this productId is not found on the shelf.");

        return shelf;
    }

    // Implement other methods for calculating markdown dates, replenishment, etc.
}
