package org.npc.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.npc.test.adapters.ProductListAdapter;
import org.npc.test.api.models.Product;
import org.npc.test.api.services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductsList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        this.products = new ArrayList<>();
        this.productListAdapter = new ProductListAdapter(this, this.products);

        this.getProductsListView().setAdapter(this.productListAdapter);
        this.getProductsListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                Product selectedProduct = (Product) getProductsListView().getItemAtPosition(position);

                intent.putExtra(getString(R.string.product_id_extras_key), selectedProduct.getId().toString());

                startActivity(intent);
            }
        });
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

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.products_list_view);
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
