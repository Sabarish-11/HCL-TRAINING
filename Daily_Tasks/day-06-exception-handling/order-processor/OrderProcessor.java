public class OrderProcessor {

    public void processOrder(String item, int quantity, int availableStock)
        throws OrderProcessingException {

        try {
            validateQuantity(quantity);

            if (quantity > availableStock) {
                throw new InsufficientStockException(
                        "Insufficient stock for " + item
                                + ": requested " + quantity
                                + ", available " + availableStock
                );
            }

            System.out.println(
                    "Order processed successfully: "
                            + quantity + " x " + item
            );

        } catch (InsufficientStockException ex) {
            throw new OrderProcessingException(
                    "Unable to process order for " + item,
                    ex
            );
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero."
            );
        }
    }
}