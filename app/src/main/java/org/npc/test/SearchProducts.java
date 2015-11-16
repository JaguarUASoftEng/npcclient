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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == UPDATE_PRODUCTS)
        {
            if (resultCode == RESULT_OK)
            {
                this.transaction = data.getParcelableExtra();
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
        intent.putExtra(this.getResources().getString(R.string.transaction_extras_key),this.transaction);
        setResult(0, intent);
    }

    public void updateProductList()
    {
        ListView list = (ListView) this.findViewById(R.id.products_list_view);
        ProductListAdapter adapter = new ProductListAdapter(this, this.transaction.getProductListing().getProducts());
        list.setAdapter(adapter);
    }
}
