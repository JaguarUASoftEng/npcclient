package org.npc.test;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.npc.test.adapters.ProductListAdapter;
import org.npc.test.api.models.Product;
import org.npc.test.api.services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductsList extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        products = new ArrayList<>();
        productListAdapter = new ProductListAdapter(this, products);
        this.getListView().setAdapter(productListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        (new RetrieveProductsTask()).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ProductDetails.class);
        Product selectedProduct = (Product) l.getItemAtPosition(position);

        intent.putExtra(this.getString(R.string.product_id_extras_key), selectedProduct.getId().toString());

        this.startActivity(intent);
    }

    private List<Product> products;
    private ProductListAdapter productListAdapter;

    private class RetrieveProductsTask extends AsyncTask<Void, Void, List<Product>> {
        protected List<Product> doInBackground(Void... params) {
            return (new ProductService()).getProducts();
        }

        protected void onPostExecute(List<Product> results) {
            products.clear();

            for (Product product : results) {
                products.add(product);
            }

            productListAdapter.notifyDataSetChanged();
        }
    }
}
