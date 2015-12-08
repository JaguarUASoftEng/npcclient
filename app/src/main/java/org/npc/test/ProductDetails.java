package org.npc.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.models.Product;
import org.npc.test.api.services.ProductService;
import org.npc.test.api.models.Transaction;

public class ProductDetails extends AppCompatActivity {

    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        this.transaction = this.getIntent().getParcelableExtra(this.getResources().getString(
                R.string.product_id_extras_key));

        this.productLookupCode =
                this.getIntent().getStringExtra(
                        this.getResources().getString(R.string.product_id_extras_key)
                );
    }

    @Override
    protected void onResume() {
        super.onResume();

        (new RetrieveProductTask()).execute(this.productLookupCode);
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

    private TextView getLookupCodeTextView() {
        return (TextView) this.findViewById(R.id.product_lookup_code_text_view);
    }

    private TextView getDescriptionTextView()
    {
        return (TextView) this.findViewById(R.id.product_description_text_view);
    }

    private TextView getPriceTextView()
    {
        return (TextView) this.findViewById(R.id.product_price_text_view);
    }

    private TextView getQuantityTextView()
    {
        return (TextView) this.findViewById(R.id.product_quantity_text_view);
    }

    private TextView getIsActiveTextView()
    {
        return (TextView) this.findViewById(R.id.product_isActive_text_view);
    }

    public void ProductDetailsCancelButtonOnClick(View view)
    {
        Intent intent = new Intent();
        intent.putExtra(this.getResources().getString(R.string.search_details_transaction_extras_key),this.transaction);
        setResult(0, intent);
        this.finish();
    }

    public void ProductDetailsAddButtonOnClick(View view)
    {
        Intent intent = new Intent();
        EditText Amount = (EditText) this.findViewById(R.id.Amount_To_Add_Box);
        String query = Amount.getText().toString();
        intent.putExtra(this.getResources().getString(R.string.search_details_transaction_extras_key),this.transaction, query);
        this.finish();
    }

    private String productLookupCode;

    private class RetrieveProductTask extends AsyncTask<String, Void, Product> {
        protected Product doInBackground(String... productLookupCodes) {
            return (new ProductService()).getProduct(productLookupCodes[0]);
        }

        protected void onPostExecute(Product result) {
            if (result.getApiRequestStatus() == ProductApiRequestStatus.OK) {
                getLookupCodeTextView().setText(result.getLookupCode());
                getDescriptionTextView().setText(result.getDescription());
                getPriceTextView().setText(Double.toString(result.getPrice()));
                getQuantityTextView().setText(Integer.toString(result.getCount()));
                getIsActiveTextView().setText(Boolean.toString(result.getIsActive()));
            } else {
                getLookupCodeTextView().setText(result.getApiRequestStatus().name());
            }
        }
    }
}