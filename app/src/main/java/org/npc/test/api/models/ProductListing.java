package org.npc.test.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npc.test.api.interfaces.LoadFromJsonInterface;
import org.npc.test.api.models.fieldnames.ProductListingFieldNames;

import java.util.LinkedList;
import java.util.List;

public class ProductListing implements Parcelable, LoadFromJsonInterface<ProductListing> {
    private List<Product> products;

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeTypedList(products);
    }

    public static final Parcelable.Creator<ProductListing> CREATOR = new Parcelable.Creator<ProductListing>()
    {
        public ProductListing createFromParcel(Parcel in)
        {
            return new ProductListing(in);
        }

        public ProductListing[] newArray(int size)
        {
            return new ProductListing[size];
        }
    };

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

    private ProductListing(Parcel in)
    {
        this.products = in.readTypedList(Product.CREATOR);
    }
}
