package org.npc.test;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import org.npc.test.api.models.*;
import java.util.UUID;
import java.lang.Object;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import org.json.JSONException;
import java.io.IOException;
import android.util.Log;
import java.io.InputStreamReader;
import org.json.JSONArray;

/**
 * Created by nfrancav on 11/17/2015.
 */
public class Test_Transaction_Summery extends AppCompatActivity {

    static final int ADD_PRODUCT = 0;
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_summary);
        this.transaction = new Transaction();

//        Dummy product initialization
//        Product p1 = new Product();
//        Product p2 = new Product();
//        p1.setId(UUID.randomUUID());
//        p1.setCount(3);
//        p1.setDescription("Vitamin X");
//        p1.setPrice(4.0);
//        p2.setId(UUID.randomUUID());
//        p2.setCount(6);
//        p2.setDescription("Supplement Y");
//        p2.setPrice(2.0);
//        this.transaction.addProduct(p1);
//        this.transaction.addProduct(p2);
    }

    public void AddProductButtonOnClick(View view) {
        Intent intent = new Intent(this, SearchProducts.class);
        intent.putExtra(this.getResources().getString(R.string.summary_search_transaction_extras_key),
                this.transaction);
        this.startActivityForResult(intent, Test_Transaction_Summery.ADD_PRODUCT);
    }

    public void MakePaymentButtonOnClick(View view) {
        this.startActivity(new Intent(this, CreateProduct.class));
    }

    private TextView getPriceTextView()
    {
        return (TextView) this.findViewById(R.id.PaymentTotalTextField);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Test_Transaction_Summery.ADD_PRODUCT && resultCode == 0)
        {
            this.transaction = data.getParcelableExtra(this.getResources().getString(R.string.search_details_transaction_extras_key));
            this.getPriceTextView().append(Double.toString(this.transaction.getTotal()));
        }
    }


    public void CompleteTransactionButtonOnClick(View view)
    {
        this.finish();
    }

    public JSONObject toJSon() {
        try {
            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArr = new JSONArray();

            for (Product pn : this.transaction.getProducts()) {
                JSONObject pnObj = new JSONObject();
                pnObj.put("UUID", pn.getId());
                pnObj.put("LookupCode", pn.getLookupCode());
                pnObj.put("Price", pn.getPrice());
                pnObj.put("Description", pn.getDescription());
                pnObj.put("IsActive", pn.getIsActive());
                pnObj.put("Count", pn.getCount());
                pnObj.put("CreatedOn", pn.getCreatedOn());
                pnObj.put("ApiRequestMessage", pn.getApiRequestMessage());
                pnObj.put("ApiRequestStatus", pn.getApiRequestStatus());
                pnObj.put("Class", pn.getClass());
                jsonArr.put(pnObj);
            }
            jsonObj.put("JsonProductList", jsonArr);
            return jsonObj;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    String destination = "http://localhost:8080/registerservice/apiv0/transactionEntry";
    JSONObject request =  toJSon();

    protected JSONObject doInBackground(Void... params)  {
        try {
            URL url = new URL(destination);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            client.setRequestMethod("PUT");
            client.connect();

            Log.d("doInBackground(Request)", request.toString());

            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
            String output = request.toString();
            writer.write(output);
            writer.flush();
            writer.close();
            client.disconnect();

            /*InputStream input = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("doInBackground(Resp)", result.toString());
            response = new JSONObject(result.toString());
        } catch (JSONException e){
            e.printStackTrace();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

}
