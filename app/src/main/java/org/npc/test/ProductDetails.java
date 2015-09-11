package org.npc.test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.models.Product;
import org.npc.test.api.services.ProductService;

import java.util.UUID;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        this.getMessageTextView().setText(
            this.getIntent().getStringExtra(
                this.getResources().getString(R.string.product_id_extras_key)
            )
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        (new RetrieveProductTask()).execute(UUID.fromString("69122437-5b8e-433b-b87a-9cf9e56679e8"));
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

    private TextView getMessageTextView() {
        return (TextView) this.findViewById(R.id.product_details_message_text_view);
    }
    private TextView getLookupCodeTextView() {
        return (TextView) this.findViewById(R.id.product_lookup_code_text_view);
    }

    private class RetrieveProductTask extends AsyncTask<UUID, Void, Product> {
        protected Product doInBackground(UUID... productIds) {
            return (new ProductService()).getProduct(productIds[0]);
        }

        protected void onPostExecute(Product result) {
            if (result.getApiRequestStatus() == ProductApiRequestStatus.OK) {
                getLookupCodeTextView().setText(result.getLookupCode());
            } else {
                getLookupCodeTextView().setText(result.getApiRequestStatus().name());
            }
        }
    }
}
