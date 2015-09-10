package org.npc.test.api.services;

import org.json.JSONObject;
import org.npc.test.api.enums.ApiLevel;
import org.npc.test.api.enums.ProductApiMethod;
import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.interfaces.PathElementInterface;
import org.npc.test.api.models.Product;

import java.util.UUID;

public class ProductService extends BaseRemoteService {
    public Product getProduct(UUID productId) {
        JSONObject rawJsonObject = this.requestSingle(
            (new PathElementInterface[] { ApiLevel.ONE, ProductApiMethod.PRODUCT }), productId
        );

        if (rawJsonObject != null) {
            return (new Product()).loadFromJson(rawJsonObject);
        } else {
            return new Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR);
        }
    }
}
