package org.npc.test.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;


public class Transaction implements Parcelable
{
    private ArrayList<Product> products;

    public Transaction()
    {
        this.products = new ArrayList<>();
    }

    private Transaction(Parcel in)
    {
        this.products = in.createTypedArrayList(Product.CREATOR);
    }

    public void addProduct(Product newProduct)
    {
        for (Product p : this.products)
        {
            if (p.getId().compareTo(newProduct.getId()) == 0)
            {
                p.setCount(p.getCount() + 1);
                return;
            }
        }
        this.products.add(newProduct);
        return;
    }

    public double getTotal()
    {
        double total = 0;
        for (Product p : this.products)
        {
            total += p.getCount() * p.getPrice();
        }

        return total;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeTypedList(products);
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>()
    {
        public Transaction createFromParcel(Parcel in)
        {
            return new Transaction(in);
        }
        public Transaction[] newArray(int size)
        {
            return new Transaction[size];
        }
    };

    public ArrayList getProducts()
    {
        return this.products;
    }
}
