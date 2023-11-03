package InventoryManagement.exception_handling;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String message) {
        super(message);

    }
}
