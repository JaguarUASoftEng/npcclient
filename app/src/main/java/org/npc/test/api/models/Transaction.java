package org.npc.test.api.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Transaction implements Parcelable
{
    private ProductListing products;
    private double total;

    public Transaction()
    {
        this.products = new ProductListing();
        this.total = 0;
    }

    private Transaction(Parcel in)
    {
        this.products = in.readTypedObject(ProductListing.CREATOR);
        this.total = in.readDouble();
    }

    public void addProduct(Product p)
    {
        this.products.addProduct(p);
        this.total += p.getPrice();
    }

    public double getTotal()
    {
        return this.total;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeTypedObject(products, 0);
        out.writeDouble(this.total);
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

    public ProductListing getProductListing()
    {
        return this.products;
    }
}
