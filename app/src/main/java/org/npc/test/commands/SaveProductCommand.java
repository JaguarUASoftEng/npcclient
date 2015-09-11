package org.npc.test.commands;

import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.models.Product;
import org.npc.test.api.services.ProductService;
import org.npc.test.commands.interfaces.ResultCommandInterface;

public class SaveProductCommand implements ResultCommandInterface<Product> {
    @Override
    public Product execute() {
        if (this.product == null) {
            return (new Product()).setApiRequestStatus(ProductApiRequestStatus.INVALID_INPUT);
        }

        return (new ProductService()).putProduct(this.product);
    }

    private Product product;
    public Product getProduct() {
        return this.product;
    }
    public SaveProductCommand setProduct(Product product) {
        this.product = product;
        return this;
    }
}
