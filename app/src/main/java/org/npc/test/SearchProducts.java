package org.npc.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.npc.test.adapters.ProductListAdapter;

public class SearchProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
    }

    public void searchProductButtonOnClick(View view)
    {
        Intent intent = new Intent(this, ProductDetails.class);
        EditText searchBox = (EditText) this.findViewById(R.id.productSearchBox);
        String query = searchBox.getText().toString();
        this.startActivity(intent.putExtra(this.getResources().getString(R.string.product_id_extras_key), query));
    }

    public void updateProductList(transactionDTO trans)
    {
        ListView list = (ListView) this.findViewById(R.id.products_list_view);
        ProductListAdapter adapter = new ProductListAdapter(this,trans.getProducts());
        list.setAdapter(adapter);
    }

    public void addProduct(TransactionDTO trans, Product p, int q)
    {
        trans.addProduct();
    }
}
