package org.npc.test.api.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npc.test.api.enums.ApiLevel;
import org.npc.test.api.enums.ProductApiMethod;
import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.interfaces.PathElementInterface;
import org.npc.test.api.models.Product;

import java.util.LinkedList;
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

    public LinkedList<Product> getProducts() {
        LinkedList<Product> products = new LinkedList<Product>();
        JSONArray rawJsonArray = this.requestMany(
                (new PathElementInterface[]{ApiLevel.ONE, ProductApiMethod.PRODUCT})
        );

        try {
            for (int i = 0; i < rawJsonArray.length(); i++) {
                JSONObject rawJsonObject = rawJsonArray.getJSONObject(i);

                if (rawJsonObject != null) {
                    products.add((new Product()).loadFromJson(rawJsonObject));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product putProduct(Product product) {
        JSONObject rawJsonObject = this.putSingle(
                (new PathElementInterface[] { ApiLevel.ONE }), product.convertToJson()
        );

        if (rawJsonObject != null) {
            return (new Product()).loadFromJson(rawJsonObject);
        } else {
            return new Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR);
        }
    }
}
