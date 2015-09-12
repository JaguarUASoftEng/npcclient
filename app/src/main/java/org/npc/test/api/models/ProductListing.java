package org.npc.test.api.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npc.test.api.interfaces.LoadFromJsonInterface;
import org.npc.test.api.models.fieldnames.ProductListingFieldNames;

import java.util.LinkedList;
import java.util.List;

public class ProductListing implements LoadFromJsonInterface<ProductListing> {
    private List<Product> products;
    public List<Product> getProducts() {
        return this.products;
    }
    public ProductListing setProducts(List<Product> products) {
        this.products = products;
        return this;
    }
    public ProductListing addProduct(Product product) {
        this.products.add(product);
        return this;
    }

    @Override
    public ProductListing loadFromJson(JSONObject rawJsonObject) {
        JSONArray products = rawJsonObject.optJSONArray(ProductListingFieldNames.PRODUCTS);

        if (products != null) {
            try {
                for (int i = 0; i < products.length(); i++) {
                    JSONObject jsonProduct = products.getJSONObject(i);

                    this.addProduct((new Product()).loadFromJson(jsonProduct));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    public ProductListing() {
        this.products = new LinkedList<Product>();
    }
}
