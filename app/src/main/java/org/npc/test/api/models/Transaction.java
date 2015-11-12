package org.npc.test.api.models;

import android.os.Parcelable;

import java.util.ArrayList;
public class Transaction implements Parcelable
{
    private ArrayList<Product> products;
    private double total;

    public Transaction()
    {
        this.products = new ArrayList<>();
        this.total = 0;
    }

    public ArrayList<Product> getProducts()
    {
        return new ArrayList<Product>(this.products);
    }

    public void addProduct(Product product)
    {
        for (Product p : this.products)
        {
            if (product.getLookupCode().equals(p.getLookupCode()))
            {
                int count = p.getCount();
                p.setCount(count++);
            }

            else
            {
                products.add(product);
            }
        }
    }
}
