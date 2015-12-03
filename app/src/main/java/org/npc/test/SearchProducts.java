package org.npc.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import org.npc.test.api.models.Transaction;
import org.npc.test.adapters.ProductListAdapter;

public class SearchProducts extends AppCompatActivity {

    static final int UPDATE_PRODUCTS = 0;
    Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        this.transaction = this.getIntent().getParcelableExtra(this.getResources().getString(
                R.string.summary_search_transaction_extras_key));
        this.updateProductList();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == UPDATE_PRODUCTS)
        {
            if (resultCode == RESULT_OK)
            {
                this.transaction = data.getParcelableExtra(this.getResources().getString(
                        R.string.search_details_transaction_extras_key));
                this.updateProductList();
            }
        }
    }
    public void searchProductButtonOnClick(View view)
    {
        Intent intent = new Intent(this, ProductDetails.class);
        EditText searchBox = (EditText) this.findViewById(R.id.productSearchBox);
        String query = searchBox.getText().toString();
        this.startActivityForResult(intent.putExtra(this.getResources().getString(R.string.product_id_extras_key), query),UPDATE_PRODUCTS);
    }

    public void searchProductBackButtonOnClick(View view)
    {
        Intent intent = new Intent();
        intent.putExtra(this.getResources().getString(R.string.search_details_transaction_extras_key),this.transaction);
        setResult(0, intent);
        this.finish();
    }

    public void updateProductList()
    {
        ListView list = (ListView) this.findViewById(R.id.productList);
        ProductListAdapter adapter = new ProductListAdapter(this, this.transaction.getProducts());
        list.setAdapter(adapter);
    }
}
