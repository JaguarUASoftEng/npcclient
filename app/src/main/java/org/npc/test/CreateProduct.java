package org.npc.test;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.models.Product;
import org.npc.test.commands.CreateDummyProductCommand;
import org.npc.test.commands.SaveProductCommand;

import java.util.UUID;

public class CreateProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
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

    public void createDummyProductButtonOnClick(View view) {
        (new CreateProductTask()).execute();
    }

    private class CreateProductTask extends AsyncTask<Void, Void, Product> {
        protected Product doInBackground(Void... params) {
            return (new SaveProductCommand()).
                setProduct(
                    (new CreateDummyProductCommand()).execute()
                ).
                execute();
        }

        protected void onPostExecute(Product result) {
            boolean successfulSave = (result.getApiRequestStatus() == ProductApiRequestStatus.OK);

            new AlertDialog.Builder(CreateProduct.this).
                setTitle(getString(R.string.product_save_alert_dialog_title)).
                setMessage(successfulSave ? getString(R.string.product_save_success) : getString(R.string.product_save_failure)).
                show();
        }
    }
}
